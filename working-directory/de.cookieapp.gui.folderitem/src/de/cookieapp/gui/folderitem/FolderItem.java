package de.cookieapp.gui.folderitem;

import org.eclipse.swt.widgets.Composite;

public interface FolderItem {

	/**
	 * Getter Method for the Content this Tab prints
	 * @return a Composite with the Content
	 */
	public Composite getContent(Composite tabFolder);
	
	/**
	 * Getter Method for the Text, that should stand on the TabItem from the TabFolder
	 * @return the TabItemName as String
	 */
	public String getTabItemName();
	
	/**
	 * Sets a Property specific for the implementaton
	 * @param property
	 */
	public void setSpecificProperty(Object property);
}
