# Authorization Server (인증 서버)

## Goals
1. Token Server
2. NonSocial Auth Server
3. Social Auth Server (KAKAO)

## History
### 2022.08.14
- ADD :: H2 Local Token Publishing

## Tech
- H2, JWT, Spring Security, Redis Server ...

### 2022.09.24
- ADD :: Test User 
```json
{
    "email": "rojae@test.com",
    "password": "password"
}
```

### H2 Setting
- Install H2
  - http://www.h2database.com/html/installation.html
- Enter H2 Console 
- Create Database in console
  - jdbcUrl : jdbc:h2:tcp://localhost/~/auth