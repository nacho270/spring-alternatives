package com.nacho.blog.springalternatives.fulldemo.repository.impl;

import com.nacho.blog.springalternatives.fulldemo.model.User;
import com.nacho.blog.springalternatives.fulldemo.repository.UserRepository;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.Optional;

import static com.nacho.blog.springalternatives.fulldemo.gen.Tables.T_USER;

@Singleton
public class UserRepositoryImpl implements UserRepository {

  private final DSLContext dslContext;

  @Inject
  public UserRepositoryImpl(DSLContext dslContext) {
    this.dslContext = dslContext;
  }

  @Override
  public Optional<User> getById(Integer id) {
    return Optional.ofNullable( //
            dslContext.select(T_USER.ID, T_USER.EMAIL) //
                    .from(T_USER) //
                    .where(T_USER.ID.eq(id)) //
                    .fetchOneInto(User.class));
  }

  @Override
  public User save(Configuration configuration, User user) {
    DSL.using(configuration) //
            .insertInto(T_USER, T_USER.ID, T_USER.EMAIL) //
            .values(user.getId(), user.getEmail()) //
            .execute();
    return user;
  }
}
