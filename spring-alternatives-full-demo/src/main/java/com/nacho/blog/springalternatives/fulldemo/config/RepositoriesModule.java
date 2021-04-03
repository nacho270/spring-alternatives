package com.nacho.blog.springalternatives.fulldemo.config;

import com.nacho.blog.springalternatives.fulldemo.repository.ProductRepository;
import com.nacho.blog.springalternatives.fulldemo.repository.ShipmentRepository;
import com.nacho.blog.springalternatives.fulldemo.repository.UserRepository;
import com.nacho.blog.springalternatives.fulldemo.repository.impl.ProductRepositoryImpl;
import com.nacho.blog.springalternatives.fulldemo.repository.impl.ShipmentRepositoryImpl;
import com.nacho.blog.springalternatives.fulldemo.repository.impl.UserRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoriesModule {

  @Binds
  public abstract ProductRepository productRepositoryImpl(ProductRepositoryImpl productRepositoryImpl);

  @Binds
  public abstract ShipmentRepository shipmentRepositoryImpl(ShipmentRepositoryImpl shipmentRepositoryImpl);

  @Binds
  public abstract UserRepository userRepositoryImpl(UserRepositoryImpl userRepositoryImpl);

}
