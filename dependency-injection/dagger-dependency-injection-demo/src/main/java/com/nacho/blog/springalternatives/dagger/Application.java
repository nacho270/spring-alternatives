package com.nacho.blog.springalternatives.dagger;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.nacho.blog.springalternatives.dagger.model.User;
import com.nacho.blog.springalternatives.dagger.service.complex.UserService;
import com.nacho.blog.springalternatives.dagger.service.simple.Operation;

@Singleton
public class Application {

  private Operation operation;

  private UserService userSevice;

  @Inject
  public Application(final Operation operation, final UserService userSevice) {
    this.operation = operation;
    this.userSevice = userSevice;
  }

  public Double calculate(final Double number) {
    return operation.apply(number);
  }

  public void store(final String name) {
    userSevice.addUser(name);
  }

  public User get(final int id) {
    return userSevice.getById(id);
  }
}
