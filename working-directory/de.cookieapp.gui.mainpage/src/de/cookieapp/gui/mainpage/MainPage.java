package de.cookieapp.gui.mainpage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
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

import de.cookieapp.control.ControlService;
import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.gui.folderitem.FolderItem;

public class MainPage extends AbstractEntryPoint {

	private Composite parent;
	private TabFolder tabFolder;
	private List<FolderItem> folderItems = new ArrayList<FolderItem>();
	protected List<Composite> folderItemComposites = new ArrayList<Composite>();
	private List<TabItem> tabItems = new ArrayList<TabItem>();
	private BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
	private ControlService controlService;
	@SuppressWarnings("rawtypes")
	private ServiceTracker serviceTrackerTabItem;
	@SuppressWarnings("rawtypes")
	private ServiceTracker serviceTrackerControlService;
	private boolean started = false;
	final String defaultTab = "Home";
	//	private static final int HEADER_HEIGHT = 140;
	//	private static final int CENTER_AREA_WIDTH = 998;		
	private Display display;
	private static final String BACKGROUNDIMAGE = "resources/greenbackground.jpg";
	private static final String CONTROLBACKGROUNDIMAGE = "resources/controlbackground.png";
	private static final String LOGO = "resources/Logo.png";
	private Long sessionID;
	private Text nameText;
	private Text passwortText;


	@Override
	protected void createContents(Composite parent) {
		System.out.println("Main Page started");
		startControlServiceSeviceTracker();
		this.parent = parent;
		final ServerPushSession pushSession = new ServerPushSession();
		pushSession.start();
		if (controlService != null) {
			sessionID = controlService.createSession();
		} else {
			System.err.println("ControlSerive Not Found!");
		}


		setBackgroundImage(parent);
		createLoginComposite(parent);


		tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			public void widgetSelected(SelectionEvent e) {
				/*
				int i = tabFolder.getSelectionIndex();
				lower_textfield.setText(numeralSystems.get(i).getBackup());
				ergebnis = false;
				 */
			}
		});

		startTabItemSeviceTracker();
		if (sessionID != null) {
			addSessionIDToTabs(sessionID);
		}
	}


	private void createLoginComposite(Composite parent) {
		Composite homeControlComposite = new Composite(parent, SWT.FILL | SWT.RIGHT);
		homeControlComposite.setLayout(new GridLayout(2, true));
		Image controlImage = loadImage(CONTROLBACKGROUNDIMAGE);
		homeControlComposite.setBackgroundImage(controlImage);		
		Image headerImage = loadImage(LOGO);
		final int width = headerImage.getBounds().width;
		final int height = headerImage.getBounds().height;
		headerImage = new Image(display, headerImage.getImageData().scaledTo((int)(width*0.5),(int)(height*0.5)));
		Label headerLabel = new Label( homeControlComposite, SWT.LEFT );
		headerLabel.setImage( headerImage );
		Composite loginComposite = new Composite(homeControlComposite, SWT.RIGHT);
		loginComposite.setLayout(new GridLayout(2, true));
		Label nameLabel = new Label(loginComposite, SWT.NONE);
		nameLabel.setText("Benutzername oder eMail");
		nameText = new Text(loginComposite, SWT.BORDER | SWT.FILL);
		nameText.addKeyListener(new KeyListener() {			
			private static final long serialVersionUID = 6511802604250486237L;
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					login();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				//do nothing, wait for release
			}
		});
		Label passwordLabel = new Label(loginComposite, SWT.NONE);
		passwordLabel.setText("Passwort");
		passwortText = new Text(loginComposite, SWT.BORDER | SWT.PASSWORD | SWT.FILL);
		passwortText.addKeyListener(new KeyListener() {			
			private static final long serialVersionUID = 6277769989802841352L;
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					login();
				}

			}
			@Override
			public void keyPressed(KeyEvent e) {
				//do nothing, wait for release
			}

		});
		Label emptyLabel = new Label(loginComposite, SWT.NONE);
		emptyLabel.setVisible(false);
		Button loginButton = new Button(loginComposite, SWT.CENTER);
		loginButton.setText("Login");
		loginButton.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1925540608622081691L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				login();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				//do nothing here				
			}
		});
	}


	private void login() {
		if (controlService != null) {
			try {
				controlService.login(sessionID, nameText.getText(), passwortText.getText());
				System.out.println("logged in as " +  controlService.getCurrentUserName(sessionID));
			} catch (CookieAppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.err.println("Login Exception");
			}
		}
	}


	private void setBackgroundImage(Composite parent) {
		Image backgroundImage = loadImage(BACKGROUNDIMAGE);
		final int width = backgroundImage.getBounds().width;
		final int height = backgroundImage.getBounds().height;
		backgroundImage = new Image(display, backgroundImage.getImageData().scaledTo((int)(width*0.7),(int)(height*0.7)));
		parent.setBackgroundImage(backgroundImage);
		parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
	}


	public Image loadImage(String name) {
		Image result = null;
		InputStream stream = MainPage.class.getClassLoader().getResourceAsStream( name );
		if( stream != null ) {
			try {
				result = new Image(display, stream);
			} finally {
				try {
					stream.close();
				} catch( IOException unexpected ) {
					throw new RuntimeException( "Failed to close image input stream", unexpected );
				}
			}
		}
		return result;
	}
	
	private void addSessionIDToTabs(Long sessionID) {
		for (FolderItem folderItem : folderItems) {
			folderItem.setSessionID(sessionID);
		}
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
					if (sessionID != null) {
						folderItem.setSessionID(sessionID);
					}

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
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

//	public void setControlService(ControlService controlService) {
//		if (controlService != null) {
//			this.controlService = controlService;
//		}
//	}
//
//	public void unsetControlService(ControlService controlService) {
//		if (this.controlService.equals(controlService)) {
//			this.controlService = null;
//		}
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void startControlServiceSeviceTracker() {
		serviceTrackerControlService = new ServiceTracker(context, ControlService.class,
				new ServiceTrackerCustomizer() {

			public Object addingService(final ServiceReference reference) {
				final ControlService controlService = (ControlService) context.getService(reference);
				if(controlService != null) {
					MainPage.this.controlService = controlService;
				}
				return controlService;
			}

			public void modifiedService(final ServiceReference reference, final Object service ) {/*ok*/}

			public void removedService(final ServiceReference reference, final Object service) {
				final ControlService controlService = (ControlService) context.getService(reference);
				if(controlService != null) {
					MainPage.this.controlService = null;
				}
			}
		});
		Object[] services = serviceTrackerControlService.getServices();
		if (services != null) {
			for (int i = 0; i < services.length; i++) {
				if(services[i] != null) {
					MainPage.this.controlService = (ControlService) services[i];

				}
			}
		}		
		serviceTrackerControlService.open();
		//started = true;
	}
}
