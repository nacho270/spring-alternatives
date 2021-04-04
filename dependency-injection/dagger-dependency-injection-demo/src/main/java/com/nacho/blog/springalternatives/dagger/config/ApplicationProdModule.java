package com.nacho.blog.springalternatives.dagger.config;

import javax.inject.Singleton;

import com.nacho.blog.springalternatives.dagger.dao.RedisUserKeyValueStore;
import com.nacho.blog.springalternatives.dagger.dao.UserKeyValueStore;
import com.nacho.blog.springalternatives.dagger.service.simple.Operation;
import com.nacho.blog.springalternatives.dagger.service.simple.PowerTwoOperation;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import redis.clients.jedis.Jedis;

@Module
public abstract class ApplicationProdModule {

  @Binds
  public abstract Operation powerTwoOperation(PowerTwoOperation operation);

  @Binds
  public abstract UserKeyValueStore redisUserKeyValueStore(RedisUserKeyValueStore userKeyValueStore);

  @Singleton
  @Provides
  public static Jedis jedis() {
    // configure the host, port, etc
    return new Jedis();
  }
}
