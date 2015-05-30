package de.cookieapp.gui.createrecipe;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.data.model.User;
import de.cookieapp.gui.folderitem.FolderItem;

public class CreateRecipeTab implements FolderItem {

	private User user;
	private Long sessionID;
	private Composite ingredientsComposite;
	private final ArrayList<Text> ingredientName = new ArrayList<>();
	private final ArrayList<Text> ingredientQuantity = new ArrayList<>();
	private final ArrayList<Text> ingredientUnit = new ArrayList<>();


	@Override
	public Composite getContent(CTabFolder tabFolder) {
		Composite contentComposite = new Composite(tabFolder, SWT.NONE);
		contentComposite.setLayout(new FillLayout(SWT.VERTICAL));

		createBody(contentComposite);		
		return contentComposite;
	}

	private void createBody(Composite contentComposite) {
		Composite informationArea = new Composite(contentComposite, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, false);
		informationArea.setLayout(gridLayout);
		Label recipeName = new Label(informationArea, SWT.NONE);
		recipeName.setText("Rezept Name:");

		Text recipeNameText = new Text(informationArea, SWT.SINGLE | SWT.BORDER);
		recipeNameText.setText("Hier steht der Rezept Name");
		recipeNameText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		Button addIngredientButton = new Button(informationArea, SWT.PUSH);
		addIngredientButton.setText("Füge Zutatenfeld hinzu!");
		addButtonListener(addIngredientButton);
		ingredientsComposite = new Composite(informationArea, SWT.H_SCROLL);
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = true;
		rowLayout.pack = true;
		rowLayout.justify = true;
		rowLayout.type = SWT.VERTICAL;
		rowLayout.marginLeft = 5;
		rowLayout.marginTop = 5;
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 5;
		rowLayout.spacing = 0;
		ingredientsComposite.setLayout(rowLayout);
		ingredientsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));


		Label recipeDescriptionLabel = new Label(informationArea, SWT.NONE);
		recipeDescriptionLabel.setText("Rezept Anleitung:");

		Text recipeDescriptionText = new Text(informationArea, SWT.BORDER | SWT.MULTI);
		recipeDescriptionText.setText("Hier steht die Anleitung was zu machen ist");
		recipeDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));


		Button saveButton = new Button(informationArea, SWT.PUSH);
		saveButton.setText("Rezept Speichern!");

	}

	private void addButtonListener(Button addIngredientButton) {
		addIngredientButton.addMouseListener(new MouseListener() {
			private static final long serialVersionUID = -3744407685910833226L;
			@Override
			public void mouseUp(MouseEvent e) {
				addIngredientContent();
			}

			private void addIngredientContent() {
				Composite ingredientRowComposite = new Composite(ingredientsComposite, SWT.NONE);
				ingredientRowComposite.setLayout(new GridLayout(6, true));
				Label ingredientNameLabel = new Label(ingredientRowComposite, SWT.NONE);
				ingredientNameLabel.setText("Zutatenname:");
				ingredientNameLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));

				Text ingredientNameText = new Text(ingredientRowComposite, SWT.SINGLE | SWT.BORDER);
				ingredientName.add(ingredientNameText);
				ingredientNameText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));


				Label ingredientQuantityLabel = new Label(ingredientRowComposite, SWT.NONE);
				ingredientQuantityLabel.setText("Zutatenmenge:");
				ingredientQuantityLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));

				Text ingredientQuantityText = new Text(ingredientRowComposite, SWT.SINGLE | SWT.BORDER);
				ingredientQuantity.add(ingredientQuantityText);
				ingredientQuantityText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

				Label ingredientUnitLabel = new Label(ingredientRowComposite, SWT.NONE);
				ingredientUnitLabel.setText("Zutateneinheit:");
				ingredientUnitLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));

				Text ingredientUnitText = new Text(ingredientRowComposite, SWT.SINGLE | SWT.BORDER);
				ingredientUnit.add(ingredientUnitText);
				ingredientUnitText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

				ingredientsComposite.getParent().pack();
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
	public void setSpecificProperty(Object property) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;		
	}

	@Override
	public void setLogedInUser(User user) {
		this.user = user;
	}

	@Override
	public String getTabItemName() {
		return "Neues Rezept erstellen";
	}

}
