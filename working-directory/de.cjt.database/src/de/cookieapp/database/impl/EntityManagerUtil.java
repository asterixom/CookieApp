package de.cookieapp.database.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * This Class provides the Database with the EntityManagerFactory.
 * This Object is needed, to make transactions with the Database
 * @author christianverdion
 *
 */
public class EntityManagerUtil {
	private static EntityManagerFactory entityManagerFactory;

	public static EntityManager getEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

	private static EntityManagerFactory getEntityManagerFactory() {
		if ( entityManagerFactory == null ) {
			Bundle thisBundle = FrameworkUtil.getBundle( EntityManagerUtil.class );
			// Could get this by wiring up OsgiTestBundleActivator as well.
			BundleContext context = thisBundle.getBundleContext();
			System.out.println("Debug: Persistence Provider is: " + PersistenceProvider.class.getName());
			ServiceReference serviceReference = context.getServiceReference( PersistenceProvider.class.getName() );
			System.out.println("Debug: PersistenceServiceReference is: " + serviceReference.toString());
			PersistenceProvider persistenceProvider = (PersistenceProvider) context.getService( serviceReference );
			// Load the EntityManagerFactory from the PersistenceProvider
			entityManagerFactory = persistenceProvider.createEntityManagerFactory("CookieAppPersistenceUnit", null);
		}
		return entityManagerFactory;
	}
}