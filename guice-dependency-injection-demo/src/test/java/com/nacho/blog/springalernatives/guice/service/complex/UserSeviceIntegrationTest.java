package com.nacho.blog.springalernatives.guice.service.complex;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.nacho.blog.springalternatives.guice.config.ApplicationCommonModule;
import com.nacho.blog.springalternatives.guice.config.ApplicationProdModule;
import com.nacho.blog.springalternatives.guice.model.User;
import com.nacho.blog.springalternatives.guice.service.complex.UserService;

import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;

@Testcontainers
@ExtendWith(GuiceExtension.class)
@IncludeModule(ApplicationCommonModule.class)
@IncludeModule(ApplicationProdModule.class)
public class UserSeviceIntegrationTest {

  @Container
  public GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest")) //
      .withExposedPorts(6379);

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
