package de.cookieapp.gui.recipe;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;

public class RecipeTabImpl implements RecipeTab {
	
	private Recipe recipe;
	private Display display;
	private static final String PLACEHOLDER = "resources/platzhalter.png";
	
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
		//TODO Platzhalter für Bild einfügen oder Methode für getRecipeImage() einbinden [überprüfen, ob richtig]
		Image recipePic = loadImage(PLACEHOLDER);
		contentTL.setBackgroundImage(recipePic);
		
		Composite contentTR = new Composite(content, SWT.NONE);
		contentTR.setLayout(new GridLayout(2, false));
		Label recipeName = new Label(contentTR, SWT.NONE);
		recipeName.setText(recipe.getName()); //TODO Methode getRecipeName() einfügen
		recipeName.setFont(new Font( completeComposite.getDisplay(), "Verdana", 18, SWT.BOLD ) ); //TODO ggf. CSS-Tag Headlie1 einfügen
		
		
		
		Composite contentBL = new Composite(content, SWT.NONE);
		contentBL.setLayout(new GridLayout(2, false));
		//Tabelle einfügen und dynamisch mit Zutaten und Mengenangaben füllen
		Table table = new Table(contentBL, SWT.NONE);
		TableColumn col1 = new TableColumn(table, SWT.NONE);
		col1.setText("Zutat");
		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col2.setText("Menge");
		
		for(Ingredient ingredient : recipe.getIngredients()){
			TableItem ti = new TableItem(table, SWT.NONE);
			ti.setText(0, ingredient.getNameId()+"");
			ti.setText(1,ingredient.getQuantity()+" "+ingredient.getUnit().name());
		}
		
		Composite contentBR = new Composite(content, SWT.NONE);
		contentBR.setLayout(new GridLayout(1, false));
		Text method = new Text(contentBR, SWT.BORDER);
		method.setEditable(false);
		method.setTouchEnabled(true);
		method.setText(recipe.getDescription()); //TODO getMethod() einfügen
		
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
			 * 
			 */
			private static final long serialVersionUID = 1546174202494990071L;

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
		
		return recipe.getName(); //den Rezeptnamen returnen
	}
	
	public Image loadImage(String name) {
		Image result = null;
		InputStream stream = RecipeTabImpl.class.getClassLoader().getResourceAsStream( name );
		if( stream != null ) {
			try {
				result = new Image(display, stream);
			} finally {
				try {
					stream.close();
				} catch( IOException unexpected ) {
					throw new RuntimeException( "Failed to close image input stream", unexpected );
				}
			}
		}
		return result;
	}

}
