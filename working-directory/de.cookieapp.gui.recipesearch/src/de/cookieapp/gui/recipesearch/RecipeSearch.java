package de.cookieapp.gui.recipesearch;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.cookieapp.control.ControlService;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.gui.folderitem.FolderItem;

public class RecipeSearch implements FolderItem{

	private ControlService controlService;
	private Composite tabFolder;
	private Long sessionID;

	@Override
	public Composite getContent(Composite tabFolder) {
		this.tabFolder = tabFolder;
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTabItemName() {
		// TODO Auto-generated method stub
		return "Suche nach Rezepten";
	}
	
	@Override
	public void setSpecificProperty(Object property) {
		// TODO Auto-generated method stub
		
	}
	
	private void showRecipe(Recipe recipe) {
		Control[] controls = tabFolder.getChildren();
		for (Control control : controls) {
			if (control instanceof TabFolder) {
				TabFolder tabFolder = (TabFolder) control;
				TabItem[] items = tabFolder.getItems();
				for (TabItem tabItem : items) {
//					if (tabItem.get)
				}
			}
		}
		
	}
	
	@Override
	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
	}

	public void setControlService(ControlService controlService) {
		if (controlService != null) {
			this.controlService = controlService;
		}
	}
	
	public void unsetControlService(ControlService controlService) {
		if (this.controlService.equals(controlService)) {
			this.controlService = null;
		}
	}
}
