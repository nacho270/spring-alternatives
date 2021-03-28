package com.nacho.blog.springalternatives.dagger.service.complex;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.nacho.blog.springalternatives.dagger.config.ApplicationCommonModule;
import com.nacho.blog.springalternatives.dagger.config.ApplicationProdModule;
import com.nacho.blog.springalternatives.dagger.model.User;

import dagger.Component;

@Testcontainers
public class UserSeviceIntegrationTest {

  @Container
  public GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest")) //
      .withExposedPorts(6379);

  @Component(modules = { ApplicationCommonModule.class, ApplicationProdModule.class })
  public interface UserServiceTestComponent {
    UserService userService();
  }

  private static UserService userSevice;

  @BeforeAll
  public static void setup() {
    userSevice = DaggerUserSeviceTest_UserServiceTestComponent.builder().build().userService();
  }

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
