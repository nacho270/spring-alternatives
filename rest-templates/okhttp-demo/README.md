# Javalin demo

A simple demo on how you would use [Javalin](https://javalin.io/) to create restful apis.

I haven't focused on this, but with library you can also serve static content and render templates with various engines such as velocity and mustache.

It's a fork of [Spark Java](https://sparkjava.com/) so, just like it, it's super light-weight and quick to setup, it just works out-of-the-box.

These are the differences they claim to have at the moment: https://javalin.io/comparisons/sparkjava

### How to run

Execute in the IDE through `com.nacho.blog.springalternatives.javalin.Main`. 

Or through the console: `java -jar target/javalin-restapi-demo-0.0.1-SNAPSHOT.jar`

The web will start server on port 8080.

Once it's running you can run the following `curl` commands:

- Simple ping: `curl localhost:8080/ping`
- Example of reading a parm from the path: `curl localhost:8080/hello/user`
- Create a user: `curl -XPOST localhost:8080/user -d '{ "id": 1, "name":"the user" }'`
- Get user by id: `curl localhost:8080/user/1`
- Delete user by id: `curl -XDELETE localhost:8080/user/1`
- Search by name: `curl 'localhost:8080/user/search?name=user'`

### Error requests
- Search non-existing id will return 404 - not found: `curl localhost:8080/user/1000`
- Posting a malformed by will return 400 - bad request: `curl -XPOST localhost:8080/user -d 'hello'`
- Deleting with name instead of if will return 500 - Server error: `curl -XDELETE localhost:8080/user/hello` -> 500 - Server error

### Tests

For the sake of variete i have used [Unirest](http://kong.github.io/unirest-java/) for the tests. I simply start the app and rely on rest assured to issue the requests and make some assertions.
