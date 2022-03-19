package com.example.demo.Conf;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableAsync
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.example.demo.repos"},
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
public class JpaConf {
    @Autowired
    Environment env;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(@Qualifier("primaryDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.demo.entities", "com.example.demo.repos");
        return em;
    }

    @Bean
    @Primary
    public DataSource primaryDataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("database.connection.driver_classname"));
        dataSource.setUrl(env.getProperty("database.connection.url"));
        dataSource.setUsername(env.getProperty("database.connection.username"));
        dataSource.setPassword(env.getProperty("database.connection.password"));

        dataSource.setInitialSize(0);
        dataSource.setMaxIdle(0);
        dataSource.setTimeBetweenEvictionRunsMillis(3000);
        dataSource.setMaxTotal(35);
        dataSource.setRemoveAbandonedTimeout(10);
        return dataSource;
    }

    @Bean
    @Primary
    public PlatformTransactionManager primaryTransactionManager(@Qualifier("primaryEntityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    @Primary
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();

        /*Initial Action Done by Hibernate once*/
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");


        //properties.setProperty("hibernate.hbm2ddl.auto", "update");

        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        properties.setProperty("hibernate.show_sql", "true");

        properties.setProperty("hibernate.jdbc.time_zone", "UTC");

        return properties;
    }

}
