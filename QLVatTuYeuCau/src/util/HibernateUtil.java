package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

// TODO: Auto-generated Javadoc
/**
 * The Class HibernateUtil.
 */
public final class HibernateUtil {
	
	/**
	 * Instantiates a new hibernate util.
	 */
	private HibernateUtil() {
		
	}
	
	/** The Constant sessionFactory. */
	private static final SessionFactory	SESSIONFACTORY	= buildSessionFactory();
	
	/**
	 * Builds the session factory.
	 *
	 * @return the session factory
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			ServiceRegistry serviceRegistry;
			serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			SessionFactory sessionFactory = configuration
					.buildSessionFactory(serviceRegistry);
			return sessionFactory;
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	/**
	 * Gets the session factory.
	 *
	 * @return the session factory
	 */
	public static SessionFactory getSessionFactory() {
		return SESSIONFACTORY;
	}
	
	/**
	 * Shutdown.
	 */
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
	
}