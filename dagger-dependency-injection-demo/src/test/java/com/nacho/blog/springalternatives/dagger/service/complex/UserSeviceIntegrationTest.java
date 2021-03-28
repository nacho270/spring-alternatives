package com.nacho.blog.springalernatives.guice.service.complex;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.nacho.blog.springalernatives.dagger.model.User;
import com.nacho.blog.springalernatives.dagger.service.complex.UserService;

@Testcontainers
public class UserSeviceIntegrationTest {

  @Container
  public GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest")) //
      .withExposedPorts(6379);

//  @Inject
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
