package de.cookieapp.folderitem;

import org.eclipse.swt.widgets.Composite;

public interface FolderItem {

	/**
	 * Getter Method for the Content this Tab prints
	 * @return a Composite with the Content
	 */
	public Composite getContent();
	
	/**
	 * Getter Method for the Text, that should stand on the TabItem from the TabFolder
	 * @return the TabItemName as String
	 */
	public String getTabItemName();
}
