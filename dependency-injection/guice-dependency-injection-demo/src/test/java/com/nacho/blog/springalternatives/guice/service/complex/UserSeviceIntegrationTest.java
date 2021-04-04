package com.nacho.blog.springalternatives.guice.service.complex;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.nacho.blog.springalternatives.guice.config.ApplicationCommonModule;
import com.nacho.blog.springalternatives.guice.config.ApplicationProdModule;
import com.nacho.blog.springalternatives.guice.model.User;

import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;
import redis.clients.jedis.Jedis;

@Testcontainers
@ExtendWith(GuiceExtension.class)
@IncludeModule(ApplicationCommonModule.class)
@IncludeModule(ApplicationProdModule.class)
public class UserSeviceIntegrationTest {

  @Container
  public GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest")) //
      .withExposedPorts(6379) //
      .waitingFor( //
          Wait.forLogMessage(".*Ready to accept connections.*\\n", 1) //
      );

  @Inject
  private UserService userSevice;

  @Inject
  private Jedis jedis;

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
