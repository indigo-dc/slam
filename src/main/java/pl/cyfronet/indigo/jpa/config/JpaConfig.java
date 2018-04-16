package pl.cyfronet.indigo.jpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * Created by km on 10.06.16.
 */
@Configuration
public class JpaConfig {
    @Autowired
    private Environment env;
    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter hibernateJpa = new HibernateJpaVendorAdapter();
        hibernateJpa.setDatabasePlatform(env.getProperty("hibernate.dialect"));
        hibernateJpa.setGenerateDdl(env.getProperty("hibernate.generateDdl", Boolean.class));
        hibernateJpa.setShowSql(env.getProperty("hibernate.show_sql", Boolean.class));

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(env.getProperty("entityManager.packagesToScan"));
        emf.setJpaVendorAdapter(hibernateJpa);
        emf.setJpaPropertyMap(Collections.singletonMap("javax.persistence.validation.mode", "none"));
        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txnMgr = new JpaTransactionManager();
        txnMgr.setEntityManagerFactory(entityManagerFactory().getObject());
        return txnMgr;
    }
}
