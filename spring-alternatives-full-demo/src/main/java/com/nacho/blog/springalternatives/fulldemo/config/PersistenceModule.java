package com.nacho.blog.springalternatives.fulldemo.config;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Module
@Slf4j
public abstract class PersistenceModule {

  @Singleton
  @Provides
  public static DataSource dataSource(final Environment environment) {
    final var databaseConfiguration = environment.getObject("datasource", DatabaseConfiguration.class);
    log.info("Using db at host: {}", databaseConfiguration.getUrl());
    final var hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(databaseConfiguration.getUrl());
    hikariConfig.setDriverClassName(databaseConfiguration.getDriver());
    hikariConfig.setUsername(databaseConfiguration.getUserName());
    hikariConfig.setPassword(databaseConfiguration.getPassword());
    return new HikariDataSource(hikariConfig);
  }

  @Singleton
  @Provides
  public static DSLContext dslContext(final DataSource dataSource, final Environment environment) {
    final var sqlDialect = SQLDialect.valueOf(environment.getString("datasource.dialect"));
    log.info("Using SQL dialect: {}", sqlDialect);
    final var cp = new DataSourceConnectionProvider(dataSource);
    return DSL.using(new DefaultConfiguration().set(cp).set(sqlDialect)
        // Ideal use case for ThreadLocalTransactionProvider as no 3rd party libraries for tx is used here..
        .set(new ThreadLocalTransactionProvider(cp, true)));
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class DatabaseConfiguration {
    String url;
    String driver;
    String userName;
    String password;
    String dialect;
  }
}
