package de.cookieapp.gui.createrecipe;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.control.ControlService;
import de.cookieapp.data.model.User;
import de.cookieapp.gui.folderitem.FolderItem;

public class CreateRecipeTab implements FolderItem {

	private User user;
	private Long sessionID;
	private Composite ingredientsComposite;
	private final ArrayList<Text> ingredientName = new ArrayList<>();
	private final ArrayList<Text> ingredientQuantity = new ArrayList<>();
	private final ArrayList<Text> ingredientUnit = new ArrayList<>();
	private ControlService controlService;
	private Text recipeDescriptionText;
	private Text recipeNameText;
	private ScrolledComposite scrolledComposite;

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
		recipeName.setSize(recipeName.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		recipeNameText = new Text(informationArea, SWT.SINGLE | SWT.BORDER);
		recipeNameText.setMessage("Hier steht der Rezept Name");
		recipeNameText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
				false, 1, 1));
		recipeNameText.setSize(recipeNameText.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));

		Button addIngredientButton = new Button(informationArea, SWT.PUSH);
		addIngredientButton.setText("Füge Zutatenfeld hinzu!");
		addButtonListener(addIngredientButton);

		scrolledComposite = new ScrolledComposite(informationArea, SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.BORDER);
		scrolledComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		// scrolledComposite.setAlwaysShowScrollBars(true);

		ingredientsComposite = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(ingredientsComposite);
		scrolledComposite.setMinHeight(200);

		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		rowLayout.pack = false;
		rowLayout.justify = true;
		rowLayout.type = SWT.VERTICAL;
		rowLayout.marginLeft = 5;
		rowLayout.marginTop = 5;
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 5;
		rowLayout.spacing = 0;
		ingredientsComposite.setLayout(rowLayout);

		Label recipeDescriptionLabel = new Label(informationArea, SWT.NONE);
		recipeDescriptionLabel.setText("Rezept Anleitung:");

		recipeDescriptionText = new Text(informationArea, SWT.BORDER
				| SWT.MULTI);
		recipeDescriptionText
				.setMessage("Hier steht die Anleitung was zu machen ist");
		recipeDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));

		Button saveButton = new Button(informationArea, SWT.PUSH);
		saveButton.setText("Rezept Speichern!");
		saveButton.addMouseListener(new MouseListener() {
			private static final long serialVersionUID = -3777723625406460035L;

			@Override
			public void mouseUp(MouseEvent e) {
				if (recipeNameText.getText() != null
						&& recipeDescriptionText.getText() != null) {
					String recipeName = recipeNameText.getText();
					String recipeDescription = recipeDescriptionText.getText();
					ArrayList<String> ingredientNames = new ArrayList<String>();
					ArrayList<String> ingredientUnits = new ArrayList<String>();
					ArrayList<String> ingredientQuantities = new ArrayList<String>();
					for (int i = 0; i < ingredientName.size(); i = i + 1) {
						ingredientNames.add(ingredientName.get(i).getText());
						ingredientUnits.add(ingredientUnit.get(i).getText());
						ingredientQuantities.add(ingredientQuantity.get(i)
								.getText());
					}
					controlService.saveRecipe(recipeName, recipeDescription,
							user, ingredientNames, ingredientUnits,
							ingredientQuantities);
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		informationArea.setRedraw(true);
		informationArea.redraw();
	}

	private void addButtonListener(Button addIngredientButton) {
		addIngredientButton.addMouseListener(new MouseListener() {
			private static final long serialVersionUID = -3744407685910833226L;

			@Override
			public void mouseUp(MouseEvent e) {
				addIngredientContent();
			}

			private void addIngredientContent() {
				Composite ingredientRowComposite = new Composite(
						ingredientsComposite, SWT.NONE);
				ingredientRowComposite.setLayout(new GridLayout(6, true));
				ingredientRowComposite.setSize(ingredientRowComposite
						.computeSize(SWT.DEFAULT, SWT.DEFAULT));

				Label ingredientNameLabel = new Label(ingredientRowComposite,
						SWT.NONE);
				ingredientNameLabel.setText("Zutatenname:");
				ingredientNameLabel.setLayoutData(new GridData(SWT.NONE,
						SWT.NONE, false, false, 1, 1));

				Text ingredientNameText = new Text(ingredientRowComposite,
						SWT.SINGLE | SWT.BORDER);
				ingredientName.add(ingredientNameText);
				ingredientNameText.setLayoutData(new GridData(SWT.FILL,
						SWT.NONE, true, false, 1, 1));

				Label ingredientQuantityLabel = new Label(
						ingredientRowComposite, SWT.NONE);
				ingredientQuantityLabel.setText("Zutatenmenge:");
				ingredientQuantityLabel.setLayoutData(new GridData(SWT.NONE,
						SWT.NONE, false, false, 1, 1));

				Text ingredientQuantityText = new Text(ingredientRowComposite,
						SWT.SINGLE | SWT.BORDER);
				ingredientQuantity.add(ingredientQuantityText);
				ingredientQuantityText.setLayoutData(new GridData(SWT.FILL,
						SWT.NONE, true, false, 1, 1));

				Label ingredientUnitLabel = new Label(ingredientRowComposite,
						SWT.NONE);
				ingredientUnitLabel.setText("Zutateneinheit:");
				ingredientUnitLabel.setLayoutData(new GridData(SWT.NONE,
						SWT.NONE, false, false, 1, 1));

				Text ingredientUnitText = new Text(ingredientRowComposite,
						SWT.SINGLE | SWT.BORDER);
				ingredientUnit.add(ingredientUnitText);
				ingredientUnitText.setLayoutData(new GridData(SWT.FILL,
						SWT.NONE, true, false, 1, 1));

				// ingredientsComposite.setSize(ingredientsComposite.computeSize(SWT.DEFAULT,
				// SWT.DEFAULT));
				scrolledComposite.setMinHeight(scrolledComposite.getMinHeight() + 20);

				ingredientsComposite.pack();
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
