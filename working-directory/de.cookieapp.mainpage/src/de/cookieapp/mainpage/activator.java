package de.cookieapp.mainpage;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class activator implements BundleActivator, ServiceListener {

	private BasicApplication service;
	private ServiceTracker serviceTracker;
	private BundleContext context;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		this.context = context;
		service = new BasicApplication();

		Hashtable<String, String> properties = new Hashtable<String, String>();
		// register the service
		context.registerService(BasicApplication.class.getName(), service, properties);

		// create a tracker and track the service
		serviceTracker = new ServiceTracker(context, BasicApplication.class.getName(), null);
		serviceTracker.open();

		// have a service listener to implement the whiteboard pattern
	    context.addServiceListener(this, "(objectclass=" + BasicApplication.class.getName() + ")");
		
		// grab the service
		service = (BasicApplication) serviceTracker.getService();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		// close the service tracker
		serviceTracker.close();
		serviceTracker = null;

		service = null;
		context = null;
	}

	public void serviceChanged(ServiceEvent ev) {
		/*
		ServiceReference sr = ev.getServiceReference();
		switch(ev.getType()) {
			case ServiceEvent.REGISTERED:
			{
				BasicApplication dictionary = (BasicApplication) context.getService(sr);
				service.registerDictionary(dictionary);
			}
			break;
			case ServiceEvent.UNREGISTERING:
			{
				Dictionary dictionary = (Dictionary) context.getService(sr);
				service.unregisterDictionary(dictionary);
			}
			break;
		}
		*/
	}


}
