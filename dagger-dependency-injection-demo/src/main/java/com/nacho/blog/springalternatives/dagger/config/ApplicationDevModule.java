package com.nacho.blog.springalternatives.dagger.config;

import com.nacho.blog.springalternatives.dagger.dao.StubbedUserKeyValueStore;
import com.nacho.blog.springalternatives.dagger.dao.UserKeyValueStore;
import com.nacho.blog.springalternatives.dagger.service.simple.MultiplyByTwoOperation;
import com.nacho.blog.springalternatives.dagger.service.simple.Operation;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationDevModule {

  @Binds
  public abstract Operation multiplyByTwoOperation(MultiplyByTwoOperation operation);

  @Binds
  public abstract UserKeyValueStore stubbedUserKeyValueStore(StubbedUserKeyValueStore userKeyValueStore);

}
