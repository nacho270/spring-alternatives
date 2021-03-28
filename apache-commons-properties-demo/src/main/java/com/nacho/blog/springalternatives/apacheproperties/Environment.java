package com.nacho.blog.springalternatives.apacheproperties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.MergeCombiner;

public class Environment {

  private Configuration config;

  public Environment() {
    System.out.println("Using profiles: " + System.getProperty("profile"));
    final var profile = Optional.ofNullable(System.getProperty("profile")).orElse("").split(",");
    config = loadProperties(profile);
  }

  public String getString(final String key) {
    return config.getString(key);
  }

  private static Configuration loadProperties(final String[] profiles) {
    // There are different combiners that can be used: OverrideCombiner, MergeCombiner, UnionCombiner
    final CombinedConfiguration configuration = new CombinedConfiguration(new MergeCombiner());
    final List<String> configurationsToLoad = new ArrayList<>();

    // Not very intuitive: First load the specific properties files and then add the default one
    Stream.of(profiles) //
        .filter(p -> !isBlank(p)) //
        .map(p -> "application-" + p) //
        .forEach(configurationsToLoad::add);
    configurationsToLoad.add("application");

    for (final String config : configurationsToLoad) {
      try {
        configuration.addConfiguration(new Configurations().properties(new File(config) + ".properties"));
      } catch (final ConfigurationException e) {
        System.out.println(e.getMessage());
      }
    }
    return configuration;
  }

  private static boolean isBlank(final CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (Character.isWhitespace(cs.charAt(i)) == false) {
        return false;
      }
    }
    return true;
  }

}
