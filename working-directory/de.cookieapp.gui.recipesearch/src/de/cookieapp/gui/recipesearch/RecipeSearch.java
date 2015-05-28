package de.cookieapp.gui.recipesearch;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.control.ControlService;
import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.data.model.User;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.gui.folderitem.FolderItem;
import de.cookieapp.gui.recipe.RecipeTabImpl;

public class RecipeSearch implements FolderItem {

	private ControlService controlService;
	private CTabFolder tabFolder;
	private Long sessionID;
	private Text recipeNameT;
	private Composite content;
	private Label secondResult;
	private Composite resultComposite;
	private Label firstResult;

	@Override
	public Composite getContent(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
		// tabFolder.setLayout(new FillLayout(SWT.VERTICAL));

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
				if (controlService != null) {
					try {
						ArrayList<Recipe> recipes = controlService
								.getRecipeByName(sessionID,
										recipeNameT.getText());
						// System.err.println("Recipe-List-Size: "+recipes.size());
						showResults(content, recipes);
						// resultComposite.setVisible(true);
					} catch (CookieAppException exception) {
						System.err.println("Kein Ergebnis!");
						firstResult.setText("keine Ergebnisse gefunden");
						secondResult.setVisible(false);
						resultComposite.setVisible(true);
					}
				}
			}
		});
		searchButton.setText("Suchen");
		Label blankLabel2 = new Label(content, SWT.NONE);
		blankLabel2.setVisible(false);

		resultComposite = new Composite(content, SWT.NONE);
		resultComposite.setLayout(new FillLayout(SWT.VERTICAL));
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

	public Composite showResults(final Composite parent,
			final ArrayList<Recipe> recipes) {
//		Composite resultComposite = new Composite(parent, SWT.NONE);
//		resultComposite.setLayout(new GridLayout(1, true));
		for(Control control : resultComposite.getChildren()){
			control.dispose();
		}
		Label resultHeader = new Label(resultComposite, SWT.BOLD);
		resultHeader.setText("Ergebnisse:");
		for (Recipe recipe : recipes) {
			System.err.println("Added button for " + recipe.getName());
			final Recipe recipeToShow = recipe;
			Label recipeNameLabel = new Label(resultComposite, SWT.NONE);
			recipeNameLabel.setText(recipe.getName());
			/*
			 * recipeNameLabel.addSelectionListener(new SelectionAdapter() {
			 * private static final long serialVersionUID = 1L;
			 * 
			 * public void widgetSelected(SelectionEvent e) {
			 * showRecipe(recipeToShow); System.err.println("KLICK!"); } });
			 */
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
			// showRecipe(recipeToShow);
		}
		parent.redraw();
		parent.pack();
		/*
		 * updateContent(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * Composite resultComposite = new Composite(parent, SWT.NONE);
		 * resultComposite.setLayout(new GridLayout(1, true)); for (Recipe
		 * recipe : recipes) { final Recipe recipeToShow = recipe; Label
		 * recipeNameLabel = new Label(resultComposite, SWT.NONE);
		 * recipeNameLabel.setText(recipe.getName());
		 * recipeNameLabel.addMouseListener(new MouseListener() { private static
		 * final long serialVersionUID = -5558483000466339886L;
		 * 
		 * @Override public void mouseUp(MouseEvent e) {
		 * showRecipe(recipeToShow); }
		 * 
		 * @Override public void mouseDown(MouseEvent e) { // TODO
		 * Auto-generated method stub }
		 * 
		 * @Override public void mouseDoubleClick(MouseEvent e) { // TODO
		 * Auto-generated method stub } }); showRecipe(recipeToShow); }
		 * parent.redraw(); } });
		 */

		return parent;
	}

	private void showRecipe(Recipe recipe) {
		RecipeTabImpl recipeTab = new RecipeTabImpl();
		Composite recipeComp = recipeTab.getContent(tabFolder, recipe);
		CTabItem recipeTabItem = new CTabItem(tabFolder, SWT.CLOSE);
		recipeTabItem.setText(recipe.getName());
		recipeTabItem.setControl(recipeComp);
		tabFolder.setSelection(recipeTabItem);
		tabFolder.pack();
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

	public void updateContent(final Runnable runnable) {

		Runnable bgRunnable = new Runnable() {
			public void run() {
				if (tabFolder != null) {
					tabFolder.getDisplay().asyncExec(runnable);
				} else {
					System.err.println("Parent is null");
				}
			};
		};
		Thread bgThread = new Thread(bgRunnable);
		bgThread.setDaemon(true);
		bgThread.start();
	}

	@Override
	public void setLogedInUser(User user) {
		// TODO Auto-generated method stub
		
	}
}
