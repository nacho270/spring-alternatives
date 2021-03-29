package com.nacho.blog.springalternatives.fulldemo.config;

import com.nacho.blog.springalternatives.fulldemo.UrlMappings;

import dagger.Component;

public class DependencyGraph {

  @Component(modules = { ApplicationCommonModule.class })
  interface ProdComponent {
    UrlMappings application();
  }

  public static UrlMappings buildApplication() {
    if (hasProfile("dev")) {
//      return DaggerDependencyGraph_DevComponent.create().application();
    } else {
    }
    return DaggerDependencyGraph_ProdComponent.create().application();
  }

  private static boolean hasProfile(final String profile) {
    return System.getProperty("profile") != null && System.getProperty("profile").contains(profile);
  }
}
