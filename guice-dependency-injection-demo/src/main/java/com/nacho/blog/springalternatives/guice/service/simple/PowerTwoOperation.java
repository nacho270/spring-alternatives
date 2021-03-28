package com.nacho.blog.springalternatives.guice.service.simple;

public class PowerTwoOperation implements Operation {

  @Override
  public Double apply(final Double number) {
    return Math.pow(number, 2D);
  }

}
