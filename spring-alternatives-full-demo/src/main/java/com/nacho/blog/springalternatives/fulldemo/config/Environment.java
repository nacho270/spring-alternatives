package com.nacho.blog.springalternatives.fulldemo.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.MergeCombiner;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * there many limitations in this code, see here:
 * https://github.com/nacho270/spring-alternatives/blob/master/properties/apache-commons-properties-demo/src/main/java/com/nacho/blog/springalternatives/apacheproperties/Environment.java
 */
@Slf4j
@Singleton
public class Environment {

  private final Configuration config;
  private static final char LIST_DELIMITER = ',';

  @Inject
  public Environment() {
    log.info("Using profiles: {}", System.getProperty("profile"));
    final var profile = Optional.ofNullable(System.getProperty("profile")).orElse("").split(",");
    config = loadProperties(profile);
  }

  public String getString(final String key) {
    return config.getString(key);
  }

  public Integer getInt(final String key, final Integer defaultValue) {
    return config.getInteger(key, defaultValue);
  }

  public <T> T getObject(final String keyPrefix, final Class<T> clazz) {
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
    final Class<?> type = clazz.getDeclaredField(attribute).getType();
    if (type.equals(List.class)) {
      return Arrays.asList(conf.getString(attribute).split(String.valueOf(LIST_DELIMITER)));
    }
    return conf.get(type, attribute);
  }

  private static Configuration loadProperties(final String[] profiles) {
    final CombinedConfiguration configuration = new CombinedConfiguration(new MergeCombiner());
    configuration.setListDelimiterHandler(new DefaultListDelimiterHandler(LIST_DELIMITER));
    final List<String> configurationsToLoad = new ArrayList<>();

    Stream.of(profiles) //
        .filter(StringUtils::isNotBlank) //
        .map(p -> "application-" + p) //
        .forEach(configurationsToLoad::add);
    configurationsToLoad.add("application");

    for (final var config : configurationsToLoad) {
      try {
        configuration.addConfiguration(new Configurations().properties(new File(config) + ".properties"));
      } catch (final ConfigurationException e) {
        log.error(e.getMessage());
      }
    }

    System.getenv().forEach((k, v) -> {
      if (configuration.containsKey(k)) {
        log.info("Overriding property {} with value {}", k, v);
      }
      configuration.setProperty(k, v);
    });

    return configuration;
  }
}
