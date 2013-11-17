package ece356;


/**
 *
 * @author Sola
 */
public class Specialization extends Model {
   final int specID;
   final String name; 

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
