package com.nacho.blog.springalternatives.fulldemo.config;

public class DatasourceConfig {

//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public DataSourceConnectionProvider connectionProvider() {
//        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
//    }
//
//    @Bean
//    public DefaultDSLContext dsl(@Value("${spring.datasource.dialect}") final String dialect) {
//        log.info("Creating JOOQ config using dialect: {}", dialect);
//        final DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
//        jooqConfiguration.set(connectionProvider());
//        jooqConfiguration.set(SQLDialect.valueOf(dialect));
//        return new DefaultDSLContext(jooqConfiguration);
//    }
}
