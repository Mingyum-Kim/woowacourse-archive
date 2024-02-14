# 코딩 테스트 대비 정리 노트

## 목차

- [코딩 테스트 대비 정리 노트](#---------------)
    * [0. Snippet](#0-snippet)
    * [1. 기본적으로 세팅할 클래스](#1--------------)
        + [ConsoleReader](#consolereader)
        + [ConsoleWriter](#consolewriter)
        + [CustomException](#customexception)
        + [ErrorMessage](#errormessage)
        + [Retry](#retry)
    * [2. 검증 클래스](#2-------)
        + [ValidateNumber](#validatenumber)
        + [validateRange](#validaterange)
        + [validateDuplication](#validateduplication)
    * [3. 자바 스트림 (Java Stream)](#3---------java-stream-)
        + [Convert](#convert)
        + [Count](#count)
        + [Sum](#sum)
        + [Max](#max)
        + [isPresent](#ispresent)
    * [4. Enum](#4-enum)
        + [Find Object](#find-object)
        + [Functional Programming](#functional-programming)
    * [5. List](#5-list)
        + [equals](#equals)
        + [remove](#remove)
        + [subList](#sublist)
    * [6. Map](#6-map)
        + [Foreach](#foreach)
        + [Map using Stream](#map-using-stream)
        + [getOrDefault](#getordefault)
    * [7. Set](#7-set)
        + [retainAll](#retainall)
        + [removeAll](#removeall)
    * [8. 문자열](#8----)
        + [문자열 포맷](#------)
        + [StringJoiner](#stringjoiner)
        + [String.join](#stringjoin)
        + [정규표현식](#-----)
    * [9. LocalDate](#9-localdate)
    * [10. Test](#10-test)
        + [assertThatThrownBy()](#assertthatthrownby--)
        + [@ParameterizedTest](#-parameterizedtest)
        + [Mock Test](#mock-test)
        + [@Nested](#-nested)

---
## 0. Snippet
`csrd` : ConsoleReader

`cswt` : ConsoleWriter

`customexception` : CustomException

`errormessage` : ErrorMessge

`enumfind`: enum Find method

`parsestringtolist` : Parse String to List

`prsf` : private static final

`retry` : retry Until Success

`sumlist`: calculateTotalCount

`validduplicateall` : validate Duplication of List

`validduplicateone`: validate Duplication of List from One element

`validnumber` : validate number

`validrange`: validate range

`valieseparator` : validate correct separator

`printlnmessage`: ConsoleWriter.printlnMessage

`prsts`: private static final String

`prsti` : private static final int

`throwcustom` : throw CustomException.from(ErrorMessage.)


## 1. 기본적으로 세팅할 클래스

### ConsoleReader

```java
public final class ConsoleReader {
    public static String enterMessage() {
        return Validator.validate(Console.readLine());
    }

    private static class Validator {
        public static String validate(String message) {
            validateBlankInput(message);
            return message;
        }

        private static void validateBlankInput(String message) {
            if (message.isBlank()) {
                throw CustomException.from(ErrorMessage.BLANK_INPUT_ERROR);
            }
        }
    }
}
```

### ConsoleWriter
```java
public final class ConsoleWriter {
    public static void printlnMessage(String message) {
        System.out.println(message);
    }

    public static void printlnFormat(String message, Object... args) {
        printlnMessage(String.format(message, args));
    }
}
```

### CustomException
```java
public class CustomException extends IllegalArgumentException {
    private static final String PREFIX = "[ERROR] ";

    private CustomException(ErrorMessage errorMessage) {
        super(PREFIX + errorMessage.getMessage());
    }

    public static CustomException from(ErrorMessage errorMessage) {
        return new CustomException(errorMessage);
    }
}
```

### ErrorMessage
```java
public enum ErrorMessage {
    BLANK_INPUT_ERROR("빈 문자열이 입력되었습니다."); 

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
```

### Retry
```java
private static <T> T retry(Supplier<T> supplier) {
    while (true) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            ConsoleWriter.printlnMessage(e.getMessage());
        }
    }
}
```

## 2. 검증 클래스
### ValidateNumber
숫자임을 검증하는 메서드
```java
public static int validateNumber(String message, ErrorMessage errorMessage) {
    if (isNotNumber(message)) {
        throw CustomException.from(errorMessage);
    }
    return Integer.parseInt(message);
}

private static boolean isNotNumber(String str) {
        return !str.matches("\\d+"); // 0을 포함한 양의 정수를 테스트
        return !str.matches("-?\\d+"); // 양수와 음수를 모두 허용하는 경우 
        return !str.matches("^[1-9]\\d*$") // 1 이상의 양의 정수를 테스트
}
```

### validateRange
주어진 숫자 범위 (start 이상, end 이하)를 검증하는 메서드 
```java
public static void validateRange(int number, int start, int end) {
        if (isInvalidRange(number, start, end)) {
        throw CustomException.from(ErrorMessage.INVALID_RANGE_ERROR);
    }
}

private static boolean isInvalidRange(int number, int start, int end) {
        return number < start || number > end;
}
```

### validateDuplication

리스트 내의 요소들이 중복이 없음을 검증하는 메서드
```java
private static void validateDuplicatedItem(List<String> items) {
    if (hasDuplicatedItem(items)) {
        throw CustomException.from(ErrorMessage.DUPLICATED_ITEM_ERROR);
    }
}

private static boolean hasDuplicatedItem(List<String> items) {
    return items.size() != calculateUniqueItemsCount(items);
}

private static int calculateUniqueItemsCount(List<String> items) {
    return (int) items.stream()
            .distinct()
            .count();
} 
```

새로 입력된 문자열이 기존의 리스트와 중복되지 않음을 검증하는 메서드
```java
public static void validateDuplication(List<String> items, String item) {
	if(isDuplicated(items, item)) {
		throw CustomException.from(ErrorMessage.DUPLICATION_ERROR);
	}
}

public boolean isDuplicated(List<String> items, String item){ 
	return items.stream().anyMatch(s -> s.equals(newString));
}
```

## 3. 자바 스트림 (Java Stream)

### Convert

문자열을 구분자로 나누어 리스트로 변환
```java
private static List<String> parseStringToList(String message, String separator) {
		return Arrays.stream(split(message, separator)).toList();
}

private static String[] split(String message, String separator) {
		return message.split(separator, -1);
}
```

문자열 리스트를 클래스 리스트로 변환
```java
private List<Car> generateToCars(List<String> cars) {
        return cars.stream()
                .map(carName-> new Car(new carName))
                .toList();
}
```

### Count
리스트에서 특정 조건에 만족하는 개수를 계산
```java
public int countMatchedNumbers(final List<Number> numbers) {
        return (int) numbers.stream()
                .filter(winningNumbers::contains)
                .count();
}
```

### Sum
리스트에서 값을 꺼내어 합을 계산
```java
private static int calculateTotalCount(List<Order> orders) {
            return orders.stream()
                    .map(Order::getCount)
                    .mapToInt(Count::getValue)
                    .sum();
}
```

### Max
리스트에서 최댓값을 조회하는 메서드
```java
private int getMaxCount(List<Car> cars) {
    return cars.stream()
            .mapToInt(car -> car.getCount()) 
            .max()
            .orElseThrow(IllegalStateException::new);
}
```

### isPresent
리스트에서 특정 값이 존재하는 지 여부를 판단하는 메서드
```java
private boolean isQualified(int day) {
        return Arrays.stream(SPECIAL_DAYS)
                .filter(special -> special == day)
                .findAny()
                .isPresent();
} 
```

## 4. Enum

### Find Object
문자열 입력으로 Enum 객체를 조회하는 메서드 
```java
public static GameCommand from(String command) {
        return Arrays.stream(GameCommand.values())
                .filter(element -> element.command.equals(command))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.INVALID_COMMAND_ERROR));
}
```

### Functional Programming

```java
public enum WinningType {
    NONE(0,
            (winningCount, hasBonusNumber) -> winningCount < 3),
    FIFTH(3,
            (winningCount, hasBonusNumber) -> winningCount.equals(3)),
    FOURTH(4,
            (winningCount, hasBonusNumber) -> winningCount.equals(4)),
    THIRD(5,
            (winningCount, hasBonusNumber) -> winningCount.equals(5) && hasBonusNumber.equals(false)),
    SECOND(5,
            (winningCount, hasBonusNumber) -> winningCount.equals(5) && hasBonusNumber.equals(true)),
    FIRST(6,
            (winningCount, hasBonusNumber) -> winningCount.equals(6));

    private final int winningCount;
    private final BiPredicate<Integer, Boolean> standard;

    public static WinningType findWinningType(final int winningCount, final boolean hasBonusNumber) {
        return Arrays.stream(WinningType.values())
                .filter(winning -> winning.standard.test(winningCount, hasBonusNumber))
                .findFirst()
                .orElse(NONE);
    }
}
```

## 5. List

### equals
```java
list.equals(other);
```

### remove
```java
list.remove(int index)
list.remove(Object o); // 첫번째로 나오는 값만 삭제함
list.removeAll(); 
list.removeIf(name -> name.equals("김민겸"));
```

### subList

```java
names.subList(0, names.size() - 3); // 뒤에서 3개의 요소를 제외함
```

## 6. Map

### Foreach
```java
for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
}
```

### Map using Stream
```java
private double getEarnings() {
        int earnings = (int) winningResult.entrySet()
                .stream()
                .mapToDouble(entry -> calculateWinningPrice(entry))
                .sum();
        return earnings;
    }
```

### getOrDefault
```java
map.put(key, map.getOrDefault(key, 0) +1)
```

## 7. Set

### retainAll
(교집합) 중복되는 요소가 있는 지 확인
```java
Set<String> intersect = new HashSet<>(pair1);
return intersect.retainAll(pair2); // 중복된 요소가 있어서 intersect가 변경되면 true
```

### removeAll
(차집합) 중복되는 요소가 있는 지 확인
```java
Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
Set<Integer> set2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
// 중복된 요소가 있어서 set1이 변경되므로 true
return set1.removeAll(set2); //  set = {1, 2, 3}  
```

## 8. 문자열
### 문자열 포맷
소수점 둘째자리까지 반올림하여 출력
```java
double number = 123.45678;
DecimalFormat df = new DecimalFormat("#.##");
String formattedNumber = df.format(number);

System.out.println(formattedNumber); // 123.46
```

돈의 형태로 출력
```java
int number = 1000;
System.out.printf("%,d", number);
```

### StringJoiner

StringJoiner 객체 생성하여 문자열 조합
```java
StringJoiner sj = new StringJoiner("-", "[", "]");

sj.add(first);
sj.add(second);
sj.add(third);
```

스트림을 사용하여 문자열 조합
```java
List<String> names = Arrays.asList(first, second, third, fourth, fifth);

String message = names.stream()
        .collect(Collectors.joining("-", "[", "]"));
// 하나의 이름이라면 [ 김민겸 ] 이런 식으로 양 옆의 문자만 붙는다
```

### String.join
단순히 리스트 사이에 구분자를 넣을 때 사용
```java
String[] value = {"apple", "orange", "grape", "pear"}; // List<String>을 사용해도 된다!
String separator = ", ";
String result = String.join(separator, value);
System.out.println(result); // apple, orange, grape, pear
```

### 정규표현식
숫자로만 이루어진 문자열임을 검사 
```java
private static boolean isNumber(String str) {
        return str.matches("\\d+");
}
```
R 또는 Q에만 해당되는 문자열임을 검사
```java
private static boolean isCorrect(String str) {
        return str.matches("^[RQ]$");
}
```
이메일 주소
```
/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i
```

전화번호
```java
/^\d{3}-\d{3,4}-\d{4}$/
```

핸드폰 번호

```java
/^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/
```

`문자열-숫자` 형태의 입력인 경우
```java
"([가-힣a-zA-Z\\s]+-[0-9]+,?)+";
```

## 9. LocalDate
년, 월, 일 입력으로 요일 출력하기
```java
LocalDate date = LocalDate.of(year, month, day);
String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
System.out.println(dayOfWeek);  // 출력: 금요일
```


## 10. Test
### assertThatThrownBy()

```java
assertThatThrownBy(() -> TestMethod())
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessageContaining(ErrorMessage.);
```

### @ParameterizedTest

하나의 원시값을 여러 개 테스트하는 경우

```java
@ParameterizedTest
@ValueSource(strings = {"", "123"}) 
@DisplayName("다리 길이 입력 크기가 1보다 작거나 2보다 큰 경우 예외 처리")
void validateBridgeLength(String input) {
    assertThatThrownBy(() -> validator.validateBridgeSize(input))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(ErrorMessage.INCORRECT_BRIDGE_SIZE);
}
```

Enum 객체를 파라미터로 넣는 경우 
```java
@ParameterizedTest
@EnumSource(value = TimeUnit.class, names = { "DAYS", "HOURS" })
void testWithEnumSourceInclude(TimeUnit timeUnit) {
    assertTrue(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS).contains(timeUnit));
}
```

(MethodSource) 여러 타입의 파라미터를 사용하는 경우
```java
@ParameterizedTest
@MethodSource("provideStringsForIsBlank")
void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
     assertEquals(expected, Strings.isBlank(input));
}

private static Stream<Arguments> provideStringsForIsBlank() {
    return Stream.of(
       Arguments.of(null, true)
       Arguments.of("", true),
       Arguments.of("  ", true),
       Arguments.of("not blank", false)
    );
}
```

(CsvSource) 여러 타입의 파라미터를 사용하는 경우
```java
    @ParameterizedTest
    @CsvSource({
            "1, false, true",
            "7, true, false",
            "24, true, false"
    })
    @DisplayName("평일 및 주말 여부를 확인한다.")
    void checkWeekdayAndWeekend(int inputDate, boolean isWeekday, boolean isWeekend) {
        // given
        Date date = Date.from(inputDate);

        // when
        boolean weekdayResult = date.isWeekday();
        boolean weekendResult = date.isWeekend();

        // then
        assertEquals(isWeekday, weekdayResult);
        assertEquals(isWeekend, weekendResult);
    }

    @ParameterizedTest
    @CsvSource({
            "APPETIZER, true",
            "DESSERT, false"
    })
    @DisplayName("특정 메뉴 카테고리에 해당하는 주문인지 확인한다.")
    void checkIfOrderIsIncludedInMenu(Menu menu, boolean expected) {
        // given
        Order order = Order.of("타파스", 2);

        // when
        boolean isIncluded = order.isIncluded(menu);

        // then
        assertEquals(expected, isIncluded);
    }
```

### Mock Test

정적 메서드가 아닌 경우 

```java
class BridgeMakerTest {

    @Mock
    private BridgeNumberGenerator bridgeNumberGenerator;

    @InjectMocks
    private BridgeMaker bridgeMaker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makeBridgeTestWithUp() {
        when(bridgeNumberGenerator.generate()).thenReturn(1);

        List<String> result = bridgeMaker.makeBridge(5);

        assertEquals(5, result.size());
        assertEquals("U", result.get(0));
    }
}
```


정적 메서드인 경우
```java
private static MockedStatic<Randoms> Randoms;

@BeforeAll
public static void beforeALl() {
    randoms = mockStatic(Randoms.class);
}

@AfterAll
public static void afterAll() {
    randoms.close();
}

// given(모킹할 메소드를 가진 클래스.메소드명()).willReturn(모킹할 값);
BDDMockito.given(Randoms.nextInt(1, 2)).willReturn(5);
```

### @Nested

```java
@Nested
@DisplayName("할인 계산 테스트") 
class DiscountCalculationTest {
    @Test
    public firstTest() {
        // ... 
    }
    
    @Test
    public secondTest() {
        // ...
    }
}
```
