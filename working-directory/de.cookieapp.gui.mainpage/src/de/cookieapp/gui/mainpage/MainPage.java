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
import de.cookieapp.util.PictureLoader;

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
	final String[] loggedInTabs = {"Mein Profil", "Neues Rezept erstellen"};
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
	private User user;

	/**
	 * Method, that will be executed, when the Page gets loaded. Everything for
	 * the starting routine should be in here!
	 * 
	 * @param parent
	 *            the Composite parent, which is the window of the Browser
	 */
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
			System.err.println("ControlService Not Found!");
		}

		setBackgroundImage(parent);
		createLoginComposite(parent);
		createTabFolderComposite(parent);

		if (sessionID != null) {
			addSessionIDToTabs(sessionID);
		}
	}

	/**
	 * This Method Creates and Formats the TabFolder Composite and places it
	 * into the given Composite The TabFolder has been changed to a CTabFolder,
	 * so a Tab can be closed!
	 * 
	 * @param parent
	 *            the Composite, in which the TabFolder should be placed in
	 * @return The Composite of the TabFilder
	 */
	private Composite createTabFolderComposite(Composite parent) {
		tabFolder = new CTabFolder(parent, SWT.NONE);
		tabFolder.setBackground(new Color(null, 0xf0, 0xf0, 0xf0));
		tabFolder.setLocation(CONTENT_SHIFT, HEADER_HEIGHT);
		int tabFolderHeight = parent.getSize().y - HEADER_HEIGHT;
		tabFolder.setSize(CONTENT_WITH, tabFolderHeight);
		FormData tabFormData = new FormData();
		tabFormData.top = new FormAttachment(homeControlComposite, 10);
		tabFormData.left = new FormAttachment(50, -CONTENT_WITH);
		tabFormData.right = new FormAttachment(50, CONTENT_WITH);
		tabFormData.bottom = new FormAttachment(100, -10);
		tabFolder.setLayoutData(tabFormData);
		startTabItemSeviceTracker();
		return tabFolder;
	}

	/**
	 * This Method created the Login Part for the Site.
	 * 
	 * @param parent
	 *            the Composite, in which the Login Composite should be placed
	 * @return the Composite of the Login part
	 */
	private Composite createLoginComposite(Composite parent) {
		homeControlComposite = new Composite(parent, SWT.FILL | SWT.CENTER);
		homeControlComposite.setLayout(new GridLayout(2, true));
		homeControlComposite.setLocation(CONTENT_SHIFT, 0);
		homeControlComposite.setSize(CONTENT_WITH, HEADER_HEIGHT);
		Image controlImage = PictureLoader.loadImageFromDatabase(CONTROLBACKGROUNDIMAGE);
		homeControlComposite.setBackgroundImage(controlImage);
		Image headerImage = PictureLoader.loadImageFromDatabase(LOGO);
		final int width = headerImage.getBounds().width;
		final int height = headerImage.getBounds().height;
		headerImage = new Image(display, headerImage.getImageData().scaledTo((int) (width * 0.5), (int) (height * 0.5)));
		Label headerLabel = new Label(homeControlComposite, SWT.LEFT);
		headerLabel.setImage(headerImage);
		loggedinHeader(null);
		FormData data1 = new FormData();
		data1.left = new FormAttachment(50, -CONTENT_WITH);
		data1.right = new FormAttachment(50, CONTENT_WITH);
		data1.top = new FormAttachment(0, 20);
		homeControlComposite.setLayoutData(data1);
		return homeControlComposite;
	}

	/**
	 * This Method formats the Header if a user is logged in
	 * 
	 * @param username
	 *            the username, that should be written here
	 */
	private void loggedinHeader(final String username) {
		if (loginComposite != null) {
			if (username == null || "".equals(username)) {
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						// loginComposite.dispose();
						// loginComposite = new
						// Composite(homeControlComposite,SWT.RIGHT);
						// loginComposite.setLayout(new GridLayout(2, true));
						// Label loggedinAsLabel = new Label(loginComposite,
						// SWT.NONE);
						// loggedinAsLabel.setText("Eingeloggt als " +
						// username);
						// loggedinAsLabel.setText("Eingeloggt als " +
						// username);
						nameLabel.setText("Benutzername oder eMail");
						System.out.println("Text sollte geändert sein!");
						nameText.setVisible(true);
						passwordLabel.setVisible(true);
						passwortText.setVisible(true);
						loginButton.setText("Login");
					}
				};
				updateContent(runnable);
			} else {
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						// loginComposite.dispose();
						// loginComposite = new
						// Composite(homeControlComposite,SWT.NONE);
						// loginComposite.setLayout(new FillLayout(SWT.NONE));
						// Label loggedinAsLabel = new Label(loginComposite,
						// SWT.NONE);
						// loggedinAsLabel.setText("Eingeloggt als " +
						// username);
						// loggedinAsLabel.setText("Eingeloggt als " +
						// username);
						nameLabel.setText("Eingeloggt als " + username);
						nameText.setVisible(false);
						passwordLabel.setVisible(false);
						passwortText.setVisible(false);
						loginButton.setText("Ausloggen");
						// System.out.println("test");
					}
				};
				updateContent(runnable);
			}
		} else {
			loginComposite = new Composite(homeControlComposite, SWT.RIGHT);
			loginComposite.setLayout(new GridLayout(2, true));
			GridData textGridData = new GridData();
			textGridData.horizontalAlignment = GridData.FILL;
			nameLabel = new Label(loginComposite, SWT.NONE);
			nameLabel.setText("Benutzername oder eMail");
			nameText = new Text(loginComposite, SWT.BORDER | SWT.FILL);
			nameText.setLayoutData(textGridData);
			/**
			 * Key listener, that is triggered, when the EnterKey is released
			 */
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
					// do nothing, wait for release
				}
			});
			passwordLabel = new Label(loginComposite, SWT.NONE);
			passwordLabel.setText("Passwort");
			passwortText = new Text(loginComposite, SWT.BORDER | SWT.PASSWORD | SWT.FILL);
			GridData gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			passwortText.setLayoutData(gridData);
			/**
			 * Key listener, that is triggered, when the EnterKey is released
			 */
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
					// do nothing, wait for release
				}

			});
			Label emptyLabel = new Label(loginComposite, SWT.NONE);
			emptyLabel.setVisible(false);
			loginButton = new Button(loginComposite, SWT.CENTER);
			loginButton.setText("Login");
			/**
			 * The Selectionlistener is triggered, when the Login Button is
			 * pressed
			 */
			loginButton.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1925540608622081691L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println(sessionID);
					if (user == null) {
						login();
					} else {
						logout();
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// do nothing here
				}
			});
		}
	}

	/**
	 * The Routine, that should be executed, when the user wants to logout.
	 * Everything that should happen, if a user logs in should be in here!
	 */
	private void logout() {
		if (controlService != null) {
			try {
				controlService.logout(sessionID);
				System.out.println("logged out!");
				loggedinHeader("");
				for (FolderItem folderitem : folderItems) {
					folderitem.setLogedInUser(null);
				}
				for (CTabItem tabItem : tabItems) {
					for (String loggedInTab : loggedInTabs) {
						if (!tabItem.isDisposed() && tabItem.getText().equals(loggedInTab)) {
							tabItem.dispose();
						}
					}
				}
				nameText.setText("");
				passwortText.setText("");
				addSessionIDToTabs(sessionID);
			} catch (CookieAppException e1) {
				e1.printStackTrace();
				System.err.println("Login Exception");
			}
		}
		user = null;
	}

	/**
	 * The Routine, that should be executed, when the user wants to login.
	 * Everything that should happen, if a user logs in should be in here!
	 */
	private void login() {
		if (controlService != null) {
			try {
				// System.out.println(sessionID);
				if (!controlService.login(sessionID, nameText.getText(), passwortText.getText())) {
					System.err.println("Wrong Password! Not logged in.");
					// TODO show Message on site
					return;
				}
				System.out.println("logged in as " + controlService.getCurrentUserName(sessionID));
				if (nameText.getText().equalsIgnoreCase(controlService.getCurrentUserName(sessionID)) || nameText.getText().equalsIgnoreCase(controlService.getCurrentUser(sessionID).geteMail())) {
					loggedinHeader(controlService.getCurrentUserName(sessionID));
					user = controlService.getCurrentUser(sessionID);
					for (FolderItem folderitem : folderItems) {
						folderitem.setLogedInUser(user);
						nameText.setText("");
						passwortText.setText("");
						for (String loggedInTab : loggedInTabs) {
							if (folderitem.getTabItemName().equals(loggedInTab)) {
								// General Composite for the Tab and the NumberArea
								Composite folderItemComposite = folderitem.getContent(tabFolder);
								// folderItemComposite.setParent(tabFolder);
								folderItemComposites.add(folderItemComposite);
								// The New Tab for the Composite
								CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
								tabItem.setText(folderitem.getTabItemName());
								tabItem.setControl(folderItemComposite);
								tabItems.add(tabItem);
								if (sessionID != null) {
									folderitem.setSessionID(sessionID);
								}
							}
						}
					}

					addSessionIDToTabs(sessionID);
					// TODO implement real database with user
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
	 * 
	 * @param parent
	 *            the Composite, which should have the default background image
	 */
	private void setBackgroundImage(Composite parent) {
		Image backgroundImage = PictureLoader.loadImageFromDatabase(BACKGROUNDIMAGE);
		final int width = backgroundImage.getBounds().width;
		final int height = backgroundImage.getBounds().height;
		backgroundImage = new Image(display, backgroundImage.getImageData().scaledTo((int) (width * 0.7), (int) (height * 0.7)));
		parent.setBackgroundImage(backgroundImage);
		parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
	}

	/**
	 * This Method reports the sessionID of the Current Session to all Tabs
	 * 
	 * @param sessionID
	 */
	private void addSessionIDToTabs(Long sessionID) {
		for (FolderItem folderItem : folderItems) {
			folderItem.setSessionID(sessionID);
		}
	}

	/**
	 * wird vor dem create Content aufgerufen... tabs zeichne methode muss
	 * angepasst werden!
	 */
	public void setFolderItem(final FolderItem folderItem) {
		if (folderItem != null && !(folderItems.contains(folderItem))) {
			updateContent(new Runnable() {

				public void run() {
					folderItems.add(folderItem);
					System.out.println("added " + folderItem.getTabItemName());
					boolean hidden = false;
					for (String loggedInTab : loggedInTabs) {
						if (folderItem.getTabItemName().equals(loggedInTab)) {
							hidden = true;
						}
					}
					if (!hidden) {
						// General Composite for the Tab and the NumberArea
						Composite folderItemComposite = folderItem.getContent(tabFolder);
						// folderItemComposite.setParent(tabFolder);
						folderItemComposites.add(folderItemComposite);

						// The New Tab for the Composite
						CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
						tabItem.setText(folderItem.getTabItemName());
						tabItem.setControl(folderItemComposite);
						tabItems.add(tabItem);
						if (sessionID != null) {
							folderItem.setSessionID(sessionID);
						}
					}

					/*
					 * Default TabItem has to be Selected!!!
					 */
					int defaultTabNumber = 0;
					if (folderItems.size() > 1 && !started) {
						for (int i = 1; i < folderItems.size(); i = i + 1) {
							if (folderItems.get(i).getTabItemName().equals(defaultTab)) {
								defaultTabNumber = i;
								started = true;
							}
							// TODO set Profile inivible and after login to visible
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
	 * starts the ServiceTracker, which adds the started tabs to the list and
	 * registers a osgi listener, which will be activated, if a tab service will
	 * be started
	 */
	public void startTabItemSeviceTracker() {
		serviceTrackerTabItem = new ServiceTracker<FolderItem, FolderItem>(context, FolderItem.class, new ServiceTrackerCustomizer<FolderItem, FolderItem>() {

			public FolderItem addingService(final ServiceReference<FolderItem> reference) {
				final FolderItem folderItem = context.getService(reference);
				if (folderItem != null)
					setFolderItem(folderItem);
				return folderItem;
			}

			public void modifiedService(final ServiceReference<FolderItem> reference, final FolderItem service) {/* ok */
			}

			public void removedService(final ServiceReference<FolderItem> reference, final FolderItem service) {
				final FolderItem folderItem = context.getService(reference);
				if (folderItem != null) {
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
		// started = true;
	}

	public void stopTabItemSeviceTracker() {
		if (serviceTrackerTabItem != null)
			serviceTrackerTabItem.close();
	}

	/**
	 * Updated the Page without reloading it
	 * 
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
	 * starts the ServiceTracker, which adds the started ControlService and
	 * registers a osgi listener, which will be activated, if a ControlService
	 * will be started
	 */
	public void startControlServiceSeviceTracker() {
		serviceTrackerControlService = new ServiceTracker<ControlService, ControlService>(context, ControlService.class, new ServiceTrackerCustomizer<ControlService, ControlService>() {

			public ControlService addingService(final ServiceReference<ControlService> reference) {
				final ControlService controlService = context.getService(reference);
				if (controlService != null) {
					MainPage.this.controlService = controlService;
				}
				return controlService;
			}

			public void modifiedService(final ServiceReference<ControlService> reference, final ControlService service) {/* ok */
			}

			public void removedService(final ServiceReference<ControlService> reference, final ControlService service) {
				final ControlService controlService = (ControlService) context.getService(reference);
				if (controlService != null) {
					MainPage.this.controlService = null;
				}
			}
		});
		Object[] services = serviceTrackerControlService.getServices();
		if (services != null) {
			for (int i = 0; i < services.length; i++) {
				if (services[i] != null) {
					MainPage.this.controlService = (ControlService) services[i];

				}
			}
		}
		serviceTrackerControlService.open();
		// started = true;
	}
}
