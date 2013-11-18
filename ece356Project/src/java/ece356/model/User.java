package ece356.model;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Sola
 */
public abstract class User extends Model {
	protected final int    userID;
	protected final String firstName;
	protected final String lastName;
	protected final String alias;
	protected final String password;

	public User(int userID, String firstName, String lastName, String alias, String password) {
		this.alias     = alias;
		this.userID    = userID;
		this.lastName  = lastName;
		this.password  = password;		
		this.firstName = firstName;
	}

	public int getUserID() {
		return userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

        public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
                
	public String getAlias() {
		return alias;
	}

	public String getPassword() {
		return password;
	}
}
