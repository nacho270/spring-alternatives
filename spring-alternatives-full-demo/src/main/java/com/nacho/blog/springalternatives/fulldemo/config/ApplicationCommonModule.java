package com.nacho.blog.springalternatives.fulldemo.config;

import javax.inject.Singleton;

import dagger.Module;

@Module
public abstract class ApplicationCommonModule {

  @Singleton
  public abstract Environment environment();
}
