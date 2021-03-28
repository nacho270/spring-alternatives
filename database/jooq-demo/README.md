# JOOQ demo without Spring

A simple application that uses [JOOQ](https://www.jooq.org/) with utilising spring. Effectively, this app is a reduced version of my [JOOQ demo with codegen](https://github.com/nacho270/db-libraries-demos/tree/master/jooq-with-codegen-demo)

It is true that I already had a working example, but I found it super easy to remove spring and just make it work.

I did not bother with testing, properties, rest apis or dependency injection here... for that, go to this [spring-alternatives-full-demo](https://github.com/nacho270/spring-alternatives/tree/master/(spring-alternatives-full-demo)

## How to run

- Pull mysql: `docker run --name jooq -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest`
- Connect to the db and run the `init.sql` script. 
- Perform a clean install: `mvn clean install`
- Run the app from: `com.nacho.blog.springalternatives.jooqdemo.Application` or from the terminal: `java -jar target/jooq-demo-0.0.1-SNAPSHOT.jar`

