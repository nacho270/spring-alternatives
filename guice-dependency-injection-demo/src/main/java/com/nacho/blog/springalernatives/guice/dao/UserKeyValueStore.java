package com.nacho.blog.springalernatives.guice.dao;

import com.nacho.blog.springalernatives.guice.model.User;

public interface UserKeyValueStore {

  User getById(int id);

  void store(int id, User user);

}
