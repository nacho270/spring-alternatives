package com.nacho.blog.springalternatives.guice.dao;

import com.nacho.blog.springalternatives.guice.model.User;

public interface UserKeyValueStore {

  User getById(int id);

  void store(int id, User user);

}
