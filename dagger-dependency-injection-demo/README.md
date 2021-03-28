# Dagger dependency injection demo

A simple demo on how you would use [google dagger](https://dagger.dev/) to manage your dependency injections.

You will see that you will need to do many things manually like handling different profiles.

Of course, the way I have done things is by no means the only one. You can play a bit more on how you would build the modules.

For JUnit5 you will need a non-official extension`name.falgout.jeffrey.testing.junit5`, although the creator works for google.

### How to run

You can run this application with two different profiles:

- *_null_*: This is the default one, the one you would use in production for instance. It requires *redis* to be running:

`docker run -p 6379:6379 redis`
` java  -jar target/guice-dependency-injection-demo-0.0.1-SNAPSHOT.jar`

- *dev*: This the one you would use when you run your app locally. Instead of redis, this profiles uses a *Map* to store the information. You would need to add an environment variable called `profile`.
`java -Dprofile=dev -jar target/guice-dependency-injection-demo-0.0.1-SNAPSHOT.jar`
