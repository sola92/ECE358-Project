package ece356.model;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
import java.util.List;
/**
 *
 * @author Sola
 */
public class Doctor extends User {
	protected final Date dob;
	protected final int  gender;
	protected final int licenseYear;
	protected final Address homeAddress;
	protected List<Address> workAddresses;

	public Doctor(	int userID, String firstName, String lastName, String alias, 
					String password, Date dob, int gender, int licenseYear,
					Address homeAddress) {
		super(userID, firstName, lastName, alias, password);
		this.dob           = dob;
		this.gender        = gender;
		this.licenseYear   = licenseYear;
		this.homeAddress   = homeAddress;		
		this.workAddresses = workAddresses;
	}   

	public Date getDOB() {
		return dob;
	}

	public int getGender() {
		return gender;
	}

	public int getDoctorID() {
		return getUserID();
	}

	public int getLicenseYear() {
		return licenseYear;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}      

	public List<Address> getWorkAddresses() {
		return workAddresses;
	}   
        
	public void setWorkAddresses(List<Address> addresses) {
		workAddresses = addresses;
	}        
}
