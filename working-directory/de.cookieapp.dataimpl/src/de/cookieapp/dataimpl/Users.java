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
	private User users;

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Users(User users) {
		this.users = users;
	}
	
	public void addUser(User user){
		
	}

}
