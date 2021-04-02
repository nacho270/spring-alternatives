# Spring alternatives full demo

A fully-fledged application that utilises all the libraries showcased in the repository.

The domain is similar to the one I used in my [JOOQ demo](https://github.com/nacho270/db-libraries-demos).

- Create products
- Create shipments. For this you will need:
  - One or more product ids created at boot
  - A user id. The user id has to be a number such as 1, 2, 3, etc... and it's retrieved from this endpoint: https://jsonplaceholder.typicode.com/users/1

## Tools/Libraries

- Java 11
- Docker
- [Apache commons configuration 2](https://github.com/nacho270/spring-alternatives/tree/master/properties/apache-commons-properties-demo) for the configuration properties.
- [Google dagger](https://github.com/nacho270/spring-alternatives/tree/master/dependency-injection/dagger-dependency-injection-demo) for the dependency injection.
- [Jooq](https://github.com/nacho270/spring-alternatives/tree/master/database/jooq-demo) for the DB access.
- [Javalin](https://github.com/nacho270/spring-alternatives/tree/master/rest-apis/javalin-restapi-demo) for the rest api.
- [Retrofit](https://github.com/nacho270/spring-alternatives/tree/master/rest-templates/retrofit-demo) for rest-template.

## How to run

As stated, the application relies on Jooq and Dagger which, in turn, depend on code generation. In particular, Jooq depends on a DB connection to generate the classes.

1- Pull mysql: `docker run --name alternatives_demo -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest` 

2- Connect to the DB and run the `init.sql` script.

3- Run `mvn clean install`

4- Run the app:

    - From the IDE using the main class src/main/java/com/nacho/blog/springalternatives/fulldemo/Main.java
    - From the console: java -jar target/spring-alternatives-full-demo-0.0.1-SNAPSHOT-jar-with-dependencies.jar
    - From docker: docker-compose up. This may take a minute or 2 to start

### Sample curls

- `curl localhost:8080/ping`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/shipment -d '{"userId":5, "items":[{"product":"2deff5ae-7ec2-48be-a2c4-90ea7ffd3c85","quantity":2}, {"product":"d0100c7a-1e18-4cdf-bfdc-a132fe1b3de7","quantity":1}]}'`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/product -d '{"name":"keyboard nacho","price":30.5}'`
- `curl localhost:8080/shipment/20f57155-cf88-4527-ab30-a5c079f9d358`
