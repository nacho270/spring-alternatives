package com.nacho.blog.springalernatives.guice.dao;

import java.util.Map;

import com.google.common.collect.Maps;
import com.nacho.blog.springalernatives.guice.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubbedUserKeyValueStore implements UserKeyValueStore {

  private static final Map<Integer, User> USER_CACHE = Maps.newHashMap();

  @Override
  public User getById(final int id) {
    log.info("Getting user from STUB {}", id);
    return USER_CACHE.get(id);
  }

  @Override
  public void store(final int id, final User user) {
    log.info("Storing user in STUB {}", id);
    USER_CACHE.put(id, user);
  }
}
