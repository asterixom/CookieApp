package de.cookieapp.gui.register;

import java.awt.TextField;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
		completeComposite.setLayout(new GridLayout(1,false));
		
		createHeader(completeComposite);
		createContent(completeComposite);

		return completeComposite;
	}


	private void createHeader(Composite completeComposite) {
		Composite header = new Composite(completeComposite, SWT.NONE);
		header.setLayout(new GridLayout(1, false));
		Label headline = new Label(header, SWT.NONE);
		headline.setText("Registriere dich f�r CookieApp");		
	}
	
	private void createContent(Composite completeComposite) {
		Composite content = new Composite(completeComposite, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		Label usernameL = new Label(content, SWT.NONE);
		Text usernameT = new Text(content, SWT.NONE);
		Label passwordL = new Label(content, SWT.NONE);
		Text passwordT = new Text(content, SWT.NONE | SWT.PASSWORD);
		Label mailL = new Label(content, SWT.NONE);
		Text mailT = new Text(content, SWT.NONE);
		
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
