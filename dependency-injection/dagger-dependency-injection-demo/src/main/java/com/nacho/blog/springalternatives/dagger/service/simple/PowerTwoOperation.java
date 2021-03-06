package com.nacho.blog.springalternatives.dagger.service.simple;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PowerTwoOperation implements Operation {

  @Inject
  public PowerTwoOperation() {
  }

  @Override
  public Double apply(final Double number) {
    return Math.pow(number, 2D);
  }

}
