package de.cookieapp.dataimpl;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// für jede Klasse wird eine neue Tabelle erstellt
public class Users {
	
	@OneToMany
	private UserImpl users;

	public UserImpl getUsers() {
		return users;
	}

	public void setUsers(UserImpl users) {
		this.users = users;
	}

	public Users(UserImpl users) {
		this.users = users;
	}
	
	public void addUser(UserImpl user){
		
	}

}
