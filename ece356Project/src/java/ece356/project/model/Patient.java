/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356.model;

/**
 *
 * @author Sola
 */
public class Patient extends User {
	int patientID;
	String email;
	
	public Patient(	int userID, String firstName, String lastName, 
					String alias, String password, int patientID, String email ) {
	    super(userID, firstName, lastName, alias, password);
	    this.email 	   = email;
	    this.patientID = patientID;
	} 

    public int getPatientID() {
    	return patientID;
    }

	public String getEmail() {
		return email;
	}   
}
