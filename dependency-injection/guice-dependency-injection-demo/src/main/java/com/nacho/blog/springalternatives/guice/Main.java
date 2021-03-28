package com.nacho.blog.springalternatives.guice;

import com.nacho.blog.springalternatives.guice.config.DependencyGraph;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(final String[] args) {
    final Application application = new Application();
    new DependencyGraph().inject(application);

    final double input = 7D;
    log.info("Value for {}: {}", input, application.calculate(input));
    application.store("Nacho");
    log.info("User 1: {}", application.get(1));
  }
}
