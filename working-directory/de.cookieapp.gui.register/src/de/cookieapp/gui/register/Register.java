package de.cookieapp.gui.register;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.cookieapp.control.ControlService;
import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.data.model.User;
import de.cookieapp.gui.folderitem.FolderItem;

public class Register implements FolderItem{
	
	private ControlService controlService;
	private Text usernameT;
	private Text passwordT;
	private Text mailT;
	private Long sessionID;
	Composite tabFolder;
	Composite completeComposite;
	private Text passwordT2; 
	

	@Override
	public Composite getContent(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
		Composite completeComposite = new Composite(tabFolder, SWT.NONE);
		FillLayout verticalLayout = new FillLayout(SWT.VERTICAL);
		completeComposite.setLayout(verticalLayout);
		this.completeComposite = completeComposite;
		createContent(completeComposite);
		
		return completeComposite;
	}
	
	private void createContent(Composite contentComposite) {
		Composite content = new Composite(contentComposite, SWT.NONE);
		content.setLayout(new GridLayout(2, true));
		
		Label headline = new Label(content, SWT.NONE);
		headline.setText("Registriere dich für CookieApp");

		Label blankLabel = new Label(content, SWT.NONE);
		blankLabel.setVisible(false);
		
		//GridData gridData = new GridData(SWT.NONE, SWT.NONE, true, false, 2, 1);
		Label usernameL = new Label(content, SWT.NONE);
		usernameL.setText("Username");
//		usernameL.setLayoutData(gridData);
		usernameT = new Text(content, SWT.BORDER);
		usernameT.setMessage("Benutzername eingeben");
//		usernameT.setLayoutData(gridData);
		Label passwordL = new Label(content, SWT.NONE);
		passwordL.setText("Passwort");
//		passwordL.setLayoutData(gridData);
		passwordT = new Text(content, SWT.BORDER | SWT.PASSWORD);
		passwordT.setMessage("Passwort eingeben");
//		passwordT.setLayoutData(gridData);
		Label passwordL2 = new Label(content, SWT.NONE);
		passwordL2.setText("Passwort wiederholen");
//		passwordL2.setLayoutData(gridData);
		passwordT2 = new Text(content, SWT.BORDER | SWT.PASSWORD);
		passwordT2.setMessage("Passwort wiederholen");
//		passwordT2.setLayoutData(gridData);
		Label mailL = new Label(content, SWT.NONE);
		mailL.setText("eMail Adresse");
//		mailL.setLayoutData(gridData);
		mailT = new Text(content, SWT.BORDER);
		mailT.setMessage("Mail Adresse eingeben");
//		mailT.setLayoutData(gridData);
		
		Button register = new Button(content, SWT.NONE);
		register.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 4028437061777994602L;
			/**
			 * Actionlistener for register button
			 */
			public void widgetSelected(SelectionEvent e) {
				if(controlService != null && passwordT.getText().equals(passwordT2.getText())) {
					String userName = usernameT.getText();
					String password = passwordT.getText();
					String eMail = mailT.getText();
					try {
						controlService.register(sessionID, userName, password, eMail);
						if (controlService.getCurrentUserName(sessionID) != null && controlService.getCurrentUserName(sessionID).equalsIgnoreCase(userName)) {
							System.out.println(controlService.getCurrentUserName(sessionID));
							Combo combo = new Combo(completeComposite, SWT.NONE);
							combo.setText("Erfolgreich Registriert");
						} else {
							System.err.println("Failed to Register");
						}
					} catch (CookieAppException exception) {
						System.err.println("Registation Failed!");
					}
				}
			}
		});
		register.setText("Registrieren");
	}
	
	@Override
	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
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
	
	@Override
	public void setSpecificProperty(Object property) {
	}


	@Override
	public void setLogedInUser(User user) {
	}

}
