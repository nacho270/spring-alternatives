# Dagger dependency injection demo

A simple demo on how you would use [google dagger](https://dagger.dev/) to manage your dependency injections.

It sort of emulates [google guice](https://github.com/google/guice) but instead of being 100% code based, it relies on annotation based + generated code.

I found it's learning curve is very steep until you realize how it works with a few pros and cons:

- Pros:

	- The code is very clean and simpler to read

- Cons:

	- By being based on generated code you will to tweak your IDE to compile properly or perform a `mvn compile` every time you change your dependencies.
	- There's also not too much documentation or examples.
	
One really useful tip for this is: **Use `@Module` to tell dagger the things it cannot figure out**, ie: datasource configuration, implementation of interfaces.

Of course, the way I have done things is by no means the only one. You can play a bit more on how you would build the modules.

### How to run

You can run this application with two different profiles:

- *_null_*: This is the default one, the one you would use in production for instance. It requires *redis* to be running:

`docker run -p 6379:6379 redis`

`java -jar target/dagger-dependency-injection-demo-0.0.1-SNAPSHOT.jar`

- *dev*: This the one you would use when you run your app locally. Instead of redis, this profiles uses a *Map* to store the information. You would need to add an environment variable called `profile`.

`java -Dprofile=dev -jar target/dagger-dependency-injection-demo-0.0.1-SNAPSHOT.jar`
