<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-6.14.15-blue">
  <img alt="node" src="https://img.shields.io/badge/node-14.18.2-blue">
  <a href="https://edu.nextstep.camp/c/R89PYi5H" alt="nextstep atdd">
    <img alt="Website" src="https://img.shields.io/website?url=https%3A%2F%2Fedu.nextstep.camp%2Fc%2FR89PYi5H">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/next-step/atdd-subway-admin">
</p>

<br>

# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

<br>

## 🚀 Getting Started

### Install
#### npm 설치
```
cd frontend
npm install
```
> `frontend` 디렉토리에서 수행해야 합니다.

### Usage
#### webpack server 구동
```
npm run dev
```
#### application 구동
```
./gradlew bootRun
```
<br>

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/next-step/atdd-subway-service/issues) 에 등록해주세요 :)

<br>

## 📝 License

This project is [MIT](https://github.com/next-step/atdd-subway-service/blob/master/LICENSE.md) licensed.

## 1단계 - 인수 테스트 기반 리팩터링

---

### 요구사항
- LineService 리팩터링
  - LineService의 비즈니스 로직을 도메인으로 옮기기
  - 한번에 많은 부분을 고치려 하지 말고 나눠서 부분부분 리팩터링하기
  - 전체 기능은 인수 테스트로 보호한 뒤 세부 기능을 TDD로 리팩터링하기
- LineSectionAcceptanceTest 리팩터링
  - API를 검증하기 보다는 시나리오, 흐름을 검증하는 테스트로 리팩터링 하기

- [x] LineService 리팩터링
  - [x] 노선 저장하기 리팩터링
  - [x] 구간 추가하기 리팩터링
  - [x] 구간 제거하기 리팩터링
- [x] LineSectionAcceptanceTest 리팩터링


## 2단계 - 경로 조회 기능

---

### 요구사항
- 최단 경로 조회 인수 테스트 만들기
- 최단 경로 조회 기능 구현하기

- [x] 최단 경로 조회 인수 테스트 만들기
- [ ] 최단 경로 조회 기능 구현하기
  - [ ] 예외
    - [x] 출발역과 도착역이 같은 경우
    - [x] 출발역과 도착역이 연결이 되어 있지 않은 경우
    - [ ] 존재하지 않은 출발역이나 도착역을 조회 할 경우
