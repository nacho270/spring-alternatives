package com.nacho.blog.springalternatives.fulldemo.config;

import com.nacho.blog.springalternatives.fulldemo.repository.ProductRepository;
import com.nacho.blog.springalternatives.fulldemo.repository.ShipmentRepository;
import com.nacho.blog.springalternatives.fulldemo.repository.UserRepository;
import com.nacho.blog.springalternatives.fulldemo.repository.impl.ProductRepositoryImpl;
import com.nacho.blog.springalternatives.fulldemo.repository.impl.ShipmentRepositoryImpl;
import com.nacho.blog.springalternatives.fulldemo.repository.impl.UserRepositoryImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module
@Slf4j
public abstract class PersistenceModule {

  @Binds
  public abstract ProductRepository productRepositoryImpl(ProductRepositoryImpl productRepositoryImpl);

  @Binds
  public abstract ShipmentRepository shipmentRepositoryImpl(ShipmentRepositoryImpl shipmentRepositoryImpl);

  @Binds
  public abstract UserRepository userRepositoryImpl(UserRepositoryImpl userRepositoryImpl);

  @Singleton
  @Provides
  public static DataSource dataSource(Environment environment) {
    var hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(environment.getString("datasource.url"));
    hikariConfig.setUsername(environment.getString("datasource.username"));
    hikariConfig.setPassword(environment.getString("datasource.password"));
    return new HikariDataSource(hikariConfig);
  }

  @Singleton
  @Provides
  public static DSLContext dslContext(DataSource dataSource, Environment environment) {
    var sqlDialect = SQLDialect.valueOf(environment.getString("datasource.dialect"));
    log.info("Using SQL dialect: {}", sqlDialect);
    var cp = new DataSourceConnectionProvider(dataSource);
    return DSL.using(
            new DefaultConfiguration()
                    .set(cp)
                    .set(sqlDialect)
                    // Ideal use case for ThreadLocalTransactionProvider as no 3rd party libraries for tx is used here..
                    .set(new ThreadLocalTransactionProvider(cp, true)));
  }
}
