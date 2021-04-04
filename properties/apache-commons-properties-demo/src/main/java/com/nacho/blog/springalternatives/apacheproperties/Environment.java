package com.nacho.blog.springalternatives.apacheproperties;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.MergeCombiner;

/**
 * A very limited implementation for reading properties. Must be used with simple values. <br>
 * Should implement recursion to read nested objects. <br>
 */
public class Environment {

  private static final Class<PropertiesConfiguration> DEFAULT_CONFIGURATION_FILE_TYPE = PropertiesConfiguration.class;
  private static final MergeCombiner DEFAULT_COMBINER = new MergeCombiner();
  private static final String DEFAULT_CONFIG_FILE_NAME = "application";
  private static final String CONFIG_FILE_PREFIX = "application-";
  private static final String PROPERTIES_FILE_EXTENSION = ".properties";
  private static final char LIST_DELIMITER = ',';

  private Configuration config;

  public Environment() {
    System.out.println("Using profiles: " + System.getProperty("profile"));
    final var profile = Optional.ofNullable(System.getProperty("profile")).orElse("").split(",");
    config = loadProperties(profile);
  }

  public String getString(final String key) {
    return config.getString(key);
  }

  public <T> T getObject(final String keyPrefix, final Class<T> clazz) {
    try {
      final Configuration subset = config.subset(keyPrefix);
      final T newInstance = clazz.getConstructor().newInstance();
      for (final Iterator<String> it = subset.getKeys(); it.hasNext();) {
        final String attr = it.next();
        BeanUtils.setProperty(newInstance, attr, determineValue(subset, attr, clazz.getDeclaredField(attr).getType()));
      }
      return newInstance;
    } catch (final Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private <T> Object determineValue(final Configuration conf, final String attribute, final Class<T> type) throws Exception {
    // this is obviously limited.
    if (type.equals(List.class)) {
      return conf.getList(attribute);
    } else if (type.equals(Set.class)) {
      return new HashSet<>(conf.getList(attribute));
    }
    return conf.get(type, attribute);
  }

  private static Configuration loadProperties(final String[] profiles) {
    // There are different combiners that can be used: OverrideCombiner, MergeCombiner, UnionCombiner
    final CombinedConfiguration configuration = new CombinedConfiguration(DEFAULT_COMBINER);

    // Not very intuitive: First load the specific properties files and then add the default one
    final List<String> configurationsToLoad = //
        Stream.of(profiles) //
            .filter(p -> !isBlank(p)) //
            .map(p -> CONFIG_FILE_PREFIX + p) //
            .collect(collectingAndThen( //
                toList(), //
                confs -> { // this code runs AFTER the toList. So the "application" is added at the end
                  confs.add(DEFAULT_CONFIG_FILE_NAME);
                  return confs;
                }));

    // for the sake of readability do a separate stream... but it could be attatched to the collect from above
    configurationsToLoad.stream() //
        .map(Environment::propertiesFileBuilder) //
        .map(Environment::safeGetConfiguration) //
        .forEach(configuration::addConfiguration);

    // useful when using docker where you may want to set environment variables in the docker-compose or docker run
    System.getenv().forEach((k, v) -> {
      if (configuration.containsKey(k)) {
        System.out.println("Overriding property " + k + " with value " + v);
      }
      configuration.setProperty(k, v);
    });

    return configuration;
  }

  private static PropertiesConfiguration safeGetConfiguration(final FileBasedConfigurationBuilder<PropertiesConfiguration> configurationBuilder) {
    try {
      return configurationBuilder.getConfiguration();
    } catch (final ConfigurationException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private static FileBasedConfigurationBuilder<PropertiesConfiguration> propertiesFileBuilder(final String config) {
    // here you can choose between PropertiesConfiguration.class, YAMLConfiguration.class or XMLConfiguration.class!
    final var propertiesParams = new Parameters()//
        .fileBased() //
        .setFile(new File(config + PROPERTIES_FILE_EXTENSION)) //
        .setListDelimiterHandler(new DefaultListDelimiterHandler(LIST_DELIMITER));
    return new FileBasedConfigurationBuilder<>(DEFAULT_CONFIGURATION_FILE_TYPE).configure(propertiesParams);
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
