package com.nacho.blog.springalernatives.dagger.config;

import com.nacho.blog.springalernatives.dagger.dao.StubbedUserKeyValueStore;
import com.nacho.blog.springalernatives.dagger.dao.UserKeyValueStore;
import com.nacho.blog.springalernatives.dagger.service.simple.MultiplyByTwoOperation;
import com.nacho.blog.springalernatives.dagger.service.simple.Operation;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationDevModule {

  @Binds
  public abstract Operation multiplyByTwoOperation(MultiplyByTwoOperation operation);

  @Binds
  public abstract UserKeyValueStore stubbedUserKeyValueStore(StubbedUserKeyValueStore userKeyValueStore);

}
