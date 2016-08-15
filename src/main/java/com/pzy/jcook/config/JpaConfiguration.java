package com.pzy.jcook.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/*@Configuration
@EnableJpaRepositories("com.pzy.jcook.sys.repository")
*/
public class JpaConfiguration {
	/*@Bean
	public  LocalContainerEntityManagerFactoryBean  localContainerEntityManagerFactoryBean(){
		 LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		 entityManagerFactory.setDataSource(dataSource);
		 entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		 entityManagerFactory.setPackagesToScan("com.pzy.jcook.sys.entity");
	        return entityManagerFactory;
	}*/
}
