package de.cookieapp.gui.recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.control.ControlService;
import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;
import de.cookieapp.database.impl.DataProviderImpl;
import de.cookieapp.util.PictureLoader;

public class RecipeTabImpl implements RecipeTab {

	private Recipe recipe;
	private Display display;
	private User user;
	private Composite completeComposite;
	private Composite createCommentArea;
	private ControlService controlService;
	private Composite commentArea;

	@Override
	public Composite getContent(Composite tabFolder, Recipe recipe) {
		completeComposite = new Composite(tabFolder, SWT.NONE);
		completeComposite.setLayout(new GridLayout(1,false));
		this.recipe = recipe;
		createHeader(completeComposite);

		createIngredientsArea(completeComposite);
		createDescriptionArea(completeComposite);
		//createContent(completeComposite);
		createCommentArea(completeComposite);
		createCreateCommentArea(completeComposite);

		return completeComposite;
	}

	/**
	 * Creates Header. This contains the Picture and the InformationArea
	 * @param completeComposite
	 */
	private void createHeader(Composite completeComposite) {
		Composite header = new Composite(completeComposite, SWT.NONE);
		header.setLayout(new FillLayout(SWT.HORIZONTAL));

		recipe.debugDumpExtended();
		Image trollFace = null;
		if (recipe.getImage() != null) {
			trollFace = PictureLoader.loadImageFromDatabase(PictureLoader.RECIPEREPO + recipe.getImage());
		} else {
			trollFace = PictureLoader.loadImageFromDatabase(PictureLoader.TROLLFACE);
		}
		final int width = trollFace.getBounds().width;
		final int height = trollFace.getBounds().height;
		trollFace = new Image(display, trollFace.getImageData().scaledTo(250, 150));
		Label imageLabel = new Label(header, SWT.NONE);
		imageLabel.setImage(trollFace);

		createInformationArea(header);

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
		
		createFavoriteArea(informationArea);

	}

	private void createFavoriteArea(Composite informationArea) {
		Image favoriteImgage = null;
		if (user != null && controlService.isFavorite(user.getId(), recipe.getId())) {
			favoriteImgage = PictureLoader.loadImageFromDatabase(PictureLoader.BOOKMARKPIC);
		} else {
			favoriteImgage = PictureLoader.loadImageFromDatabase(PictureLoader.NOBOOKMARKTPIC);
		}
		final int width = favoriteImgage.getBounds().width;
		final int height = favoriteImgage.getBounds().height;
		favoriteImgage = new Image(display,favoriteImgage.getImageData().scaledTo(50, 50));
		Label imageLabel = new Label(informationArea, SWT.NONE);
		imageLabel.setImage(favoriteImgage);
		imageLabel.addMouseListener(new MouseListener() {
			private static final long serialVersionUID = 4736903562041234338L;
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Add to Bookmark
				if (user != null && !controlService.isFavorite(user.getId(), recipe.getId())) {
					System.out.println("User is logged in, can save bookmark");
					controlService.saveFavorite(user.getId(), recipe.getId());
				} else if (user != null && controlService.isFavorite(user.getId(), recipe.getId())) {
					System.out.println("User is logged in, wants to delete bookmark");
					controlService.removeFavorite(user.getId(), recipe.getId());
				} else {
					System.out.println("Debug: User not logged in, cant save Bookmark");

				}
			}
			
			@Override
			public void mouseDown(MouseEvent e) {			
			}		
			@Override
			public void mouseDoubleClick(MouseEvent e) {			
			}
		});
	}

	private void createIngredientsArea(Composite contentComposite) {
		Composite ingredientArea = new Composite(contentComposite, SWT.NONE);
		ingredientArea.setLayout(new RowLayout(SWT.VERTICAL));
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
		if (commentArea != null) {
			commentArea.dispose();
		}
		commentArea = new Composite(contentComposite, SWT.BORDER);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		commentArea.setLayout(rowLayout);		
		Iterator<Comment> comments = recipe.getComments().iterator();
		if (comments.hasNext()) {
			Label commentsText = new Label(commentArea, SWT.NONE);
			commentsText.setText("Kommentare:");
			
			while(comments.hasNext()) {
				Composite comentComposite = new Composite(commentArea, SWT.BORDER);
				GridLayout gridLayout = new GridLayout(2, true);
				comentComposite.setLayout(gridLayout);
				
				Comment comment = comments.next();
				
				commentsText = new Label(comentComposite, SWT.NONE);
				commentsText.setText(comment.getCreator().getName());
				GridData griddata = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
				commentsText.setLayoutData(griddata);
				
				commentsText = new Label(comentComposite, SWT.NONE);
				commentsText.setText(comment.getCreated().toString());
				griddata = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
				commentsText.setLayoutData(griddata);

				commentsText = new Label(comentComposite, SWT.NONE);
				commentsText.setText(comment.getContent());
				griddata = new GridData(SWT.LEFT, SWT.CENTER, true, true, 2, 1);
				commentsText.setLayoutData(griddata);

			}
		}
	}

	public void createCreateCommentArea(final Composite contentComposite) {
		if (createCommentArea != null) {
			createCommentArea.dispose();
		}
		createCommentArea = new Composite(contentComposite, SWT.BORDER);
		createCommentArea.setLayout(new RowLayout(SWT.VERTICAL));
		if (user == null) {
			Label pleaseLoginLabel = new Label(createCommentArea, SWT.NONE);
			pleaseLoginLabel.setText("Um die Kommentarfunktion nutzen zu können ist es nötig sich einzuloggen!");
		} else {
			final Text commentaryLabel = new Text(createCommentArea, SWT.BORDER | SWT.MULTI);
			Button createCommentaryButton = new Button(createCommentArea, SWT.PUSH | SWT.RIGHT);
			createCommentaryButton.setText("Kommentar abschicken");
			createCommentButton(contentComposite, commentaryLabel, createCommentaryButton);
		}
	}

	private void createCommentButton(final Composite contentComposite,
			final Text commentaryLabel, Button createCommentaryButton) {
		createCommentaryButton.addMouseListener(new MouseListener() {
			private static final long serialVersionUID = 6119469308002757061L;
			@Override
			public void mouseUp(MouseEvent e) {
				if (commentaryLabel != null && commentaryLabel.getText() != null && user != null) {
					controlService.saveComment(commentaryLabel.getText(), user, recipe);
					
					createCommentArea.dispose();
					createCommentArea(contentComposite);

					createCommentArea = new Composite(contentComposite, SWT.NONE);
					createCommentArea.setLayout(new RowLayout(SWT.VERTICAL));
					Label commentCreatedLabel = new Label(createCommentArea, SWT.NONE);
					// set FontColor to Green
					commentCreatedLabel.setForeground(new Color(Display.getCurrent(),34, 139, 34));
					commentCreatedLabel.setText("Kommentar erfolgreich hinzugefügt!");
					completeComposite.pack();
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
	}

	@Override
	public String getTabItemName() {
		return recipe.getName();
	}

	public void setUser(User user) {
		this.user = user;
		if (completeComposite != null) {
			createCreateCommentArea(completeComposite);
		}
	}
	
	public void setUsera(User user) {
		this.user = user;
		if (completeComposite != null) {
			createCreateCommentArea(completeComposite);
		}
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
