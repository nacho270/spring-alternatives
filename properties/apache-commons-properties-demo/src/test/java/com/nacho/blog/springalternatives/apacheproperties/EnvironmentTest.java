package com.nacho.blog.springalternatives.apacheproperties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnvironmentTest {

  @BeforeEach
  public void clearEnv() {
    System.clearProperty("profile");
  }

  @Test
  @DisplayName("Should just read from common file")
  public void testReadFromCommonFile() {
    final var env = new Environment();
    assertThat(env.getString("simpleproperty")).isEqualTo("my value");
    assertThat(env.getString("propertyToOverride")).isEqualTo("this is to override");
  }

  @Test
  @DisplayName("Should read from common and dev file")
  public void testReadFromCommonAndDevFile() {
    System.setProperty("profile", "dev");
    final var env = new Environment();
    assertThat(env.getString("simpleproperty")).isEqualTo("my value");
    assertThat(env.getString("propertyToOverride")).isEqualTo("this is was overriden by the dev profile");
  }

  @Test
  @DisplayName("Should read complex object")
  public void testReadComplexObject() {
    final var env = new Environment();
    final ObjectConfig objectConfig = env.getObject("object", ObjectConfig.class);
    assertThat(objectConfig.getPropertyString()).isEqualTo("property 1");
    assertThat(objectConfig.getPropertyInteger()).isEqualTo(5);
    assertThat(objectConfig.getPropertyListOfString()).contains("one", "two", "three");
  }

  public static class ObjectConfig {

    private String propertyString;
    private Integer propertyInteger;
    private List<String> propertyListOfString;

    public ObjectConfig() {
      super();
    }

    public String getPropertyString() {
      return propertyString;
    }

    public void setPropertyString(final String propertyString) {
      this.propertyString = propertyString;
    }

    public Integer getPropertyInteger() {
      return propertyInteger;
    }

    public void setPropertyInteger(final Integer propertyInteger) {
      this.propertyInteger = propertyInteger;
    }

    public List<String> getPropertyListOfString() {
      return propertyListOfString;
    }

    public void setPropertyListOfString(final List<String> propertyListOfString) {
      this.propertyListOfString = propertyListOfString;
    }

  }
}
