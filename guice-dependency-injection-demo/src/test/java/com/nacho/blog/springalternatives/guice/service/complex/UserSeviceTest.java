package com.nacho.blog.springalernatives.guice.service.complex;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.nacho.blog.springalternatives.guice.config.ApplicationCommonModule;
import com.nacho.blog.springalternatives.guice.config.ApplicationDevModule;
import com.nacho.blog.springalternatives.guice.model.User;
import com.nacho.blog.springalternatives.guice.service.complex.UserService;

import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;

@ExtendWith(GuiceExtension.class)
@IncludeModule(ApplicationCommonModule.class)
@IncludeModule(ApplicationDevModule.class)
public class UserSeviceTest {

  @Inject
  private UserService userSevice;

  @Test
  public void testCanCreateUser() {
    // given
    final String userName = "nacho";

    // when
    final int id = userSevice.addUser(userName);

    // then
    final User user = userSevice.getById(id);
    assertThat(user, notNullValue());
    assertThat(user.getName(), equalTo(userName));
  }
}
