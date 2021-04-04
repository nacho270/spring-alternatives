# OkHttp demo

A simple demo of [okhttp](https://square.github.io/okhttp/). For this, i call the different endpoints offered by [jsonplaceholder](https://jsonplaceholder.typicode.com/guide/).

This is a low level implementation of an http client where the developer must do everything manually, [Retrofit](https://square.github.io/retrofit/) is built on top of this.

It can perform sync/async querys and it's quite popular around android developers.

The only caveat is that I had to manually end the application given okhttp threads were simply parked, same thing that happens with retrofit [here](https://github.com/nacho270/spring-alternatives/tree/master/rest-templates/retrofit-demo).
Though, as per this site https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/#shutdown-isnt-necessary, it shouldn't be necessary to finish it manually.

## How to run

Execute in the IDE through `com.nacho.blog.springalternatives.okhttp.Main`. 

Or through the console: `java -jar target/okhttp-demo-0.0.1-SNAPSHOT.jar`

