package com.nacho.blog.springalternatives.dagger;

import com.nacho.blog.springalternatives.dagger.config.DependencyGraph;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(final String[] args) {
    final Application application = DependencyGraph.buildApplication();
    log.info("{}", application.calculate(7d));
    application.store("Nacho");
    log.info("User 1: {}", application.get(1));
  }
}
