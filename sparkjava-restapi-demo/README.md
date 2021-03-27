# Spark java demo

A simple demo on how you would use [Spark Java](https://sparkjava.com/) to create restful apis.

I haven't focused on this, but with library you can also serve static content and render templates with various engines such as velocity and mustache.

I found this framework to be super light-weight and quick to setup, it just works out-of-the-box.

### How to run

Execute in the IDE through `com.nacho.blog.springalernatives.sparkweb.Main`. 

Or through the console: `java  -jar target/sparkjava-restapi-demo-0.0.1-SNAPSHOT.jar`

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

I have used [Rest Assured](https://rest-assured.io/) for the tests. I simply start the app and rely on rest assured to issue the requests and make some assertions.
