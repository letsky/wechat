# Mini Program Back End
This is a wechat mini program backend.

## Quick Start

  First, clone the project from github.

```
  git clone https://github.com/letsky/wechat.git
  cd wechat
```

  Create the <code>application.yml</code> in <code>resources</code> folder. Then copy follow items, and enter your property.
  ```
  spring:
    datasource:
      driver-class-name: org.mariadb.jdbc.Driver
      username: 
      password: 
      url: jdbc:mariadb://127.0.0.1:3306/wechat?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
    jpa:
      show-sql: true
      open-in-view: false
    jackson:
      date-format: yyyy-MM-dd HH:mm:ss
    redis:
      database: 0
      host: 127.0.0.1
      port: 6379
    servlet:
      multipart:
        max-request-size: 10MB
        max-file-size: 10MB
    data:
      web:
        pageable:
          default-page-size: 20
          one-indexed-parameters: true
          max-page-size: 100
  logging:
    file: wechat-dev.log
  wechat:
    miniapp:
      app-id:
      secret:
    qiniu:
      access-key:
      secret-key:
      bucket:
      domain:
  ```
  Start the project.
  ```
  mvn spring-boot:run
  ```
## Documents
- API document: https://www.yuque.com/letsky/vtswur/zimd0b
- Introduction document: https://www.yuque.com/letsky/vtswur/gh46y9

## Development environment
- JDK 1.8
- MariaDB 10.3
- Spring Boot 2.x
- Redis
