package com.nacho.blog.springalternatives.guice.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.nacho.blog.springalternatives.guice.dao.RedisUserKeyValueStore;
import com.nacho.blog.springalternatives.guice.dao.UserKeyValueStore;
import com.nacho.blog.springalternatives.guice.service.simple.Operation;
import com.nacho.blog.springalternatives.guice.service.simple.PowerTwoOperation;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class ApplicationProdModule extends AbstractModule {

  @Override
  protected void configure() {
    log.info("Configuring app module");
    bind(Operation.class).to(PowerTwoOperation.class).in(Scopes.SINGLETON);
    bind(UserKeyValueStore.class).to(RedisUserKeyValueStore.class).in(Scopes.SINGLETON);
  }

  @Provides
  @Singleton
  public Jedis jedis() {
    // potentially configure here the host, port, etc
    return new Jedis();
  }
}
