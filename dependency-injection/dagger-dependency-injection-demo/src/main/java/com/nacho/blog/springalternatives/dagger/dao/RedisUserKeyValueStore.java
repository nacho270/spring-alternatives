package com.nacho.blog.springalternatives.dagger.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gson.Gson;
import com.nacho.blog.springalternatives.dagger.model.User;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
@Singleton
public class RedisUserKeyValueStore implements UserKeyValueStore {

  private static final Gson GSON = new Gson();
  private final Jedis jedis;

  @Inject
  public RedisUserKeyValueStore(final Jedis jedis) {
    this.jedis = jedis;
  }

  @Override
  public User getById(final int id) {
    log.info("Getting user in REDIS {}", id);
    return GSON.fromJson(jedis.get(String.valueOf(id)), User.class);
  }

  @Override
  public void store(final int id, final User user) {
    log.info("Storing user in REDIS {}", id);
    final String userJson = GSON.toJson(user);
    jedis.set(String.valueOf(id), userJson);
  }
}
