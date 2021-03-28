package com.nacho.blog.springalernatives.dagger.service.simple;

import javax.inject.Inject;

public class MultiplyByTwoOperation implements Operation {

  @Inject
  public MultiplyByTwoOperation() {
  }

  @Override
  public Double apply(final Double number) {
    return number * 2;
  }

}
