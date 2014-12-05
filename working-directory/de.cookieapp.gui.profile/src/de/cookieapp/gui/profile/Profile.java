package de.cookieapp.gui.profile;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.cookieapp.gui.folderitem.FolderItem;

public class Profile implements FolderItem{

	@Override
	public Composite getContent(Composite tabFolder) {
		Composite completeComposite = new Composite(tabFolder, SWT.NONE);
		completeComposite.setLayout(new GridLayout(1,false));
		
		createHeader(completeComposite);
		createContent(completeComposite);

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
		
		Composite contentTL = new Composite(content, SWT.NONE);
		contentTL.setLayout(new GridLayout(1, false));
		//TODO Platzhalter f�r Bild einf�gen
		
		Composite contentTR = new Composite(content, SWT.NONE);
		contentTR.setLayout(new GridLayout(1, false));
		Label username = new Label(contentTR, SWT.NONE);
		username.setText("username"); //TODO Methode getUsername() einf�gen
		username.setFont(new Font( contentTR.getDisplay(), "Verdana", 24, SWT.BOLD )); //TODO ggf. CSS-Tag Headlie1 einf�gen
		
		Composite contentBL = new Composite(content, SWT.NONE);
		contentBL.setLayout(new GridLayout(2, false));
		//TODO Nutzerdaten Labels und Buttons zum �ndern einf�gen
		
		Composite contentBR = new Composite(content, SWT.NONE);
		contentBR.setLayout(new GridLayout(1, false));
		Label myRecipes = new Label(contentBR, SWT.NONE);
		myRecipes.setText("Meine Rezepte");
		myRecipes.setFont(new Font( contentBR.getDisplay(), "Verdana", 18, SWT.BOLD ));
		// MyRecipes einbinden
		
		
	}
	
	@Override
	public void setSpecificProperty(Object property) {
		// TODO Auto-generated method stub
		
	}
	


	@Override
	public String getTabItemName() {
		return "Mein Profil";
	}

}