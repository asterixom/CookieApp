package de.cookieapp.gui.profile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;

import de.cookieapp.control.ControlService;
import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;
import de.cookieapp.gui.folderitem.FolderItem;
import de.cookieapp.gui.recipe.RecipeTabImpl;
import de.cookieapp.util.PictureLoader;

public class Profile implements FolderItem {

	@SuppressWarnings("unused")
	private Long sessionID;
	private Display display;
	private static final String PLACEHOLDER = "resources/platzhalter.png";
	@SuppressWarnings("unused")
	private User user;
	
	private Composite completeComposite;
	private Composite favoriteArea;
	private Composite ownRecipeArea;
	private ControlService controlService;
	private CTabFolder tabFolder;

	@Override
	public Composite getContent(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
		completeComposite = new Composite(tabFolder, SWT.NONE);
		completeComposite.setLayout(new GridLayout(1,false));
		if (user != null) {
			createHeader(completeComposite);
			createContent(completeComposite);
		}
		return completeComposite;
	}

	/**
	 * Creates headline and log-off button
	 * @param completeComposite
	 */
	private void createHeader(Composite completeComposite) {
		Composite header = new Composite(completeComposite, SWT.NONE);
		header.setLayout(new GridLayout(2, false));
		Label headline = new Label(header, SWT.NONE);
		headline.setText("Mein Profil");
		Button logOff = new Button(header, SWT.NONE);
		logOff.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1877048083214961169L;
			/**
			 * Actionlistener for log-off button
			 */
			public void widgetSelected(SelectionEvent e) {
				//TODO log-off implementieren
			}
		});
		logOff.setText("Ausloggen");

	}

	/**
	 * Creates contentComposite and fills it
	 * @param completeComposite
	 */
	private void createContent(Composite completeComposite) {
		Composite content = new Composite(completeComposite, SWT.NONE);
		content.setLayout(new GridLayout(2, false));

		Composite pictureComposite = new Composite(content, SWT.RIGHT);
		pictureComposite.setLayout(new GridLayout(1, false));
		Image profilePic = PictureLoader.loadImageFromDatabase(PictureLoader.NOUSERPIC);
		profilePic = new Image(display, profilePic.getImageData().scaledTo(100, 100));
		Label imageLabel = new Label(pictureComposite, SWT.NONE);
		imageLabel.setImage(profilePic);
		pictureComposite.setBackgroundImage(profilePic);
		
		Composite contentTR = new Composite(content, SWT.NONE);
		contentTR.setLayout(new GridLayout(1, false));
		Label username = new Label(contentTR, SWT.NONE);

		username.setText(user.getName());
		username.setFont(new Font(contentTR.getDisplay(), "Verdana", 24, SWT.BOLD )); 

		createOwnRecipeArea(content);
		createFavoriteArea(content);

		

	}
	
	private void createOwnRecipeArea(Composite contentComposite) {
		if (ownRecipeArea != null) {
			ownRecipeArea.dispose();
		}
		ownRecipeArea = new Composite(contentComposite, SWT.BORDER);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		ownRecipeArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 2, 1));
		ownRecipeArea.setLayout(rowLayout);		
		Set<Recipe> recipeSet = user.getRecipes();
		if (recipeSet != null && recipeSet.iterator().hasNext()) {
			Iterator<Recipe> recipes = user.getRecipes().iterator();
			Label recipeLabel = new Label(ownRecipeArea, SWT.NONE);
			recipeLabel.setText("Meine Rezepte:");
			
			while(recipes.hasNext()) {
				Composite comentComposite = new Composite(ownRecipeArea, SWT.NONE);
				GridLayout gridLayout = new GridLayout(2, true);
				comentComposite.setLayout(gridLayout);
				
				final Recipe recipe = recipes.next();
				
				recipeLabel = new Label(comentComposite, SWT.NONE);
				recipeLabel.setText(recipe.getName());
				recipeLabel.addMouseListener(new MouseListener() {
					private static final long serialVersionUID = -5558483000466339886L;
					@Override
					public void mouseUp(MouseEvent e) {
						showRecipe(recipe, false);
					}

					@Override
					public void mouseDown(MouseEvent e) {
					}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
					}
				});
				GridData griddata = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
				recipeLabel.setLayoutData(griddata);
			
			}
		} else {
			Label recipeLabel = new Label(ownRecipeArea, SWT.NONE);
			recipeLabel.setText("Noch keine Rezepte erstellt!");
		}
	}
	
	private void createFavoriteArea(Composite contentComposite) {
		if (favoriteArea != null) {
			favoriteArea.dispose();
		}
		favoriteArea = new Composite(contentComposite, SWT.BORDER);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		favoriteArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 2, 1));
		favoriteArea.setLayout(rowLayout);		
		Set<Recipe> recipeSet = user.getFavorites();
		if (recipeSet != null && recipeSet.iterator().hasNext()) {
			Iterator<Recipe> recipes = recipeSet.iterator();
			Label recipeLabel = new Label(favoriteArea, SWT.NONE);
			recipeLabel.setText("Meine Favoriten:");
			
			while(recipes.hasNext()) {
				Composite comentComposite = new Composite(favoriteArea, SWT.NONE);
				GridLayout gridLayout = new GridLayout(2, true);
				comentComposite.setLayout(gridLayout);
				
				final Recipe recipe = recipes.next();
				
				recipeLabel = new Label(comentComposite, SWT.NONE);
				recipeLabel.setText(recipe.getName());
				recipeLabel.addMouseListener(new MouseListener() {
					private static final long serialVersionUID = -5558483000466339886L;
					@Override
					public void mouseUp(MouseEvent e) {
						showRecipe(recipe, false);
					}

					@Override
					public void mouseDown(MouseEvent e) {
					}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
					}
				});
				
				GridData griddata = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
				recipeLabel.setLayoutData(griddata);
			}
		} else {
			Label recipeLabel = new Label(favoriteArea, SWT.NONE);
			recipeLabel.setText("Noch keine Favoriten Ausgewählt!");
		}
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
			recipeTab.setControlService(controlService);
			if (user != null) {
				recipeTab.setUser(user);
			}
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
	public void setSpecificProperty(Object property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
	}


	@Override
	public String getTabItemName() {
		return "Mein Profil";
	}

	@Override
	public void setLogedInUser(User user) {
		if(user==null && completeComposite != null){
			completeComposite.dispose();
		}
		this.user = user;
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
