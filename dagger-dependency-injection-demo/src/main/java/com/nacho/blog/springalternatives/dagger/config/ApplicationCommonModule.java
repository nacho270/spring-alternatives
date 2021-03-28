package com.nacho.blog.springalternatives.dagger.config;

import javax.inject.Singleton;

import com.nacho.blog.springalternatives.dagger.dao.UserKeyValueStore;
import com.nacho.blog.springalternatives.dagger.service.complex.UserService;

import dagger.Module;

@Module
public abstract class ApplicationCommonModule {

  @Singleton
  public abstract UserService userService(final UserKeyValueStore userKeyValueStore);

}
