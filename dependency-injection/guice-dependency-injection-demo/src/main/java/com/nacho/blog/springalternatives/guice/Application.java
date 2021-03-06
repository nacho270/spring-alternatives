package com.nacho.blog.springalternatives.guice;

import com.google.inject.Inject;
import com.nacho.blog.springalternatives.guice.model.User;
import com.nacho.blog.springalternatives.guice.service.complex.UserService;
import com.nacho.blog.springalternatives.guice.service.simple.Operation;

public class Application {

  @Inject
  private Operation operation;

  @Inject
  private UserService userSevice;

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
