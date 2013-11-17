/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356.model;

/**
 *
 * @author Sola
 */
public abstract class User extends Model {
	int    userID;
	String firstName;
	String lastName;
	String alias;
	String password;

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

	public String getAlias() {
		return alias;
	}

	public String getPassword() {
		return password;
	}
}
