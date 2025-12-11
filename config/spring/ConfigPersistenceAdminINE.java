package mx.ine.sustseycae.config.spring;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( entityManagerFactoryRef = "entityMangerFactoryAdminINE", 
						basePackages = { "mx.ine.sustseycae.repositoriesadmin" }, 
						transactionManagerRef = "transactionManagerAdminINE")
public class ConfigPersistenceAdminINE {

	@Autowired
	private Environment environment;
	
	@Bean(name = "adminINEDataSource")
	@Primary
	public DataSource dataSource() {
		return new JndiDataSourceLookup().getDataSource(environment.getProperty("spring.adminine.datasource.jndi-name"));
	}
	
	@Bean(name = "entityMangerFactoryAdminINE")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPackagesToScan("mx.ine.sustseycae.dto.dbadmin");
		bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		bean.setJpaPropertyMap(hibernateProperties());

		return bean;
	}
	
	@Bean(name = "transactionManagerAdminINE")
	@Primary
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactory().getObject());
		return manager;
	}
	
    public Map<String, String> hibernateProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comments", "true");
        properties.put("hibernate.ejb.naming_strategy", 
        			   "org.hibernate.cfg.ImprovedNamingStrategy");
        properties.put("hibernate.transaction.manager_lookup_class", 
        				"org.jboss.cache.transaction.JBossTransactionManagerLookup");
        properties.put("hibernate.jdbc.batch_size", "50");
        properties.put("hibernate.max_fetch_depth", "1");
        properties.put("hibernate.order_inserts", "true");
        properties.put("hibernate.order_updates", "true");
        properties.put("hibernate.transaction.jta.platform",
        			   "org.hibernate.service.jta.platform.internal.JBossAppServerJtaPlatform");
        properties.put("hibernate.generate_statistics", "false");
        properties.put("org.hibernate.flushMode", "AUTO");

        return properties;
    }
	
}
