package com.nacho.blog.springalternatives.dagger.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.nacho.blog.springalternatives.dagger.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class StubbedUserKeyValueStore implements UserKeyValueStore {

  private static final Map<Integer, User> USER_CACHE = new HashMap<>();

  @Inject
  public StubbedUserKeyValueStore() {
  }

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
