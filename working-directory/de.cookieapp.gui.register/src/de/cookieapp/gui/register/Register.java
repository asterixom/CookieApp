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

import de.cookieapp.control.ControlService;
import de.cookieapp.control.ControlServiceImpl;
import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.gui.folderitem.FolderItem;

public class Register implements FolderItem{
	
	private ControlService controlService;
	private Text usernameT;
	private Text passwordT;
	private Text mailT;
	

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
		usernameT = new Text(content, SWT.BORDER);
		Label passwordL = new Label(content, SWT.NONE);
		passwordL.setText("Passwort");
		passwordT = new Text(content, SWT.BORDER | SWT.PASSWORD);
		Label mailL = new Label(content, SWT.NONE);
		mailL.setText("eMail Adresse");
		mailT = new Text(content, SWT.BORDER);
		
		Button register = new Button(content, SWT.NONE);
		register.addSelectionListener(new SelectionAdapter() {
			/**
			 * Actionlistener for register button
			 */
			public void widgetSelected(SelectionEvent e) {
				if(controlService != null) {
					Long sessionID = null;
					try {
						sessionID = controlService.createSession();
					} catch (CookieAppException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String userName = usernameT.getText();
					String password = passwordT.getText();
					String eMail = mailT.getText();
					try {
						controlService.register(sessionID, userName, password, eMail);
					} catch (CookieAppException exception) {
						
					}
				}
			}
		});
		register.setText("Registrieren");
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

	@Override
	public String getTabItemName() {
		return "Registrieren";
	}

}
