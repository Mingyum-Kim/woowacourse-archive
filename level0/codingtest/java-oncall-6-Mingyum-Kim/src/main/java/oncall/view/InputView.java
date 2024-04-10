package oncall.view;

import java.util.Arrays;
import java.util.List;
import oncall.controller.dto.MonthAndStartDayOfWeek;
import oncall.controller.dto.WorkersName;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;
import oncall.view.console.ConsoleReader;
import oncall.view.console.ConsoleWriter;

public class InputView {
    public MonthAndStartDayOfWeek readMonthAndStartDayOfWeek() {
        ConsoleWriter.printMessage("비상 근무를 배정할 월과 시작 요일을 입력하세요> ");
        return Validator.validateMonthAndStartDayOfWeek(
                ConsoleReader.enterMessage()
        );
    }

    public WorkersName readWorkerInfo() {
        ConsoleWriter.printMessage("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
        List<String> weekday = Validator.validateWorkers(ConsoleReader.enterMessage());
        ConsoleWriter.printMessage("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
        List<String> weekend = Validator.validateWorkers(ConsoleReader.enterMessage());
        return new WorkersName(
                weekday,
                weekend
        );
    }

    private static class Validator {
        private static MonthAndStartDayOfWeek validateMonthAndStartDayOfWeek(
                String message
        ) {
            List<String> inputs = parseStringToList(message, ",");
            validateSeparators(inputs);
            return new MonthAndStartDayOfWeek(
                    validateMonth(inputs.get(0)),
                    inputs.get(1)
            );
        }

        private static List<String> parseStringToList(String message, String separator) {
            return Arrays.stream(split(message, separator)).toList();
        }

        private static String[] split(String message, String separator) {
            return message.split(separator, -1);
        }

        private static void validateSeparators(List<String> inputs) {
            if (inputs.size() != 2) {
                throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
            }
        }

        private static int validateMonth(String message) {
            return validateNumber(message, ErrorMessage.INVALID_INPUT_ERROR);
        }

        public static int validateNumber(String message, ErrorMessage errorMessage) {
            if (isNotNumber(message)) {
                throw CustomException.from(errorMessage);
            }
            return Integer.parseInt(message);
        }

        private static boolean isNotNumber(String str) {
            return !str.matches("\\d+");
        }

        public static List<String> validateWorkers(String message) {
            List<String> names = parseStringToListWithoutLimit(message, ",");
            validateDuplicatedItem(names);
            return names;
        }

        private static List<String> parseStringToListWithoutLimit(String message, String separator) {
            return Arrays.stream(splitWithoutLimit(message, separator)).toList();
        }

        private static String[] splitWithoutLimit(String message, String separator) {
            return message.split(separator);
        }

        private static void validateDuplicatedItem(List<String> items) {
            if (hasDuplicatedItem(items)) {
                throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
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
    }
}
