package com.nacho.blog.springalternatives.fulldemo.config;

import com.nacho.blog.springalternatives.fulldemo.service.UserApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public abstract class UserApiModule {

  @Singleton
  @Provides
  public static UserApi userApi(Environment environment) {
    return new Retrofit.Builder() //
            .baseUrl(environment.getString("userapi.url")) //
            .addConverterFactory(GsonConverterFactory.create()) //
            .build()
            .create(UserApi.class);
  }
}
