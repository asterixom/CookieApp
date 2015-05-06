package de.cjt.hibernateh2osgi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

//		ServiceReference<?> ref = context
//				.getServiceReference(SessionFactory.class.getName());
//		if (ref != null) {
//			SessionFactory factory = (SessionFactory) context.getService(ref);
//			// Ready session factory. 
//			Session session= factory.getCurrentSession();
//		}

		DataProviderImpl provider = new DataProviderImpl();
		provider.main(null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
