package de.cookieapp.gui.home;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;

import de.cookieapp.gui.folderitem.FolderItem;

public class Home implements FolderItem {

	private Long sessionID;


	@Override
	public Composite getContent(TabFolder tabFolder) {
		Composite completeComposite = new Composite(tabFolder, SWT.NONE);
		completeComposite.setLayout(new GridLayout(1,false));
		
		createHeader(completeComposite);
		//createContent(completeComposite);

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
		headline.setText("Willkommen bei CookieApp,");
		Label headline2 = new Label(header, SWT.NONE);
		headline2.setText("der Webseite zum austauschen von Rezepten");		
	}

	public String getTabItemName() {
		// TODO Auto-generated method stub
		return "Home";
	}

	@Override
	public void setSpecificProperty(Object property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;		
	}

}
