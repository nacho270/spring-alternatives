# Apache commons properties

This example uses [apache commons-configuration2](https://commons.apache.org/proper/commons-configuration/userguide/user_guide.html) to read properties files from the classpath.

As you will see, the library takes care of the file reading, parsing and accesing the properties.

However, spring allows you have different properties files depending on the environment and, ultimately, defaulting to a general application.properties.
For this, you will have to write your own code to determine the environment/profile you want to read, ie, define your own naming convention, overriding values, etc.

In this case, I went with a simple approach:
- The environment/profile is read from "profile" enviroment property
- Chose `properties` file format,
- Every file must begin with "application"
- The profile comes after a "-" 
- Same property on a "lower" file overrides the default.

In terms of "overriding", you can choose between 3 combiners, being the `UnionCombiner` the default one.
- UnionCombiner: Repeated keys live in different namespace
- MergeCombiner: For simple keys, the value is replaced. But for complex keys containing different childs, those nodes are merged.
- OverrideCombiner: Same keys are entirely replaced.

As for the format, you can choose between `PropertiesConfiguration`, `XMLConfiguration` or `YAMLConfiguration`.

To read objects I had to implement my own code with reflection. In theory this should also be supported but I couldn't make it work. Not too much documentation about it.
Will need recursion to read nested objects.
