package de.cookieapp.gui.mainpage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import de.cookieapp.control.ControlService;
import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.data.model.User;
import de.cookieapp.gui.folderitem.FolderItem;

public class MainPage extends AbstractEntryPoint {

	private Composite parent;
	private Composite loginComposite;
	private Composite homeControlComposite;
	
	private CTabFolder tabFolder;
	
	private List<FolderItem> folderItems = new ArrayList<FolderItem>();
	protected List<Composite> folderItemComposites = new ArrayList<Composite>();
	private List<CTabItem> tabItems = new ArrayList<CTabItem>();
	private BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
	private ControlService controlService;
	private ServiceTracker<FolderItem, FolderItem> serviceTrackerTabItem;
	private ServiceTracker<ControlService, ControlService> serviceTrackerControlService;
	private boolean started = false;
	final String defaultTab = "Home";
	private static final int HEADER_HEIGHT = 140;
	private static final int CONTENT_SHIFT = 300;
	private static final int CONTENT_WITH = 450;
	private Display display;
	private static final String BACKGROUNDIMAGE = "resources/greenbackground.jpg";
	private static final String CONTROLBACKGROUNDIMAGE = "resources/controlbackground.png";
	private static final String LOGO = "resources/Logo.png";
	private Long sessionID;
	private Text nameText;
	private Text passwortText;

	private Label nameLabel;
	private Label passwordLabel;
	private Button loginButton;


	@Override
	protected void createContents(Composite parent) {
		System.out.println("Main Page started");
		startControlServiceSeviceTracker();
		final ServerPushSession pushSession = new ServerPushSession();
		pushSession.start();
		this.parent = parent;
		FormLayout formLayout = new FormLayout();
		formLayout.marginTop = 5;
		formLayout.marginLeft = 5;
		parent.setLayout(new FormLayout());

		if (controlService != null) {
			sessionID = controlService.createSession();
		} else {
			System.err.println("ControlSerive Not Found!");
		}

		setBackgroundImage(parent);
		createLoginComposite(parent);
		createTabFolderComposite(parent);


		if (sessionID != null) {
			addSessionIDToTabs(sessionID);
		}
	}


	private Composite createTabFolderComposite(Composite parent) {
		tabFolder = new CTabFolder(parent, SWT.NONE);
		tabFolder.setBackground(new Color(null,0xf0,0xf0,0xf0));
		tabFolder.setLocation(CONTENT_SHIFT, HEADER_HEIGHT);
		tabFolder.setSize(CONTENT_WITH, parent.getSize().y - HEADER_HEIGHT);
		FormData tabFormData = new FormData();
		tabFormData.top = new FormAttachment(homeControlComposite, 10);
		tabFormData.left = new FormAttachment(50, -CONTENT_WITH);
		tabFormData.right = new FormAttachment(50, CONTENT_WITH);
		tabFolder.setLayoutData(tabFormData);
		startTabItemSeviceTracker();
		return tabFolder;
	}


	private Composite createLoginComposite(Composite parent) {
		homeControlComposite = new Composite(parent, SWT.FILL | SWT.CENTER);
		homeControlComposite.setLayout(new GridLayout(2, true));
		homeControlComposite.setLocation(CONTENT_SHIFT, 0);
		homeControlComposite.setSize(CONTENT_WITH, HEADER_HEIGHT);
		Image controlImage = loadImage(CONTROLBACKGROUNDIMAGE);
		homeControlComposite.setBackgroundImage(controlImage);		
		Image headerImage = loadImage(LOGO);
		final int width = headerImage.getBounds().width;
		final int height = headerImage.getBounds().height;
		headerImage = new Image(display, headerImage.getImageData().scaledTo((int)(width*0.5),(int)(height*0.5)));
		Label headerLabel = new Label( homeControlComposite, SWT.LEFT );
		headerLabel.setImage( headerImage );
		loggedinHeader(null);
	    FormData data1 = new FormData();
	    data1.left = new FormAttachment(50, -CONTENT_WITH);
	    data1.right = new FormAttachment(50, CONTENT_WITH);
	    data1.top = new FormAttachment(0, 20);
	    homeControlComposite.setLayoutData(data1);
	    return homeControlComposite;
	}

	private void loggedinHeader(final String username) {
		if (loginComposite != null) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
//					loginComposite.dispose();
			 		loginComposite = new Composite(homeControlComposite, SWT.NONE);
					loginComposite.setLayout(new FillLayout(SWT.NONE));
//					Label loggedinAsLabel = new Label(loginComposite, SWT.NONE);
//					loggedinAsLabel.setText("Eingeloggt als " + username);	
//					loggedinAsLabel.setText("Eingeloggt als " + username);	
					nameLabel.setText("Eingeloggt als " + username);
					nameText.setVisible(false);
					passwordLabel.setVisible(false);
					passwortText.setVisible(false);
					loginButton.setText("Ausloggen");
					System.out.println("test");
				}
			};
			updateContent(runnable);
		} else {
			loginComposite = new Composite(homeControlComposite, SWT.RIGHT);
			loginComposite.setLayout(new GridLayout(2, true));
			GridData textGridData = new GridData();
			textGridData.horizontalAlignment = GridData.FILL;
			nameLabel = new Label(loginComposite, SWT.NONE);
			nameLabel.setText("Benutzername oder eMail");
			nameText = new Text(loginComposite, SWT.BORDER | SWT.FILL);
			nameText.setLayoutData(textGridData);
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
			passwordLabel = new Label(loginComposite, SWT.NONE);
			passwordLabel.setText("Passwort");
			passwortText = new Text(loginComposite, SWT.BORDER | SWT.PASSWORD | SWT.FILL);
			GridData gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			passwortText.setLayoutData(gridData);
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
			loginButton = new Button(loginComposite, SWT.CENTER);
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
	}


	private void login() {
		if (controlService != null) {
			try {
				controlService.login(sessionID, nameText.getText(), passwortText.getText());
				System.out.println("logged in as " +  controlService.getCurrentUserName(sessionID));
				if (nameText.getText().equals(controlService.getCurrentUserName(sessionID)) ||
						nameText.getText().equals(controlService.getCurrentUser(sessionID).getMail())) {
					loggedinHeader(controlService.getCurrentUserName(sessionID));
					User user = controlService.getCurrentUser(sessionID);
					for (FolderItem folderitem : folderItems) {
						folderitem.setLogedInUser(user);
					}
					addSessionIDToTabs(sessionID);
					//TODO implement real database with user
				}
			} catch (CookieAppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.err.println("Login Exception");
			}
		}
	}

	/**
	 * This function sets a background image for the given Composite
	 * @param parent the Composite, which should have the default background image
	 */
	private void setBackgroundImage(Composite parent) {
		Image backgroundImage = loadImage(BACKGROUNDIMAGE);
		final int width = backgroundImage.getBounds().width;
		final int height = backgroundImage.getBounds().height;
		backgroundImage = new Image(display, backgroundImage.getImageData().scaledTo((int)(width*0.7),(int)(height*0.7)));
		parent.setBackgroundImage(backgroundImage);
		parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
	}

	/**
	 * Loads the Image at the given path, transforms it into an Image
	 * @param name the full path of the image
	 * @return the Image, which was located at the given path
	 */
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
					CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
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

	/**
	 * starts the ServiceTracker, which adds the started tabs to the list 
	 * and registers a osgi listener, which will be activated, if a tab service will be started
	 */
	public void startTabItemSeviceTracker() {
		serviceTrackerTabItem = new ServiceTracker<FolderItem, FolderItem>(context, FolderItem.class,
				new ServiceTrackerCustomizer<FolderItem, FolderItem>() {

			public FolderItem addingService(final ServiceReference<FolderItem> reference) {
				final FolderItem folderItem = context.getService(reference);
				if(folderItem != null)
					setFolderItem(folderItem);
				return folderItem;
			}

			public void modifiedService(final ServiceReference<FolderItem> reference, final FolderItem service ) {/*ok*/}

			public void removedService(final ServiceReference<FolderItem> reference, final FolderItem service) {
				final FolderItem folderItem = context.getService(reference);
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

	public void stopTabItemSeviceTracker() {
		if(serviceTrackerTabItem != null)
			serviceTrackerTabItem.close();
	}

	/**
	 * Updated the Page without reloading it
	 * @param runnable
	 */
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

	/**
	 * starts the ServiceTracker, which adds the started ControlService 
	 * and registers a osgi listener, which will be activated, if a ControlService will be started
	 */
	public void startControlServiceSeviceTracker() {
		serviceTrackerControlService = new ServiceTracker<ControlService, ControlService>(context, ControlService.class,
				new ServiceTrackerCustomizer<ControlService, ControlService>() {

			public ControlService addingService(final ServiceReference<ControlService> reference) {
				final ControlService controlService = context.getService(reference);
				if(controlService != null) {
					MainPage.this.controlService = controlService;
				}
				return controlService;
			}

			public void modifiedService(final ServiceReference<ControlService> reference, final ControlService service ) {/*ok*/}

			public void removedService(final ServiceReference<ControlService> reference, final ControlService service) {
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
