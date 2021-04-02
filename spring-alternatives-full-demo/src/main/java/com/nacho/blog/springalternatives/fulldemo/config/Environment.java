package com.nacho.blog.springalternatives.fulldemo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.MergeCombiner;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Singleton
public class Environment {

  private final Configuration config;

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

  private static Configuration loadProperties(final String[] profiles) {
    CombinedConfiguration configuration = new CombinedConfiguration(new MergeCombiner());
    List<String> configurationsToLoad = new ArrayList<>();

    Stream.of(profiles) //
            .filter(StringUtils::isNotBlank) //
            .map(p -> "application-" + p) //
            .forEach(configurationsToLoad::add);
    configurationsToLoad.add("application");

    for (var config : configurationsToLoad) {
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
