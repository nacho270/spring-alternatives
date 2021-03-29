package com.nacho.blog.springalternatives.fulldemo;

import com.nacho.blog.springalternatives.fulldemo.config.DependencyGraph;

public class Main {

  public static void main(final String[] args) {
    DependencyGraph.buildApplication().start();
  }
}
