package de.cookieapp.gui.home;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.cookieapp.database.User;
import de.cookieapp.gui.folderitem.FolderItem;

public class Home implements FolderItem {

	//private Long sessionID;
	private User user;

	@Override
	public Composite getContent(CTabFolder tabFolder) {
		Composite completeComposite = new Composite(tabFolder, SWT.NONE);
		completeComposite.setLayout(new GridLayout(1,false));
		createHeader(completeComposite);
		createContent(completeComposite);
		return completeComposite;
	}
	
	/**
	 * Creates headline and log-off button
	 * @param completeComposite
	 */
	private void createHeader(Composite completeComposite) {
		Composite header = new Composite(completeComposite, SWT.NONE);
		header.setLayout(new GridLayout(1, false));
		Label headline = new Label(header, SWT.NONE);
		String username = ""; 
		if (user != null) {
			username = user.getName();
		}
		headline.setText("Willkommen " + username + "bei CookieApp,");
		Label headline2 = new Label(header, SWT.NONE);
		headline2.setText("der Webseite zum austauschen von Rezepten");		
	}
	
	/**
	 * Creates headline and log-off button
	 * @param completeComposite
	 */
	private void createContent(Composite completeComposite) {
		//TODO add some Content	
	}

	public String getTabItemName() {
		return "Home";
	}

	@Override
	public void setSpecificProperty(Object property) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setSessionID(Long sessionID) {
		//this.sessionID = sessionID;	
		//TODO implement, if needed somehow
	}

	@Override
	public void setLogedInUser(User user) {
		this.user = user;		
		// if user is null, no user is logged in!
	}

}
