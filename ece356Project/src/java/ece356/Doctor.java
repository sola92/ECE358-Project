package ece356;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
/**
 *
 * @author Sola
 */
public class Doctor extends User {
	protected final Date dob;
	protected final int  gender;
	protected final Date licenseYear;
	protected final Address homeAddress;

	public Doctor(	int userID, String firstName, String lastName, String alias, 
					String password, Date dob, int gender, Date licenseYear,
					Address homeAddress ) {
		super(userID, firstName, lastName, alias, password);
		this.dob         = dob;
		this.gender      = gender;
		this.licenseYear = licenseYear;
		this.homeAddress = homeAddress;		
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

	public Date getLicenseYear() {
		return licenseYear;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}      

}
