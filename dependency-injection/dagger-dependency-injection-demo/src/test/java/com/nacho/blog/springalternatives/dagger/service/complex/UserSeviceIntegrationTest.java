package com.nacho.blog.springalternatives.dagger.service.complex;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Singleton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.nacho.blog.springalternatives.dagger.config.ApplicationProdModule;
import com.nacho.blog.springalternatives.dagger.model.User;

import dagger.Component;
import redis.clients.jedis.Jedis;

@Testcontainers
public class UserSeviceIntegrationTest {

  @Container
  public GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest")) //
      .withExposedPorts(6379) //
      .waitingFor( //
          Wait.forLogMessage(".*Ready to accept connections.*\\n", 1) //
      );

  @Singleton
  @Component(modules = { ApplicationProdModule.class })
  public interface UserServiceIntegrationTestComponent {
    UserService userService();

    Jedis jedis();
  }

  private static UserServiceIntegrationTestComponent userServiceTestComponent = DaggerUserSeviceIntegrationTest_UserServiceIntegrationTestComponent.builder().build();
  private UserService userSevice = userServiceTestComponent.userService();
  private Jedis jedis = userServiceTestComponent.jedis();

  @BeforeEach
  public void setup() {
    // Need this here to be able to override the redis port. TestContainers creates redis exposing port 6379 to a random one.
    // See https://www.testcontainers.org/features/networking/
    jedis.getClient().setPort(redis.getFirstMappedPort());
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
