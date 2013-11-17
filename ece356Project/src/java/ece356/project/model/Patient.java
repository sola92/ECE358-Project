/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356.project.model;

/**
 *
 * @author Sola
 */
public class Patient extends User {
	String email;
	
	public Patient(	int userID, String firstName, String lastName, 
					String alias, String password, String email ) {
	    super(userID, firstName, lastName, alias, password);
	    this.email = email;
	}  

    public int getPatientID() {
    	return getUserID();
    }

	public String getEmail() {
		return email;
	}   
}
