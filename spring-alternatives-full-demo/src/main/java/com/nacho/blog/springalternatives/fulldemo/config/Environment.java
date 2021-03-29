package com.nacho.blog.springalternatives.fulldemo.config;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.MergeCombiner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Environment {

  private Configuration config;

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
    // There are different combiners that can be used: OverrideCombiner, MergeCombiner, UnionCombiner
    final CombinedConfiguration configuration = new CombinedConfiguration(new MergeCombiner());
    final List<String> configurationsToLoad = new ArrayList<>();

    // Not very intuitive: First load the specific properties files and then add the default one
    Stream.of(profiles) //
        .filter(p -> isNotBlank(p)) //
        .map(p -> "application-" + p) //
        .forEach(configurationsToLoad::add);
    configurationsToLoad.add("application");

    for (final String config : configurationsToLoad) {
      try {
        configuration.addConfiguration(new Configurations().properties(new File(config) + ".properties"));
      } catch (final ConfigurationException e) {
        log.error(e.getMessage());
      }
    }
    return configuration;
  }
}