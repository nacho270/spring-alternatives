package com.nacho.blog.springalernatives.guice.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.nacho.blog.springalernatives.guice.dao.StubbedUserKeyValueStore;
import com.nacho.blog.springalernatives.guice.dao.UserKeyValueStore;
import com.nacho.blog.springalernatives.guice.service.simple.MultiplyByTwoOperation;
import com.nacho.blog.springalernatives.guice.service.simple.Operation;

public class ApplicationDevModule extends AbstractModule {

  // Another way of configuring the dependencies

  @Provides
  @Singleton
  public Operation operation() {
    return new MultiplyByTwoOperation();
  }

  @Provides
  @Singleton
  public UserKeyValueStore userKeyValueStore() {
    return new StubbedUserKeyValueStore();
  }
}
