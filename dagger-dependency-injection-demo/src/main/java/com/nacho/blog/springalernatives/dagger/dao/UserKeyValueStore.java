package com.nacho.blog.springalernatives.dagger.dao;

import com.nacho.blog.springalernatives.dagger.model.User;

public interface UserKeyValueStore {

  User getById(int id);

  void store(int id, User user);

}
