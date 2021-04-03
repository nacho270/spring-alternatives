package com.nacho.blog.springalternatives.apacheproperties;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.MergeCombiner;

/**
 * A very limited implementation for reading properties. Must be used with simple values. <br>
 * Works fine when you know the type you want to read. <br>
 * Couldn't make it read full objects or lists even with the separator. <br>
 * Should implement recursion to read nested objects. <br>
 */
public class Environment {

  private Configuration config;
  private static final char LIST_DELIMITER = ',';

  public Environment() {
    System.out.println("Using profiles: " + System.getProperty("profile"));
    final var profile = Optional.ofNullable(System.getProperty("profile")).orElse("").split(",");
    config = loadProperties(profile);
  }

  private static Configuration loadProperties(final String[] profiles) {
    // There are different combiners that can be used: OverrideCombiner, MergeCombiner, UnionCombiner
    final CombinedConfiguration configuration = new CombinedConfiguration(new MergeCombiner());
    configuration.setListDelimiterHandler(new DefaultListDelimiterHandler(LIST_DELIMITER));
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

    System.getenv().forEach((k, v) -> {
      if (configuration.containsKey(k)) {
        System.out.println("Overriding property " + k + " with value " + v);
      }
      configuration.setProperty(k, v);
    });

    return configuration;
  }

  public String getString(final String key) {
    return config.getString(key);
  }

  public <T> T getObject(final String keyPrefix, final Class<T> clazz) {
    // this whole code is not very nice.
    // Maybe it's a short comming from the library but i couldn't get it to read a full object
    try {
      final T newInstance = clazz.getConstructor().newInstance();
      final Configuration subset = config.subset(keyPrefix);
      for (final Iterator<String> it = subset.getKeys(); it.hasNext();) {
        final String attr = it.next();
        BeanUtils.setProperty(newInstance, attr, determineValue(subset, attr, clazz));
      }
      return newInstance;
    } catch (final Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private <T> Object determineValue(final Configuration conf, final String attribute, final Class<T> clazz) throws Exception {
    // this is obviously limited.
    final Class<?> type = clazz.getDeclaredField(attribute).getType();
    if (type.equals(List.class)) {
      // the config.getList just didn't work, even with the separator
      return Arrays.asList(conf.getString(attribute).split(String.valueOf(LIST_DELIMITER)));
    }
    return conf.get(type, attribute);
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
