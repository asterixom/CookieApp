package de.cookieapp.gui.recipe;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.data.model.Recipe;
import de.cookieapp.gui.folderitem.FolderItem;

public class RecipeTabImpl implements RecipeTab {
	
	private Recipe recipe;
	
	@Override
	public Composite getContent(Composite tabFolder, Recipe recipe) {
		Composite completeComposite = new Composite(tabFolder, SWT.NONE);
		completeComposite.setLayout(new GridLayout(1,false));
		this.recipe = recipe;
		createHeader(completeComposite);
		createContent(completeComposite);
		createFooter(completeComposite);
		createCommentArea(completeComposite);

		return completeComposite;
	}
	
	/**
	 * Creates headline and log-off button
	 * @param completeComposite
	 */
	private void createHeader(Composite completeComposite) {
		Composite header = new Composite(completeComposite, SWT.NONE);
		header.setLayout(new GridLayout(2, false));
		
	}
	
	/**
	 * Creates contentComposite and fills it
	 * @param completeComposite
	 */
	private void createContent(Composite completeComposite) {
		Composite content = new Composite(completeComposite, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		
		Composite contentTL = new Composite(content, SWT.NONE);
		contentTL.setLayout(new GridLayout(1, false));
		//TODO Platzhalter f�r Bild einf�gen oder Methode f�r getRecipeImage() einbinden
		
		Composite contentTR = new Composite(content, SWT.NONE);
		contentTR.setLayout(new GridLayout(2, false));
		Label recipeName = new Label(contentTR, SWT.NONE);
		recipeName.setText(recipe.getName()); //TODO Methode getRecipeName() einf�gen
		recipeName.setFont(new Font( completeComposite.getDisplay(), "Verdana", 18, SWT.BOLD ) ); //TODO ggf. CSS-Tag Headlie1 einf�gen
		
		
		
		Composite contentBL = new Composite(content, SWT.NONE);
		contentBL.setLayout(new GridLayout(2, false));
		//TODO Tabelle einf�gen und dynamisch mit Zutaten und Mengenangaben f�llen
		
		Composite contentBR = new Composite(content, SWT.NONE);
		contentBR.setLayout(new GridLayout(1, false));
		Text method = new Text(contentBR, SWT.BORDER);
		method.setEditable(false);
		method.setTouchEnabled(true);
		method.setText("method"); //TODO getMethod() einf�gen
		
	}

	private void createFooter(Composite completeComposite) {
		Composite footerComposite = new Composite(completeComposite, SWT.NONE);
		footerComposite.setLayout(new GridLayout(2, false));
		Label owner = new Label(footerComposite, SWT.NONE);
		owner.setText("Dieses Rezept ist von " + recipe.getCreator().getName()); //TODO noch dynamisch anpassen
		Button favorite = new Button(footerComposite, SWT.NONE);
		//TODO ggf. ueberpruefen, ob schon Favorit und noch if-selection fuer unterschiedliche Buttons
		favorite.addSelectionListener(new SelectionAdapter() {
			/**
			 * Actionlistener for favorite-button
			 */
			public void widgetSelected(SelectionEvent e) {
				//TODO Favoriten implementieren
			}
		});
		favorite.setText("Zu Favoriten hinzufuegen");
	}
	
	private void createCommentArea(Composite completeComposite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTabItemName() {
		
		return recipe.getName(); //TODO den Rezeptnamen returnen
	}

}
