/*
 * CopyrightÂ©2017 NTT corpï¼� All Rights Reservedï¼�
 */
package vn.com.freesoft.docmanagement;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import vn.com.freesoft.docmanagement.util.Utils;

/**
 * The Class ApplicationInit.
 */
@WebListener
@EnableWebMvc
@Configuration
@ComponentScan("vn.com.freesoft.docmanagement")
@EnableTransactionManagement
public class ApplicationInit extends WebMvcConfigurerAdapter implements ServletContextListener {

	/** The Constant logger. */
	private final static Logger logger = Logger.getLogger(ApplicationInit.class);

	/** Configuration class. */
	private static AppConfig config = null;

	/** The Constant MODEL_PACKAGE_TO_SCAN. */
	private static final String MODEL_PACKAGE_TO_SCAN = "vn.com.freesoft.docmanagement.entity";

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(MODEL_PACKAGE_TO_SCAN);
		sessionFactory.setHibernateProperties(config.getHibProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(config.getHibProperties().getProperty(Constant.HIBERNATE_CONNECTION_DRIVER));
		dataSource.setUrl(config.getHibProperties().getProperty(Constant.HIBERNATE_CONNECTION_URL));
		dataSource.setUsername(config.getHibProperties().getProperty(Constant.HIBERNATE_CONNECTION_USERNAME));
		dataSource.setPassword(config.getHibProperties().getProperty(Constant.HIBERNATE_CONNECTION_PASSWORD));
		return dataSource;
	}

	/**
	 * Transaction manager.
	 *
	 * @param s
	 *            the s
	 * @return the hibernate transaction manager
	 */
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}

	/**
	 * Context initialized.
	 *
	 * @param servletContextEvent
	 *            the servlet context event
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// Load config
		loadConfig();
	}

	/**
	 * Load config.
	 *
	 * @throws ExceptionInInitializerError
	 *             the exception in initializer error
	 */
	private void loadConfig() throws ExceptionInInitializerError {
		logger.debug("IN - loadConfig()");

		// Read owner config file
		config = new AppConfig();
		String fileConfig = Constant.API_RESOURCE_CONFIG;
		Properties properties = Utils.readPropertiesFile(fileConfig);
		if (properties == null) {
			logger.error("Read fileConfig error: " + fileConfig);
			throw new ExceptionInInitializerError();
		}

		// check properties config
		if (!Utils.checkHibernateConfig(properties)) {
			throw new ExceptionInInitializerError();
		}
		config.setHibProperties(properties);

		logger.debug("OUT - loadConfig()");
	}

	/**
	 * Context destroyed.
	 *
	 * @param event
	 *            the event
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("IN - contextDestroyed()");
		// This manually deregisters JDBC driver
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.debug("Deregistering jdbc driver " + driver);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Error deregistering driver " + e);
			}
		}

		if (config != null) {
			config = null;
		}
		logger.debug("OUT - contextDestroyed()");
	}

	/**
	 * Gets the config.
	 *
	 * @return the appConfig
	 */
	public static AppConfig getConfig() {
		return config;
	}
}
