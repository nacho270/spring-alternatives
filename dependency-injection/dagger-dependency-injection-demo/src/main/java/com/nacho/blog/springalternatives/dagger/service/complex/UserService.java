package com.nacho.blog.springalternatives.dagger.service.complex;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import com.nacho.blog.springalternatives.dagger.dao.UserKeyValueStore;
import com.nacho.blog.springalternatives.dagger.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {

  private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

  private UserKeyValueStore userKeyValueStore;

  @Inject
  public UserService(final UserKeyValueStore userKeyValueStore) {
    this.userKeyValueStore = userKeyValueStore;
  }

  public int addUser(final String name) {
    final int id = ID_GENERATOR.getAndIncrement();
    log.info("Storing user {}", id);
    userKeyValueStore.store(id, new User(id, name));
    return id;
  }

  public User getById(final int id) {
    log.info("Getting user {}", id);
    return userKeyValueStore.getById(id);
  }
}
