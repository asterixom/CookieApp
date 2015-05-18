package hibernateh2osgi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

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

			ServiceReference serviceReference = context.getServiceReference( PersistenceProvider.class.getName() );
			PersistenceProvider persistenceProvider = (PersistenceProvider) context.getService( serviceReference );
			entityManagerFactory = persistenceProvider.createEntityManagerFactory( "test", null);
		}
		return entityManagerFactory;
	}
}