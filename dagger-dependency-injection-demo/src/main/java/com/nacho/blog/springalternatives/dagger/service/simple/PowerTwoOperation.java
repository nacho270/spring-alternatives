package com.nacho.blog.springalternatives.dagger.service.simple;

import javax.inject.Inject;

public class PowerTwoOperation implements Operation {

  @Inject
  public PowerTwoOperation() {
  }

  @Override
  public Double apply(final Double number) {
    return Math.pow(number, 2D);
  }

}
