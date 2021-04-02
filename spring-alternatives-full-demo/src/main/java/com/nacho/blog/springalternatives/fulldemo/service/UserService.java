package com.nacho.blog.springalternatives.fulldemo.service;

import com.nacho.blog.springalternatives.fulldemo.model.User;
import com.nacho.blog.springalternatives.fulldemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import retrofit2.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class UserService {

  private final UserRepository userRepository;
  private final UserApi userApi;

  @Inject
  public UserService(UserRepository userRepository, UserApi userApi) {
    this.userRepository = userRepository;
    this.userApi = userApi;
  }

  public User getById(Integer userId) {
    log.info("Searching user {} in the database", userId);
    return userRepository
            .getById(userId)
            .map(user -> {
              log.info("User found in the DB, no need to query the api");
              return user;
            })
            .orElseGet(() -> getUserFromApi(userId));
  }

  private User getUserFromApi(Integer userId) {
    log.info("User {} not found in the database, falling back to the api", userId);
    try {
      Response<User> userResponse = userApi.getById(userId).execute();
      if (userResponse.isSuccessful()) {
        User user = userResponse.body();
        log.info("Found user in the api, saving into the DB");
        return userRepository.save(user);
      }
      log.error("Error fetching user {}", userId);
      throw new RuntimeException();
    } catch (Exception e) {
      log.error("Error fetching user {}. Exception: {}", userId, e);
      throw new RuntimeException(e);
    }
  }
}
