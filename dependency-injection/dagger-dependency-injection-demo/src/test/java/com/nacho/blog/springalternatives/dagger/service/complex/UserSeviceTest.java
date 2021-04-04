package com.nacho.blog.springalternatives.dagger.service.complex;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Singleton;

import org.junit.jupiter.api.Test;

import com.nacho.blog.springalternatives.dagger.dao.StubbedUserKeyValueStore;
import com.nacho.blog.springalternatives.dagger.dao.UserKeyValueStore;
import com.nacho.blog.springalternatives.dagger.model.User;

import dagger.Binds;
import dagger.Component;
import dagger.Module;

public class UserSeviceTest {

  private static final UserServiceTestComponent testComponent = DaggerUserSeviceTest_UserServiceTestComponent.builder().build();

  private UserService userSevice = testComponent.userService();

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

  @Singleton
  @Component(modules = TestModule.class)
  public interface UserServiceTestComponent {
    UserService userService();
  }

  @Module
  public abstract class TestModule {

    @Singleton
    public abstract UserService userService(final UserKeyValueStore userKeyValueStore);

    @Binds
    public abstract UserKeyValueStore stubbedUserKeyValueStore(StubbedUserKeyValueStore userKeyValueStore);

  }
}
