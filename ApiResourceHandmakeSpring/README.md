# JPA CRUD by handmade

## Update Configuration

in **src/main/resources/application.yml** change it accordingly for you

```
url: jdbc:mysql://localhost:3306/YourDatabase
    username: YourUsername
    password: YourPassword
```

Note:
- You must run your server before testing application
- If you run on **window** you may have to install maven and set up your **env variable**

## Run Application

### Linux And Window

```
mvn spring-boot:run
```

## Testing Application

```
mvn test -Dsurefire.useFile=false
```

## List Api Resource
- http://localhost:8080/products (for **get**)
- http://localhost:8080/products/{id} (for **post**, **put**, **get** **delete**)
- http://localhost:8080/categories (for **get**)
- http://localhost:8080/categories/{id}/products (for **get**)
- http://localhost:8080/categories/{id} (for **post**, **put**, **get** **delete**)