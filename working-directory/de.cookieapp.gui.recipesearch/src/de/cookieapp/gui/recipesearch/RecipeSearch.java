package de.cookieapp.gui.recipesearch;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
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
	private Composite resultComposite;
	private ArrayList<Label> results = new ArrayList<Label>();

	@Override
	public Composite getContent(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
		// tabFolder.setLayout(new FillLayout(SWT.VERTICAL));

		content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new GridLayout(4, true));
		content.setLocation(20, 10);

		Label headline = new Label(content, SWT.NONE);
		headline.setText("Suche hier nach neuen Rezepten");
		GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData.horizontalSpan = 4;
		gridData.horizontalIndent = 20;

		headline.setLayoutData(gridData);

		Label recipeName = new Label(content, SWT.NONE);
		recipeName.setText("Rezeptname");
		gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData.horizontalSpan = 1;
		gridData.horizontalIndent = 10;
		gridData.verticalIndent = 20;
		recipeName.setLayoutData(gridData);

		recipeNameT = new Text(content, SWT.BORDER);
		gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData.horizontalSpan = 3;
		gridData.horizontalIndent = 10;
		gridData.verticalIndent = 20;
		recipeNameT.setLayoutData(gridData);

		Button searchButton = new Button(content, SWT.NONE);
		searchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 4028437061777994602L;
			/**
			 * Actionlistener for search button
			 */
			public void widgetSelected(SelectionEvent e) {
				if (controlService != null) {
					Control[] controls = resultComposite.getChildren();
					for (Control control : controls) {
						control.dispose();
					}
					results.clear();
					try {
						ArrayList<Recipe> recipes = controlService.getRecipeByName(sessionID, recipeNameT.getText());
						showResults(content, recipes);
						resultComposite.setVisible(true);
					} catch (CookieAppException exception) {
						System.err.println("Kein Ergebnis!");
						Label resultLabel = new Label(resultComposite, SWT.NONE);
						resultLabel.setText("keine Ergebnisse gefunden");
						resultComposite.setVisible(true);
					}
				}
			}
		});
		searchButton.setText("Suchen");
		gridData = new GridData(GridData.BEGINNING, GridData.CENTER, true, false);
		gridData.horizontalSpan = 4;
		gridData.horizontalIndent = 10;
		searchButton.setLayoutData(gridData);

		resultComposite = new Composite(content, SWT.NONE);
		resultComposite.setLayout(new GridLayout(1, true));
		gridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		gridData.horizontalSpan = 4;
		gridData.horizontalIndent = 10;
		resultComposite.setLayoutData(gridData);
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

	public Composite showResults(final Composite parent, final ArrayList<Recipe> recipes) {
		//		Composite resultComposite = new Composite(parent, SWT.NONE);
		//		resultComposite.setLayout(new GridLayout(1, true));
		for(Control control : resultComposite.getChildren()){
			control.dispose();
		}
		Label resultHeader = new Label(resultComposite, SWT.BOLD);
		resultHeader.setText("Ergebnisse:");
		String fontName = resultHeader.getFont().getFontData()[0].getName();
		Font font = new Font(resultHeader.getDisplay(), fontName, 12, SWT.BOLD);
		resultHeader.setFont(font);
		for (Recipe recipe : recipes) {
			final Recipe recipeToShow = recipe;
			Label recipeNameLabel = new Label(resultComposite, SWT.NONE);
			results.add(recipeNameLabel);
			recipeNameLabel.setText(recipe.getName());
			recipeNameLabel.addMouseListener(new MouseListener() {
				private static final long serialVersionUID = -5558483000466339886L;
				@Override
				public void mouseUp(MouseEvent e) {
					showRecipe(recipeToShow, false);
				}

				@Override
				public void mouseDown(MouseEvent e) {
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					//showRecipe(recipeToShow, true);
				}
			});
		}
		parent.redraw();
		parent.pack();
		return parent;
	}

	/**
	 * Adds the Recipe as Tab to the TabFolder. 
	 * If select is true, the Tab will be opened automatically. 
	 * otherwise it will just be added to the Tab Folder
	 * @param recipe the Recipe to Show
	 * @param select a flag to show or not show the Tab
	 */
	private void showRecipe(Recipe recipe, boolean select) {
		CTabItem[] ctabs = tabFolder.getItems();
		boolean flag = false;
		for (CTabItem cTabItem : ctabs) {
			if (cTabItem.getText().equals(recipe.getName())) {
				flag = true;
			}
		}
		if (!flag)  {
			RecipeTabImpl recipeTab = new RecipeTabImpl();
			Composite recipeComp = recipeTab.getContent(tabFolder, recipe);
			CTabItem recipeTabItem = new CTabItem(tabFolder, SWT.CLOSE);
			recipeTabItem.setText(recipe.getName());
			recipeTabItem.setControl(recipeComp);
			if (select) {
				tabFolder.setSelection(recipeTabItem);
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
	}
}
