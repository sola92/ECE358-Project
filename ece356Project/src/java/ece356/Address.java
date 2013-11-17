package ece356;


/**
 *
 * @author Sola
 */
public class Address extends Model {
	final int    addressID;
	final String streetName;
	final String postalCode;
	final String city;
	final String province;

 	public Address(	int addressID, String streetName, 
 					String postalCode, String city, String province) {
 		this.city 	    = city;
 		this.province   = province;
		this.addressID  = addressID;
		this.streetName = streetName;
		this.postalCode = postalCode;
 	}

	public int getAddressID() {
		return addressID;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCity() {
		return city;
	}

	public String getProvince() {
		return province;
	}	
}
