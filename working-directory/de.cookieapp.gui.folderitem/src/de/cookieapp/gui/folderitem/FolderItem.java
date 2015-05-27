package de.cookieapp.gui.folderitem;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;

import de.cookieapp.database.User;

public interface FolderItem {

	/**
	 * Getter Method for the Content this Tab prints
	 * @return a Composite with the Content
	 */
	public Composite getContent(CTabFolder tabFolder);
	
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
	
	public void setSessionID(Long sessionID);
	
	/**
	 * Sets that a specific user is logged in. Null if user is logged out or no user is logged in
	 * @param user
	 */
	public void setLogedInUser(User user);
	
}
