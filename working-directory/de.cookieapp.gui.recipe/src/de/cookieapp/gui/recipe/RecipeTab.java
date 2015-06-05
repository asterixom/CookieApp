package de.cookieapp.gui.recipe;

import org.eclipse.swt.widgets.Composite;

import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;

public interface RecipeTab {

	Composite getContent(Composite tabFolder, Recipe recipe);

	String getTabItemName();

	void setUser(User user);
}
