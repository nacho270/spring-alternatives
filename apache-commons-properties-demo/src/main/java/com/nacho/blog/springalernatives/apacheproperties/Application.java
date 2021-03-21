package com.nacho.blog.springalernatives.apacheproperties;

public class Application {

  private Environment e;

  public Application() {
    e = new Environment();
    System.out.println("simpleproperty: " + e.getString("simpleproperty"));
    System.out.println("propertyToOverride: " + e.getString("propertyToOverride"));
  }

  public static void main(final String[] args) {
    new Application();
  }
}
