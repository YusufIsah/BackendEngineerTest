# dynamic-data-mapper project

## Running the application in dev

You can run your application in dev mode with below command:
```
./mvnw compile quarkus:dev

```
You can run the unit and integration with below command:
```
./mvnw clean test

```

##Note: jdk version is 11

- Load Provider Data : localhost:8080/data
- Filter Provider Data After Load: localhost:8080/data/filter/12345?name=eqc:ciroma&age=eq:20&timestamp=gt:​1587614029

