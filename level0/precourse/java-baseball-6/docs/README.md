# 💪 프로젝트 개요

컴퓨터가 생성한 숫자를 맞추기 위해 플레이어가 숫자를 입력한다.
컴퓨터는 플레이어의 숫자에 대해 스트라이크, 볼, 낫싱으로 힌트를 제공한다.

# 📝 구현 기능 목록

### 컴퓨터가 숫자를 생성하는 기능

- [x] `숫자 야구 게임을 시작합니다.`를 출력한다.
- [x] 1부터 9까지의 숫자 세 개를 생성한다.

### 플레이어가 숫자를 입력하는 기능

- [x] `숫자를 입력해주세요 : `를 출력한다.
- [x] 숫자를 입력받는다.
    - [x] 빈 문자열이 아님을 검증한다.
    - [x] 길이가 3인 문자열임을 검증한다.
    - [x] 숫자로 이루어진 문자열임을 검증한다.
    - [x] 숫자가 1 이상, 9 이하임을 검증한다.
    - [x] 서로 다른 숫자임을 검증한다.

### 힌트를 생성하는 기능

- [x] 컴퓨터에 저장한 숫자와 입력된 숫자를 비교한다.
- [x] 스트라이크와 볼 개수를 계산하여 반환한다.

### 힌트를 출력하는 기능

- [x] `1볼 1스트라이크`와 같이 볼 개수와 스트라이크 개수를 출력한다.

### 게임을 새로 시작하거나 종료하는 기능

- [x] 스트라이크가 3인 경우 게임을 종료한다.
    - [x] `3개의 숫자를 모두 맞히셨습니다! 게임 종료 \n 게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.`를 출력한다.
    - [x] 1 혹은 2의 입력을 받는다.
- [x] 입력에 따라 게임을 새로 시작하거나 종료한다.
