/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356.model;

/**
 *
 * @author Sola
 */
public class Specialization extends Model {
   int specID;
   String name; 

	public Specialization(int specID, String name) {
		this.name   = name;
		this.specID = specID;
	}    

	public int getSpecID() {
			return specID;
	}

	public String getName() {
			return name;
	}
}
