package de.cookieapp.gui.register;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.gui.folderitem.FolderItem;

public class Register implements FolderItem{

	@Override
	public Composite getContent(Composite tabFolder) {
		
		Composite completeComposite = new Composite(tabFolder, SWT.NONE);
		completeComposite.setLayout(new FillLayout(SWT.FILL));
		
//		Composite headerComposite = new Composite(completeComposite, SWT.NONE);		
		createHeader(completeComposite);
		
//		Composite contentComposite = new Composite(completeComposite, SWT.NONE);
		createContent(completeComposite);
		
		return completeComposite;
	}


	private void createHeader(Composite headerComposite) {
		Composite header = new Composite(headerComposite, SWT.NONE);
		header.setLayout(new GridLayout(1, false));
		Label headline = new Label(header, SWT.NONE);
		headline.setText("Registriere dich fuer CookieApp");		
	}
	
	private void createContent(Composite contentComposite) {
		Composite content = new Composite(contentComposite, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		Label usernameL = new Label(content, SWT.NONE);
		usernameL.setText("Username");
		Text usernameT = new Text(content, SWT.BORDER);
		Label passwordL = new Label(content, SWT.NONE);
		passwordL.setText("Passwort");
		Text passwordT = new Text(content, SWT.BORDER | SWT.PASSWORD);
		Label mailL = new Label(content, SWT.NONE);
		mailL.setText("eMail Adresse");
		Text mailT = new Text(content, SWT.BORDER);
		
		Button register = new Button(content, SWT.NONE);
		register.addSelectionListener(new SelectionAdapter() {
			/**
			 * Actionlistener for register button
			 */
			public void widgetSelected(SelectionEvent e) {
				//TODO register implementieren
			}
		});
		register.setText("Registrieren");
	}

	@Override
	public String getTabItemName() {
		return "Registrieren";
	}



}
