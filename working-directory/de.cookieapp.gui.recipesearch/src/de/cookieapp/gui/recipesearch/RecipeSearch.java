package de.cookieapp.gui.recipesearch;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.control.ControlService;
import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.dataimpl.Recipe;
import de.cookieapp.gui.folderitem.FolderItem;
import de.cookieapp.gui.recipe.RecipeTabImpl;

public class RecipeSearch implements FolderItem{

	private ControlService controlService;
	private Composite tabFolder;
	private Long sessionID;
	private Text recipeNameT;
	private Composite content;

	@Override
	public Composite getContent(Composite tabFolder) {
		this.tabFolder = tabFolder;
		content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new GridLayout(2, true));

		Label headline = new Label(content, SWT.NONE);
		headline.setText("Suche hier nach neuen Rezepten");

		Label blankLabel = new Label(content, SWT.NONE);
		blankLabel.setVisible(false);

		Label recipeName = new Label(content, SWT.NONE);
		recipeName.setText("Rezeptname");
		recipeNameT = new Text(content, SWT.BORDER);

		Button searchButton = new Button(content, SWT.NONE);
		searchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 4028437061777994602L;
			/**
			 * Actionlistener for register button
			 */
			public void widgetSelected(SelectionEvent e) {
				if(controlService != null) {
					try {
						ArrayList<Recipe> recipes = controlService.getRecipeByName(sessionID, recipeNameT.getText());
						showResults(content, recipes);
					} catch (CookieAppException exception) {
						System.err.println("Registation Failed!");
					}
				}
			}
		});
		searchButton.setText("Suchen");
		return content;
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

	public Composite showResults(Composite parent, ArrayList<Recipe> recipes) {
		Composite resultComposite = new Composite(parent, SWT.NONE);
		resultComposite.setLayout(new GridLayout(1, true));
		for (Recipe recipe : recipes) {
			final Recipe recipeToShow = recipe;
			Label recipeNameLabel = new Label(resultComposite, SWT.NONE);
			recipeNameLabel.setText(recipe.getName());
			recipeNameLabel.addMouseListener(new MouseListener() {
				private static final long serialVersionUID = -5558483000466339886L;
				@Override
				public void mouseUp(MouseEvent e) {
					showRecipe(recipeToShow);
				}
				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});
		}
		parent.redraw();
		return parent;
	}

	private void showRecipe(Recipe recipe) {
		RecipeTabImpl recipeTab = new RecipeTabImpl();
		Composite recipeComp = recipeTab.getContent(tabFolder, recipe);
		Control[] controls = tabFolder.getChildren();

		for (Control control : controls) {
			if (control instanceof TabFolder) {
				TabFolder tabFolder = (TabFolder) control;
				TabItem recipeTabItem = new TabItem(tabFolder, SWT.NONE);
				recipeTabItem.setText(recipe.getName());
				recipeTabItem.setControl(recipeComp);
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
