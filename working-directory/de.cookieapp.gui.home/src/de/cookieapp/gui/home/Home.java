package de.cookieapp.gui.home;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.cookieapp.gui.folderitem.FolderItem;

public class Home implements FolderItem {

	private Long sessionID;


	public Composite getContent(Composite tabFolder) {
		// TODO Auto-generated method stub
		return new Composite(tabFolder, SWT.NONE);
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
