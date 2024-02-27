# 화폐 예제

## 기능 요구사항 
다중 화폐를 다루는 보고서를 만들기 위해 화폐 연산 기능을 구현한다.

## 기능 구현 목록
- [ ] 통화가 다른 두 금액을 더해서 환율에 맞게 변환한 금액을 구한다.
- [x] 주가를 주식의 수로 곱한 금액을 구한다.
    - [x] Dollar 부작용 해결 - 값 객체 패턴으로 정의
    - [x] 다른 Dollar와 equals 정의
    - [ ] hashcode 정의
    - [ ] null과의 equals 정의
    - [ ] Object와의 equals 정의
- [ ] Franc 객체가 Dollar와 동일하게 작동할 수 있도록 하기
  - [ ] Dollar와 Franc의 중복 제거
  - [x] 공통 equals 구현
  - [ ] 공통 times 구현
