package com.nacho.blog.springalternatives.guice.config;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DependencyGraph {

  private final Injector injector;

  public DependencyGraph() {
    log.info("Using profile: {}", System.getProperty("profile"));
    final List<Module> modules = new ArrayList<>();
    modules.add(new ApplicationCommonModule());
    if (hasProfile("dev")) {
      modules.add(new ApplicationDevModule());
    } else {
      modules.add(new ApplicationProdModule());
    }
    injector = Guice.createInjector(modules);
  }

  public void inject(final Object instance) {
    injector.injectMembers(instance);
  }

  public static boolean hasProfile(final String profile) {
    return System.getProperty("profile") != null && System.getProperty("profile").contains(profile);
  }

}
