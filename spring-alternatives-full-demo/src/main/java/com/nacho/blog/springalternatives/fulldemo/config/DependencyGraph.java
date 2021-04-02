package com.nacho.blog.springalternatives.fulldemo.config;

import com.nacho.blog.springalternatives.fulldemo.UrlMappings;
import dagger.Component;

import javax.inject.Singleton;

public class DependencyGraph {

  @Singleton
  @Component(modules = {UserApiModule.class, RepositoriesModule.class, PersistenceModule.class})
  public interface ProdComponent {
    UrlMappings application();
  }

  public static UrlMappings buildApplication() {
    return DaggerDependencyGraph_ProdComponent.create().application();
  }
}
