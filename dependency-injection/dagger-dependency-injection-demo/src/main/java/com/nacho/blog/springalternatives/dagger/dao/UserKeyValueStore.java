package com.nacho.blog.springalternatives.dagger.dao;

import com.nacho.blog.springalternatives.dagger.model.User;

public interface UserKeyValueStore {

  User getById(int id);

  void store(int id, User user);

}
