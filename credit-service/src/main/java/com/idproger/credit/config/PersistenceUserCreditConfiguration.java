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
        basePackages = "com.idproger.credit.repository.user_credits",
        entityManagerFactoryRef = "userCreditEntityManager",
        transactionManagerRef = "userCreditTransactionManager"
)
public class PersistenceUserCreditConfiguration {

    private Environment env;

    public PersistenceUserCreditConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean userCreditEntityManager(){
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userCreditDataSource());
        em.setPackagesToScan(
                "com.idproger.credit.entitiy.user_credit");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("user_credit.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.dialect",
                env.getProperty("user_credit.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }
    @Bean
    public DataSource userCreditDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                Objects.requireNonNull(env.getProperty("user_credit.jdbc.driverClassName")));
        dataSource.setUrl(env.getProperty("user_credit.jdbc.url"));
        dataSource.setUsername(env.getProperty("user_credit.jdbc.user"));
        dataSource.setPassword(env.getProperty("user_credit.jdbc.pass"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager userCreditTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                userCreditEntityManager().getObject());
        return transactionManager;
    }



}
