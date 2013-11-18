package ece356.model;

import java.util.Date;


/**
 *
 * @author Sola
 */
public class Review extends Model {
	
	protected final double 	rating; 
	protected final int 	reviewID;
	protected final Date 	reviewDate; 	
	protected final String 	note;       
	protected final Doctor 	doctor;   
	protected final Patient patient;  
	
	public Review(	int rating,  int reviewID, Date reviewDate, 
			String note, Doctor doctor, Patient patient ) {
		this.note 	    = note;       
		this.rating     = rating; 
		this.doctor     = doctor;   
		this.patient    = patient;
		this.reviewID   = reviewID;
		this.reviewDate = reviewDate; 	
	}  

	public double getRating() {
		return rating;
	} 

	public Boolean isRecommendation() {
		return rating > 3;
	} 	

	public String getNote() {
		return note;
	}       

	public int getReviewID() {
		return reviewID;
	}   

	public Doctor getDoctor() {
		return doctor;
	}   

	public Date getReviewDate() {
		return reviewDate;
	} 	

	public Patient getPatient() {
		return patient;
	} 	

	
}