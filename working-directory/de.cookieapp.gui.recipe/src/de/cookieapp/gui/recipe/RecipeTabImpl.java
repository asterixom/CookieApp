package de.cookieapp.gui.recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.database.impl.DataProviderImpl;
import de.cookieapp.util.PictureLoader;

public class RecipeTabImpl implements RecipeTab {
	
	private Recipe recipe;
	private Display display;
	
	@Override
	public Composite getContent(Composite tabFolder, Recipe recipe) {
		Composite completeComposite = new Composite(tabFolder, SWT.NONE);
		completeComposite.setLayout(new GridLayout(1,false));
		this.recipe = recipe;
		createHeader(completeComposite);
		
		createIngredientsArea(completeComposite);
		createDescriptionArea(completeComposite);
		//createContent(completeComposite);
		createCommentArea(completeComposite);
		
		return completeComposite;
	}
	
	/**
	 * Creates Header. This contains the Picture and the InformationArea
	 * @param completeComposite
	 */
	private void createHeader(Composite completeComposite) {
		Composite header = new Composite(completeComposite, SWT.NONE);
		header.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//Composite imageComposite = new Composite(header, SWT.NONE);
		recipe.debugDumpExtended();
		Image trollFace = null;
		if (recipe.getImage() != null) {
			String temp = recipe.getImage();
			trollFace = PictureLoader.loadImageFromDatabase(PictureLoader.RECIPEREPO + recipe.getImage());
		} else {
			trollFace = PictureLoader.loadImageFromDatabase(PictureLoader.TROLLFACE);
		}
		final int width = trollFace.getBounds().width;
		final int height = trollFace.getBounds().height;
		trollFace = new Image(display, trollFace.getImageData().scaledTo((int) (width * 0.3), (int) (height * 0.3)));
		//imageComposite.setBackgroundImage(trollFace);
		Label imageLabel = new Label(header, SWT.NONE);
		imageLabel.setImage(trollFace);

		createInformationArea(header);
		
		
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
		//TODO Platzhalter f�r Bild einf�gen oder Methode f�r getRecipeImage() einbinden [�berpr�fen, ob richtig]
		Image recipePic = PictureLoader.loadImageFromDatabase(PictureLoader.DEFAULTPIC);
		contentTL.setBackgroundImage(recipePic);
		
		Composite contentTR = new Composite(content, SWT.NONE);
		contentTR.setLayout(new GridLayout(2, false));
		Label recipeName = new Label(contentTR, SWT.NONE);
		recipeName.setText(recipe.getName()); //TODO Methode getRecipeName() einf�gen
		recipeName.setFont(new Font( completeComposite.getDisplay(), "Verdana", 18, SWT.BOLD ) ); //TODO ggf. CSS-Tag Headlie1 einf�gen
		
		
		
		Composite contentBL = new Composite(content, SWT.NONE);
		contentBL.setLayout(new GridLayout(2, false));
		//Tabelle einf�gen und dynamisch mit Zutaten und Mengenangaben f�llen
		Table table = new Table(contentBL, SWT.NONE);
		TableColumn col1 = new TableColumn(table, SWT.NONE);
		col1.setText("Zutat");
		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col2.setText("Menge");
		// TODO implement this
		/*
		for(Ingredient ingredient : recipe.getIngredients()){
			TableItem ti = new TableItem(table, SWT.NONE);
			ti.setText(0, ingredient.getNameId()+"");
			ti.setText(1,ingredient.getQuantity()+" "+ingredient.getUnit().name());
		}
		*/
		Composite contentBR = new Composite(content, SWT.NONE);
		contentBR.setLayout(new GridLayout(1, false));
		Text method = new Text(contentBR, SWT.BORDER);
		method.setEditable(false);
		method.setTouchEnabled(true);
		method.setText(recipe.getDescription()); //TODO getMethod() einf�gen
		
	}

	/**
	 * Creates the Footer, here is Comment and a Favorite Button
	 * @param completeComposite
	 */
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
	
	private void createInformationArea(Composite headerComposite) {
		Composite informationArea = new Composite(headerComposite, SWT.NONE);
		informationArea.setLayout(new FillLayout(SWT.VERTICAL));
		Label recipeName = new Label(informationArea, SWT.NONE);
		recipeName.setText(recipe.getName());
		
		Label recipeCreator = new Label(informationArea, SWT.NONE);
		recipeCreator.setText(recipe.getCreator().getName());
		
		Label recipeCreated = new Label(informationArea, SWT.NONE);
		recipeCreated.setText(recipe.getCreated().toString());		
	}
	
	private void createIngredientsArea(Composite contentComposite) {
		Composite ingredientArea = new Composite(contentComposite, SWT.NONE);
		ingredientArea.setLayout(new FillLayout(SWT.VERTICAL));
		Iterator<Ingredient> ingredients = recipe.getIngredients().iterator();
		if (ingredients.hasNext()) {
			Label ingredientsText = new Label(ingredientArea, SWT.NONE);
			ingredientsText.setText("Zutaten:");
			while(ingredients.hasNext()) {
				ingredientsText = new Label(ingredientArea, SWT.NONE);
				Ingredient ingredient = ingredients.next();
				ingredientsText.setText(ingredient.getQuantity() + " " + ingredient.getUnit() + " " + ingredient.getName());
			}
		}
		recipe.debugDumpExtended();
	}
	
	private void createDescriptionArea(Composite contentComposite) {
		Composite descriptionArea = new Composite(contentComposite, SWT.NONE);
		descriptionArea.setLayout(new FillLayout(SWT.VERTICAL));
		Label ingredientsText = new Label(descriptionArea, SWT.NONE);
		ingredientsText.setText("Anleitung: \n" + recipe.getDescription());
	}
	
	private void createCommentArea(Composite contentComposite) {
		Composite commentArea = new Composite(contentComposite, SWT.NONE);
		commentArea.setLayout(new FillLayout(SWT.VERTICAL));		
		Iterator<Comment> comments = recipe.getComments().iterator();
		if (comments.hasNext()) {
			Label commentsText = new Label(commentArea, SWT.NONE);
			commentsText.setText("Kommentare:");
			while(comments.hasNext()) {
				commentsText = new Label(commentArea, SWT.NONE);
				Comment comment = comments.next();
				commentsText.setText(comment.getCreator().getName() + "\n" + comment.getContent());
			}
		}
	}

	@Override
	public String getTabItemName() {
		return recipe.getName();
	}
}
