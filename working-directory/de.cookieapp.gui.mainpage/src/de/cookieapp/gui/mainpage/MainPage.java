package de.cookieapp.gui.mainpage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.service.ResourceLoader;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import de.cookieapp.gui.folderitem.FolderItem;

public class MainPage extends AbstractEntryPoint {

	private Composite parent;
	private TabFolder tabFolder;
	private List<FolderItem> folderItems = new ArrayList<FolderItem>();
	protected List<Composite> folderItemComposites = new ArrayList<Composite>();
	private List<TabItem> tabItems = new ArrayList<TabItem>();
	private BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
	@SuppressWarnings("rawtypes")
	private ServiceTracker serviceTrackerTabItem;
	private boolean started = false;
	final String defaultTab = "Registrieren";
	private static final int HEADER_HEIGHT = 140;
	private static final int CENTER_AREA_WIDTH = 998;





	@Override
	protected void createContents(Composite parent) {

		System.out.println("Main Page started");

		this.parent = parent;
		final ServerPushSession pushSession = new ServerPushSession();
		pushSession.start();

		InputStream is = new InputStream() {

			@Override
			public int read() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
		};

		createHeader(parent);
		
		parent.setLayout(new GridLayout(1,false));


		/*
		 * Hier soll der Header Stehen
		 */

		/*
		Composite textArea = new Composite(parent, SWT.NONE);
		lower_textfield = new Text(textArea, SWT.BORDER);
		lower_textfield.setEditable(false);
		lower_textfield.setTouchEnabled(true);
		lower_textfield.setBounds(10, 0, 318, 60);
		 */

		tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				/*
				int i = tabFolder.getSelectionIndex();
				lower_textfield.setText(numeralSystems.get(i).getBackup());
				ergebnis = false;
				 */
			}
		});

		startTabItemSeviceTracker();

		/*
		int numberOfTabItems = tabFolder.getItems().length;
		System.out.println("There are " + numberOfTabItems + " tabitems");
		 */
	}

	private static ResourceLoader createResourceLoader( final String resourceName ) {
		return new ResourceLoader() {
			public InputStream getResourceAsStream( String resourceName ) throws IOException {
				return getClass().getClassLoader().getResourceAsStream( resourceName );
			}
		};
	}
	
	 private Composite createHeader( Composite parent ) {
		    Composite comp = new Composite( parent, SWT.NONE );
		    comp.setData( RWT.CUSTOM_VARIANT, "header" );
		    comp.setBackgroundMode( SWT.INHERIT_DEFAULT );
		    comp.setLayout( new FormLayout() );
		    Composite headerCenterArea = createHeaderCenterArea( comp );
		    createLogo( headerCenterArea );
		    return comp;
		  }

		  private FormData createHeaderFormData() {
		    FormData data = new FormData();
		    data.top = new FormAttachment( 0 );
		    data.left = new FormAttachment( 0 );
		    data.right = new FormAttachment( 100 );
		    data.height = HEADER_HEIGHT;
		    return data;
		  }

		  private Composite createHeaderCenterArea( Composite parent ) {
		    Composite headerCenterArea = new Composite( parent, SWT.NONE );
		    headerCenterArea.setLayout( new FormLayout() );
		    headerCenterArea.setLayoutData( createHeaderCenterAreaFormData() );
		    return headerCenterArea;
		  }

		  private FormData createHeaderCenterAreaFormData() {
		    FormData data = new FormData();
		    data.left = new FormAttachment( 50, -CENTER_AREA_WIDTH / 2 );
		    data.top = new FormAttachment( 0 );
		    data.bottom = new FormAttachment( 100 );
		    data.width = CENTER_AREA_WIDTH;
		    return data;
		  }

	private void createLogo( Composite headerComp ) {
		Label logoLabel = new Label( headerComp, SWT.NONE );
		Image cookieLogo = getImage( headerComp.getDisplay(), "CookieApp.png" );
		logoLabel.setImage( cookieLogo );
		logoLabel.setLayoutData( createLogoFormData( cookieLogo ) );
		//makeLink( logoLabel, RAP_PAGE_URL );
	}

	private static FormData createLogoFormData( Image cookieLogo ) {
		FormData data = new FormData();
		data.left = new FormAttachment( 0 );
		int logoHeight = cookieLogo.getBounds().height;
		data.top = new FormAttachment( 50, -( logoHeight / 2 ) );
		return data;
	}

	public static Image getImage( Display display, String path ) {
		ClassLoader classLoader = MainPage.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream( "resources/" + path );
		Image result = null;
		if( inputStream != null ) {
			try {
				result = new Image( display, inputStream );
			} finally {
				try {
					inputStream.close();
				} catch( IOException e ) {
					// ignore
				}
			}
		}
		return result;
	}


	/*
	 * wird vor dem create Content aufgerufen...
	 * tabs zeichne methode muss angepasst werden!
	 */


	public void setFolderItem(final FolderItem folderItem) {
		if (folderItem != null && !(folderItems.contains(folderItem))) {
			updateContent(new Runnable() {

				public void run() {					
					folderItems.add(folderItem);
					System.out.println("added " + folderItem.getTabItemName());
					//General Composite for the Tab and the NumberArea
					Composite folderItemComposite = folderItem.getContent(tabFolder);
					//					folderItemComposite.setParent(tabFolder);
					folderItemComposites.add(folderItemComposite);		

					//The New Tab for the Composite
					TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
					tabItem.setText(folderItem.getTabItemName());
					tabItem.setControl(folderItemComposite);
					tabItems.add(tabItem);	

					/*
					 * Default TabItem has to be Selected!!!
					 */ 
					int defaultTabNumber = 0;
					if (folderItems.size() > 1 && !started) {
						for ( int i = 1;  i < folderItems.size();  i = i + 1){
							if (folderItems.get(i).getTabItemName().equals(defaultTab)){
								defaultTabNumber = i;
								started = true;		
							} 
							//TODO set Profile inivible and after login to visible
						}
					}
					tabFolder.setSelection(defaultTabNumber);
					parent.layout();


				}
			});
		} 

	}

	public void unsetFolderItem(final FolderItem folderItem) {
		if (folderItem != null) {
			updateContent(new Runnable() {
				public void run() {					
					final int x = folderItems.indexOf(folderItem);
					if (x > -1) {
						tabItems.remove(tabFolder.getItem(x));
						tabFolder.getItem(x).dispose();
						folderItemComposites.get(x).dispose();
						folderItemComposites.remove(x);
						folderItems.remove(folderItem);
					}
					parent.layout();
				}
			});
		}
	}

	public void startTabItemSeviceTracker() {
		serviceTrackerTabItem = new ServiceTracker(context, FolderItem.class,
				new ServiceTrackerCustomizer() {

			public Object addingService(final ServiceReference reference) {
				final FolderItem folderItem = (FolderItem) context.getService(reference);
				if(folderItem != null)
					setFolderItem(folderItem);
				return folderItem;
			}

			public void modifiedService(final ServiceReference reference, final Object service ) {/*ok*/}

			public void removedService(final ServiceReference reference, final Object service) {
				final FolderItem folderItem = (FolderItem) context.getService(reference);
				if(folderItem != null) {
					unsetFolderItem(folderItem);
				}
			}
		});
		Object[] services = serviceTrackerTabItem.getServices();
		if (services != null) {
			for (int i = 0; i < services.length; i++) {
				setFolderItem((FolderItem) services[i]);
			}
		}		
		serviceTrackerTabItem.open();
		//started = true;
	}

	public void stopNumeralSystem() {
		if(serviceTrackerTabItem != null)
			serviceTrackerTabItem.close();
	}

	public void updateContent(final Runnable runnable) {
		Runnable bgRunnable = new Runnable() {
			public void run() {
				if (parent != null) { 
					parent.getDisplay().asyncExec(runnable);
				} else {
					System.err.println("Parent is null");
				}
			};
		};
		Thread bgThread = new Thread(bgRunnable);
		bgThread.setDaemon(true);
		bgThread.start();
	}

}
