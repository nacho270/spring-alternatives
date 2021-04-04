# Retrofit demo

A simple demo of [Retrofit](https://square.github.io/retrofit/). For this, i call the different endpoints offered by [jsonplaceholder](https://jsonplaceholder.typicode.com/guide/).

This library is a high-level rest client quite similar to [Spring feign](https://cloud.spring.io/spring-cloud-openfeign/reference/html/) where you simply define an interface 
annotated with the request information (`path, method, params, query, headers, etc`) and then the library takes care of connectivity, serialisation/deserialisation, etc 
under the hood using [OkHttp](https://square.github.io/okhttp/), also covered [here](https://github.com/nacho270/spring-alternatives/tree/master/rest-templates/okhttp-demo).

It can perform sync/async querys and it's quite popular around android developers.

The only caveat is that I had to manually end the application given some retrofit threads were simply parked.

## How to run

Execute in the IDE through `com.nacho.blog.springalternatives.retrofit.Main`. 

Or through the console: `java -jar target/retrofit-demo-0.0.1-SNAPSHOT.jar`

