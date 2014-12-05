package de.cookieapp.gui.recipe;

import org.eclipse.swt.widgets.Composite;

public interface RecipeTab {

	Composite getContent(Composite tabFolder);

	String getTabItemName();

}
