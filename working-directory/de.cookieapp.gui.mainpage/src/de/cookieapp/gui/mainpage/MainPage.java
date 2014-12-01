package de.cookieapp.gui.mainpage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
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
	private Text lower_textfield;
	private Composite buttonArea;
	private TabFolder tabFolder;
	private Composite operatorArea;
	private List<FolderItem> folderItems = new ArrayList<FolderItem>();
	protected List<Composite> folderItemComposites = new ArrayList<Composite>();
	private List<TabItem> tabItems = new ArrayList<TabItem>();
	private BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
	private ServiceTracker serviceTrackerTabItem;


	@Override
	protected void createContents(Composite parent) {
		
		System.out.println("Main Page started");
		
		this.parent = parent;
		final ServerPushSession pushSession = new ServerPushSession();
		pushSession.start();

		parent.setLayout(new GridLayout(1,false));

		Composite textArea = new Composite(parent, SWT.NONE);

		lower_textfield = new Text(textArea, SWT.BORDER);
		lower_textfield.setEditable(false);
		lower_textfield.setTouchEnabled(true);
		lower_textfield.setBounds(10, 0, 318, 60);

		buttonArea = new Composite(parent, SWT.NONE);
		buttonArea.setLayout(new GridLayout(2, true));
		tabFolder = new TabFolder(buttonArea, SWT.NONE);
		checkTabfolderItems();
		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				/*
				int i = tabFolder.getSelectionIndex();
				lower_textfield.setText(numeralSystems.get(i).getBackup());
				ergebnis = false;
				 */
			}
		});

		operatorArea = new Composite(buttonArea, SWT.NONE);
		operatorArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		operatorArea.setLayout(new GridLayout());

		Button deleteButton = new Button(operatorArea, SWT.NONE);
		deleteButton.setLayoutData(new GridData(GridData.FILL_BOTH));
		deleteButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void widgetSelected(SelectionEvent e) {
				lower_textfield.setText("");
			}
		});
		deleteButton.setText("delete");

		Button revertButton = new Button(operatorArea, SWT.NONE);
		revertButton.setLayoutData(new GridData(GridData.FILL_BOTH));
		revertButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (lower_textfield.getText().endsWith(" ")) {
					lower_textfield.setText(lower_textfield.getText().substring(0, lower_textfield.getText().length() - 3));
				} else if (lower_textfield.getText().length() < 0){
					lower_textfield.setText(lower_textfield.getText().substring(0, lower_textfield.getText().length() - 1));
				}
			}
		});
		revertButton.setText("<--");
		
		startTabItemSeviceTracker();

		int numberOfTabItems = tabFolder.getItems().length;
		System.out.println("There are " + numberOfTabItems + " tabitems");
	}
	
	private void checkTabfolderItems() {
		System.out.println(folderItems.size());
		
		/*
		folderItems.add(folderItem);
		System.out.println("added " + folderItem.getTabItemName());
		//General Composite for the Tab and the NumberArea
		Composite folderItemComposite = folderItem.getContent();
		folderItemComposite.setParent(tabFolder);
		folderItemComposites.add(folderItemComposite);		

		//The New Tab for the Composite
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(folderItem.getTabItemName());
		tabItem.setControl(folderItemComposite);
		tabItems.add(tabItem);	
		*/
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
					 * 
					 * 
					int max = 0;
					if (numeralSystems.size() > 1 && !started) {
						for ( int i = 1;  i < numeralSystems.size();  i = i + 1){
							if (numeralSystems.get(max).getPriority() < numeralSystems.get(i).getPriority()){
									max = i;	
							}
						}
						tabFolder.setSelection(max);
					}
					buttonArea.layout();
					 */

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
					buttonArea.layout();
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
				System.out.println("pong");
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