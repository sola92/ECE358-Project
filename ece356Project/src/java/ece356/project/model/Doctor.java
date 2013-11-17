/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356.project.model;

import java.util.Date;
/**
 *
 * @author Sola
 */
public class Doctor extends User {
	Date dob;
	int  gender;
	Date licenseYear;
	Address homeAddress;

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
