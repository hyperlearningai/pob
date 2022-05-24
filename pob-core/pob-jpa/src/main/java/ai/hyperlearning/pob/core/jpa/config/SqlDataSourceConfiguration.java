package ai.hyperlearning.pob.core.jpa.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SQL Data Source Configuration
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"ai.hyperlearning.pob.core.jpa.repositories"}, 
        entityManagerFactoryRef = "pobEntityManagerFactory",
        transactionManagerRef = "pobTransactionManager")
public class SqlDataSourceConfiguration {
    
    @Primary
    @Bean(name = "pobDataSource")
    @ConfigurationProperties(prefix = "storage.sql")
    public DataSource ontologyCollaborationDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Primary
    @Bean(name = "pobEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean pobEntityManagerFactory(
            EntityManagerFactoryBuilder pobEntityManagerFactoryBuilder, 
            @Qualifier("pobDataSource") DataSource pobDataSource) {
        
        Map<String, String> primaryJpaProperties = new HashMap<>();
        primaryJpaProperties.put("hibernate.hbm2ddl.auto", "update");
        return pobEntityManagerFactoryBuilder
                .dataSource(pobDataSource)
                .packages("ai.hyperlearning.pob.model")
                .persistenceUnit("pob")
                .properties(primaryJpaProperties)
                .build();
        
    }
    
    @Primary
    @Bean(name = "pobTransactionManager")
    public PlatformTransactionManager pobTransactionManager(
            @Qualifier("pobEntityManagerFactory") 
                EntityManagerFactory pobEntityManagerFactory) {
        return new JpaTransactionManager(pobEntityManagerFactory);
    }

}
