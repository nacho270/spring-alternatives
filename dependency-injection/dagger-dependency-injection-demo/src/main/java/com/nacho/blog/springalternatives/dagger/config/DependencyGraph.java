package com.nacho.blog.springalternatives.dagger.config;

import com.nacho.blog.springalternatives.dagger.Application;

import dagger.Component;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Slf4j
public class DependencyGraph {

  @Singleton
  @Component(modules = {  ApplicationDevModule.class })
  interface DevComponent {
    Application application();
  }

  @Singleton
  @Component(modules = { ApplicationProdModule.class })
  interface ProdComponent {
    Application application();
  }

  public static Application buildApplication() {
    log.info("Using profile: {}", System.getProperty("profile"));
    if (hasProfile("dev")) {
      return DaggerDependencyGraph_DevComponent.create().application();
    } else {
      return DaggerDependencyGraph_ProdComponent.create().application();
    }
  }

  private static boolean hasProfile(final String profile) {
    return System.getProperty("profile") != null && System.getProperty("profile").contains(profile);
  }

}
