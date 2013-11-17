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
	int  doctorID;
	Date licenseYear;
	Address homeAddress;

	public Doctor(	int userID, String firstName, String lastName, String alias, 
					String password, Date dob, int gender, int doctorID, Date licenseYear,
					Address homeAddressID ) {
		super(userID, firstName, lastName, alias, password);
		this.dob           = dob;
		this.gender        = gender;
		this.doctorID      = doctorID;
		this.licenseYear   = licenseYear;
		this.homeAddress = homeAddress;		
	}   

	public Date getDOB() {
		return dob;
	}

	public int getGender() {
		return gender;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public Date getLicenseYear() {
		return licenseYear;
	}

	public Address getHomeAddressID() {
		return homeAddress;
	}      

}
