package de.cookieapp.home;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.cookieapp.folderitem.FolderItem;

public class Home implements FolderItem {

	@Override
	public Composite getContent(Composite tabFolder) {
		// TODO Auto-generated method stub
		return new Composite(tabFolder, SWT.NONE);
	}

	@Override
	public String getTabItemName() {
		// TODO Auto-generated method stub
		return "Home";
	}

}
