package com.idproger.credit.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@PropertySource({"classpath:persistence-multiple-db.properties"})
@EnableJpaRepositories(
        basePackages = "com.idproger.credit.repository.credit",
        entityManagerFactoryRef = "creditEntityManager",
        transactionManagerRef = "creditTransactionManager"
)
public class PersistenceCreditConfiguration {

    private Environment env;

    public PersistenceCreditConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean creditEntityManager(){
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(creditDataSource());
        em.setPackagesToScan(
                "com.idproger.credit.entitiy.credit");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("credit.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.dialect",
                env.getProperty("credit.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }
    @Bean
    public DataSource creditDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                Objects.requireNonNull(env.getProperty("credit.jdbc.driverClassName")));
        dataSource.setUrl(env.getProperty("credit.jdbc.url"));
        dataSource.setUsername(env.getProperty("credit.jdbc.user"));
        dataSource.setPassword(env.getProperty("credit.jdbc.pass"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager creditTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                creditEntityManager().getObject());
        return transactionManager;
    }



}
