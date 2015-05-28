package de.cookieapp.gui.recipe;

import org.eclipse.swt.widgets.Composite;

import de.cookieapp.data.model.Recipe;

public interface RecipeTab {

	Composite getContent(Composite tabFolder, Recipe recipe);

	String getTabItemName();

}
