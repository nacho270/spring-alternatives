package com.nacho.blog.springalternatives.dagger.config;

import com.nacho.blog.springalternatives.dagger.Application;

import dagger.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DependencyGraph {

  @Component(modules = { ApplicationCommonModule.class, ApplicationDevModule.class })
  interface DevComponent {
    Application application();
  }

  @Component(modules = { ApplicationCommonModule.class, ApplicationProdModule.class })
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
