/*
 * Copyright©2017 NTT corp． All Rights Reserved．
 */
package vn.co.evn.materialmanagement;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * The Class ApplicationInit.
 */
@Component
public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {

	/** The Constant logger. */
	private final static Logger logger = LoggerFactory.getLogger(ApplicationInit.class);

	/** Configuration class. */
	private static AppConfig config = null;

	 /**
	 * Load config.
	 *
	 * @throws ExceptionInInitializerError the exception in initializer error
	 */
	private void loadConfig() throws ExceptionInInitializerError {
//		config = new AppConfig();
//		String rootDir = getClass().getClassLoader().getResource(".").getFile().substring(1);
//		System.out.println("root dir: " + rootDir);
//		config.setRootDirectory(rootDir);
	}

	/**
	 * Context destroyed.
	 *
	 * @param event
	 *            the event
	 */
//	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("IN - contextDestroyed()");
		// This manually deregisters JDBC driver
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.debug("Deregistering jdbc driver {}", driver);
			} catch (SQLException e) {
				logger.error("Exception: ", e);
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

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		System.out.println("Init app");
		loadConfig();
	}

}
