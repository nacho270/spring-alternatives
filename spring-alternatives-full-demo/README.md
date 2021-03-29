# Spring + JOOQ (With codegen)

A simple spring-boot application using JOOQ.

Instead of just using the standard JOOQ libraries, it uses:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jooq</artifactId>
</dependency>
```

This (almost) the same as using:

```xml
<dependency>
  <groupId>org.jooq</groupId>
  <artifactId>jooq</artifactId>
</dependency>
<dependency>
  <groupId>org.jooq</groupId>
  <artifactId>jooq-meta</artifactId>
</dependency>
<dependency>
  <groupId>org.jooq</groupId>
  <artifactId>jooq-codegen</artifactId>
</dependency>
```

This version makes use of the JOOQ codegen capabilities and you'll see that's somehow easier and maintenable than the no-codegen version [here](https://github.com/nacho270/db-libraries-demos/tree/master/jooq-no-codegen-demo).

After booting, the app will insert a sample set of products.

## Caveats

I thought it was going to **way** easier to work with the codegen solution but i found a few things:

- The codegen does reverse engineering on the DB to create the clases. So, the process relies on DB connectivity and having the tables already in place.
- The mapping works fine for queries witout joins but i couldn't find an answer for the `getById` that has an `inner join` and, thus, it brings many rows per shipment.
- A potential option for this 2 could be to rely on JPA for mapping and creating the schema (either directly through JPA or by using liquibase)
- I had to change the table names in order to avoid confusion with my POJOs.
- Given that the schema is not created by the app, i had to add a custom H2 script for the tests.
- Given that the schema needs to be there before hand, i had to hid the pre-loading of the DB behind a profile so it wouldn't fail when running the tests.

## What you'll see

- Insert/query examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/service/ProductService.java` and `src/main/java/com/nacho/blog/jooq/jooqdemo/service/ShipmentService.java`
- Count and Delete examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/repository/impl/ShipmentRepositoryImpl.java`

## How to run

The generated files are already inside the app. If you want to re generate run maven with the `codegen` profile: `mvn -Pcodegen clean install -DskipTests`

### From IDE
- Pull mysql: `docker run --name jooq -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest`
- connect to the db and run the `init.sql` script.
- Run the app with maven `mvn spring-boot:run` or through the main class here: `src/main/java/com/nacho/blog/jooq/jooqdemo/JooqDemoApplication.java`
- After booting, the app will create the tables and insert a sample set of products.

### Docker

- cd into `jooq-with-codegen-demo`
- `docker-compose up`

This will start a dockerized `mysql` with the schema and tables already there.

### Tests



### Sample curls

- `curl localhost:8080/product`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/product -d '{"name":"keyboard","price":30.5}'`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/shipment -d '{"items":[{"product":"e82658bf-cd8b-471c-9711-c0ea77733bdb","quantity":2}, {"product":"db909c72-7aaa-4805-be7b-b1579a9cb2c0","quantity":1}]}'`
- `curl localhost:8080/shipment/20f57155-cf88-4527-ab30-a5c079f9d358`
