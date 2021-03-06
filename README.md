# Test Coverage Perfect demo

테스트 100% 커버리지 시도해보기

[TOSS SLASH21 — 테스트커버리지 100% 요약/DEMO](https://byungkyu-ju.medium.com/toss-slash21-%ED%85%8C%EC%8A%A4%ED%8A%B8%EC%BB%A4%EB%B2%84%EB%A6%AC%EC%A7%80-100-%EC%9A%94%EC%95%BD-demo-2fb8b52cf2a9)

## How to build

```
 ./gradlew build
```

## How to test

cover test, jacocotest, openapi3 all
```
 ./gradlew openapi3
```


## API Spec - example

/static/swagger.html
![image info](screenshot.png)

## API Spec - manual typo

### 1. 회원가입

**URL** : `/members`    
**Method** : `POST`  
**Auth required** : NO   
**Permissions required** : None  

#### Request

```json
{
    "email": "[unicode 64 chars max]",
    "password" : "",
    "passwordConfirm" : "",
    "nickname" : ""
}
```

#### Success Response
**Status** : `201 CREATED`  
**Header** : `/members/{memberId}`

#### Error Responses

**Status** : `400 BAD REQUEST`
```json
{
  "code": "ILLEGAL_ARGUMENT_EXCEPTION",
  "message": "입력한 비밀번호가 일치하지 않습니다."
}
```
```json
{
  "code": "ILLEGAL_ARGUMENT_EXCEPTION",
  "message": "중복된 이메일이 존재합니다."
}
```

### 2. 회원조회

**URL** : `/members`    
**Method** : `GET`  
**Auth required** : NO   
**Permissions required** : None

#### Request

**URL** : `/members/{memberId}`

#### Success Response
**Status** : `200 OK`

```json
{
  "id": 1,
  "email": "tester12@gmail.com",
  "nickname": "nick"
}
```

#### Error Responses

**Status** : `400 BAD REQUEST`
```json
{
  "code": "ILLEGAL_ARGUMENT_EXCEPTION",
  "message": "회원정보를 찾을 수 없습니다."
}
```

## Database
- URL : `/h2-console`
- JDBC URL : `jdbc:h2:mem:testCoveragePerfectDemo`
- User Name : `sa`
- Password: `sa`

## dependency

- rest-assured
- restdocs-restassured
- restdocs-api-spec-restassured


## ETC
- 테스트 커버리지 100% (Speaker 이응준-토스뱅크) 
- https://toss.im/slash-21/sessions/1-6 , https://youtu.be/jdlBu2vFv58
