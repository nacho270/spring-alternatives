package com.nacho.blog.springalernatives.guice.config;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.nacho.blog.springalernatives.guice.service.complex.UserService;

public class ApplicationCommonModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(UserService.class).in(Scopes.SINGLETON);
  }
}
