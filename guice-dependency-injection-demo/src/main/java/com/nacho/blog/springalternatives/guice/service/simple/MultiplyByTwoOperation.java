package com.nacho.blog.springalternatives.guice.service.simple;

public class MultiplyByTwoOperation implements Operation {

  @Override
  public Double apply(final Double number) {
    return number * 2;
  }

}
