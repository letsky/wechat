# Mini Program Back End

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
      driver-class-name:
      username:
      password:
      url:
    jpa:
      show-sql:  
      open-in-view:
    jackson:
      date-format: yyyy-MM-dd HH:mm:ss
      time-zone: GMT+8
    redis:
      database:
      host:
      port:
  logging:
    file: wechat-dev.log
  wx:
    miniapp:
      config:
        appid: #enter your appid
        secret: #enter your secret
  qiniu:
    enabled: true
    access-key: 
    secret-key: 
    bucket: 
    domain: 
  ```
  Start the project.
  ```
  mvn spring-boot:run
  ```
