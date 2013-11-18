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

	public User(int userID, String firstName, String lastName, String alias) {
		this.alias     = alias;
		this.userID    = userID;
		this.lastName  = lastName;	
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
}
