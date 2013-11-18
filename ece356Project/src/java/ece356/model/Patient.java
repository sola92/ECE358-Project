package ece356.model;
/**
 *
 * @author Sola
 */
public class Patient extends User {
	protected final String email;
	
	public Patient(	int userID, String firstName, String lastName, 
					String alias, String email ) {
		super(userID, firstName, lastName, alias);
		this.email = email;
	}  

	public int getPatientID() {
		return getUserID();
	}

	public String getEmail() {
		return email;
	}   
}
