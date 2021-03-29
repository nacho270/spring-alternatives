package com.nacho.blog.springalternatives.fulldemo.config;

import javax.inject.Singleton;

import com.nacho.blog.springalternatives.fulldemo.controller.PingController;
import com.nacho.blog.springalternatives.fulldemo.controller.ProductController;
import com.nacho.blog.springalternatives.fulldemo.controller.ShipmentController;
import com.nacho.blog.springalternatives.fulldemo.service.ProductService;
import dagger.Module;

@Module
public abstract class ApplicationCommonModule {

  @Singleton
  public abstract Environment environment();

  @Singleton
  public abstract PingController pingController();

  @Singleton
  public abstract ProductController productController(ProductService productService);

  @Singleton
  public abstract ShipmentController shipmentController();

  @Singleton
  public abstract ProductService productService();
}
