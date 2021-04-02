package com.nacho.blog.springalternatives.fulldemo.repository;

import com.nacho.blog.springalternatives.fulldemo.model.User;

import java.util.Optional;

public interface UserRepository {

  Optional<User> getById(Integer id);

  User save(User user);
}
