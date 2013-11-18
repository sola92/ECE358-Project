package ece356.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


/**
 *
 * @author vincent
 */
public class ProjectDBAO {
    //public static final String host = "localhost";
    public static final String host = "eceweb";
    public static final String url = "jdbc:mysql://" + host + ":3306/";
    public static final String nid = "oaogunsa";
    public static final String user = "user_" + nid;
    public static final String pwd = "user_" + nid;
    
    private static Patient rowToPatient(ResultSet result) throws SQLException {
        int userID       = result.getInt("userID");
        String email     = result.getString("email");
        String alias     = result.getString("alias");
        String lastName  = result.getString("lastName");
        String password  = result.getString("password");
        String firstName = result.getString("firstName");   
        return new Patient(userID, firstName, lastName, alias, password, email);    
    }

    private static Administrator rowToAdmin(ResultSet result) throws SQLException {
        int userID       = result.getInt("userID");
        String alias     = result.getString("alias");
        String lastName  = result.getString("lastName");
        String password  = result.getString("password");
        String firstName = result.getString("firstName");   
        return new Administrator(userID, firstName, lastName, alias, password);    
    }    

    private static Doctor rowToDoctor(ResultSet result) 
        throws ClassNotFoundException, SQLException {
        int userID          = result.getInt("userID");
        String alias        = result.getString("alias");
        String lastName     = result.getString("lastName");
        String password     = result.getString("password");
        String firstName    = result.getString("firstName"); 
        Date dob            = result.getDate("dob");
        int gender          = result.getInt("gender");
        int licenseYear      = result.getInt("licenseYear");
        Address homeAddress = getAddress(result.getInt("homeAddressID"));
        return new Doctor(userID, firstName,lastName, alias, 
                   password, dob, gender, licenseYear,
                    homeAddress);    
    }    
    
    private static Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, pwd);
        Statement stmt = null;
        try {
            con.createStatement();
            stmt = con.createStatement();
            stmt.execute("USE ece356db_" + nid);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }
    
    public static Boolean aliasIsFree(String alias)
            throws ClassNotFoundException, SQLException {
        Boolean isFree = false;
        Connection connection       = null;
        PreparedStatement statement = null;        
        String query = "SELECT COUNT(*) FROM User WHERE alias = ? ";
        try {
            connection   = getConnection();
            statement    = connection.prepareStatement(query);
            statement.setString(1, alias);
            ResultSet resultSet = statement.executeQuery(); resultSet.next();
            isFree = resultSet.getInt("COUNT(*)") == 0;
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return isFree;
    }

    public static Boolean verifyUserExists(String alias, String password) 
            throws ClassNotFoundException, SQLException {
        Boolean exists = false;
        Connection connection   = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT COUNT(*) FROM User WHERE alias = ? AND password = ?");
            statement.setString(1, alias);
            statement.setString(2, MD5(password));
            ResultSet result = statement.executeQuery();
            result.next();
            exists = result.getInt("COUNT(*)") == 1;
        } finally {
            if (statement != null)   statement.close();
            if (connection != null)  connection.close();
        }
        return exists;
    } 

    public static Patient getPatientByAlias(String _alias) throws ClassNotFoundException, SQLException {
        Patient patient             = null; 
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement("SELECT * from Patient JOIN User ON Patient.patientID = User.userID WHERE alias = ?");
            statement.setString(1, _alias);
            ResultSet result = statement.executeQuery();
            result.next();
            patient = rowToPatient(result);
            statement.close();

        } finally {
            if (statement != null)   statement.close();
            if (connection != null)  connection.close();
        }
        return patient;
    } 

   public static Patient getPatientByID(String patientID) throws ClassNotFoundException, SQLException {
        Patient patient             = null; 
        Connection connection       = null;
        PreparedStatement statement = null;
        final String QUERY = "SELECT * from Patient JOIN User ON Patient.patientID = User.userID WHERE patientID = ?";
        try {
             connection = getConnection();
             statement  = connection.prepareStatement(QUERY);
             statement.setString(1, patientID);
             ResultSet result = statement.executeQuery();
             result.next();
             patient = rowToPatient(result);
             statement.close();
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return patient;
    } 

    public static Administrator getAdminByAlias(String _alias) throws ClassNotFoundException, SQLException {
        Administrator administrator = null; 
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement("SELECT * from Administrator JOIN User ON Administrator.adminID = User.userID WHERE alias = ?");
            statement.setString(1, _alias);
            ResultSet result = statement.executeQuery();
            result.next();
            administrator = rowToAdmin(result);
            statement.close();

        } finally {
            if (statement != null)  statement.close();
            if (connection != null) connection.close();
        }
        return administrator;
    }     
    
    public static List<Patient> searchPatientsByAlias(int currentUserID, String alias)
            throws ClassNotFoundException, SQLException {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        if(alias == null || alias.trim() == "") return patients;
        Connection connection       = null;
        PreparedStatement statement = null;        
        final String query = "SELECT * " +
        "FROM (" +
            "SELECT * FROM Patient INNER JOIN User  " +
            "ON Patient.patientID = User.userID " +
            "WHERE alias LIKE ? AND patientID <> ? " +
        ") AS p " +
        "WHERE (SELECT COUNT(*) FROM Friendship WHERE followerID = ? AND followeeID = p.patientID) = 0";
        try {
            connection   = getConnection();
            statement    = connection.prepareStatement(query);
            statement.setString(1, "%" + alias + "%");
            statement.setInt(2, currentUserID);
            statement.setInt(3, currentUserID);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) patients.add(rowToPatient(resultSet));
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return patients;
    }    

    public static Boolean userIsPatient(String _alias) throws ClassNotFoundException, SQLException {
        Boolean isPatient           = false; 
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT COUNT(*) FROM Patient JOIN User ON Patient.patientID = User.userID WHERE alias = ?");
            statement.setString(1, _alias);
            ResultSet result = statement.executeQuery();
            result.next();
            isPatient = result.getInt("COUNT(*)") == 1;
            statement.close();
        } finally {
            if (statement != null)   statement.close();
            if (connection != null)  connection.close();
        }
        return isPatient;
    } 

    public static Boolean userIsDoctor(String _alias) throws ClassNotFoundException, SQLException {
        Boolean isDoctor           = false; 
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT COUNT(*) FROM Doctor JOIN User ON Doctor.doctorID = User.userID WHERE alias = ?");
            statement.setString(1, _alias);
            ResultSet result = statement.executeQuery();
            result.next();
            isDoctor = result.getInt("COUNT(*)") == 1;
            statement.close();
        } finally {
            if (statement != null)   statement.close();
            if (connection != null)  connection.close();
        }
        return isDoctor;
    }     
    
    private static int makeUser(String firstName, String lastName, String alias, String password)
            throws ClassNotFoundException, SQLException {
        Connection connection       = null;
        PreparedStatement statement = null;  
        int userID = 0;                
        connection   = getConnection();
        try {
            String query = "INSERT INTO User(firstName, lastName, alias, password) VALUES(?,?,?,?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, alias);
            statement.setString(4, MD5(password));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys(); rs.next();
            userID = rs.getInt(1);            
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return userID;
    }
    
    public static int makePatient(String firstName, String lastName, String alias, String password, String email)
            throws ClassNotFoundException, SQLException {
        Connection connection       = null;
        PreparedStatement statement = null;
        int userID = -1;
        try {
            connection = getConnection();
            userID     = makeUser(firstName, lastName, alias, password);
            statement  = connection.prepareStatement("INSERT INTO Patient(patientID, email) VALUES(?, ?)");
            statement.setInt(1, userID);
            statement.setString(2, email);
            statement.executeUpdate();
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
        return userID;
    }

    public static int makeAdministrator(String firstName, String lastName, String alias, String password)
            throws ClassNotFoundException, SQLException {
        Connection connection       = null;
        PreparedStatement statement = null;
        int userID = -1;
        try {
            connection = getConnection();
            userID     = makeUser(firstName, lastName, alias, password);
            statement  = connection.prepareStatement("INSERT INTO Administrator(adminID) VALUES(?)");
            statement.setInt(1, userID);
            statement.executeUpdate();
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
        return userID;
    }   


    public static List<Specialization> getSpecializations()
            throws ClassNotFoundException, SQLException {
        ArrayList<Specialization> specializations = new ArrayList<Specialization>();
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement("SELECT * FROM Specialization;");
            ResultSet resultSet = statement.executeQuery(); 
            while(resultSet.next()) {
                int specID  = resultSet.getInt("specID");
                String name = resultSet.getString("name");
                specializations.add(new Specialization(specID, name)); 
            }
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
        return specializations;
    }      

    public static int makeReview (
                int rating, Date reviewDate, String note, int doctorID,
                int patientID
            ) throws ClassNotFoundException, SQLException {
        int reviewID = -1;
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement(
                            "INSERT INTO Review(doctorID, patientID, rating, note, reviewDate) VALUES (?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, doctorID);
            statement.setInt(2, patientID);
            statement.setInt(3, rating);
            statement.setString(4, note);
            statement.setDate(5, reviewDate);
            ResultSet resultSet = statement.getGeneratedKeys(); resultSet.next();
            reviewID = resultSet.getInt(1);  
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
        return reviewID;
    }   

    public static void makeFriendship(int followerID, int followeeID) 
            throws ClassNotFoundException, SQLException {
        if(followerID == followeeID) throw new IllegalArgumentException("user cannot follow themselves");
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement(
                            "INSERT INTO Friendship(followerID, followeeID) VALUES (?,?)");
            statement.setInt(1, followerID);
            statement.setInt(2, followeeID);
            statement.executeUpdate();
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
    }           
    
    public static int makeDoctor(
                String firstName, String lastName, String alias, String password, 
                int gender, Date dob, int homeAddressID, int license, int[] specializations )
            throws ClassNotFoundException, SQLException {
        int userID                  = -1;
        Connection connection       = null;
        PreparedStatement statement = null;
        final String QUERY = "INSERT INTO Doctor(doctorID, gender, dob, homeAddressID, licenseYear) VALUES(?,?,?,?,?)";       
        try {
            userID      = makeUser(firstName, lastName, alias, password);
            connection  = getConnection();
            statement   = connection.prepareStatement(QUERY);
            statement.setInt (1, userID);
            statement.setInt (2, gender);
            statement.setDate(3, dob);
            statement.setInt (4, homeAddressID);
            statement.setInt (5, license);
            statement.executeUpdate();
            addSpecialization(userID, specializations);
        } finally {
            if (statement  != null) statement.close();  
            if (connection != null) connection.close();
        }
        return userID;   
    }

    public static Doctor getDoctorByID(String doctorID) throws ClassNotFoundException, SQLException {
        Doctor doctor               = null; 
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement("SELECT * from Doctor JOIN User ON Doctor.doctorID = User.userID WHERE doctorID = ?");
            statement.setString(1, doctorID);
            ResultSet result = statement.executeQuery();
            result.next();
            doctor = rowToDoctor(result);
            statement.close();

        } finally {
            if (statement != null)   statement.close();
            if (connection != null)  connection.close();
        }
        return doctor;
    } 

    public static void addSpecialization(int doctorID, int[] specializations) 
        throws ClassNotFoundException, SQLException {
        Connection connection       = null;
        PreparedStatement statement = null;
        final String QUERY  = "INSERT INTO DoctorSpecialization(doctorID, specID) VALUES(?,?)";
        try {
            connection  = getConnection();
            statement   = connection.prepareStatement(QUERY);
            for(int specID: specializations) {
                statement.setInt (1, doctorID);
                statement.setInt (2, specID);
                statement.addBatch();                                   
            }
            statement.executeBatch();
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public static void addWorkAddresses(int doctorID, int[] addresses) 
        throws ClassNotFoundException, SQLException {
        Connection connection       = null;
        PreparedStatement statement = null;
        final String QUERY  = "INSERT INTO WorkAddresses(doctorID, addressID) VALUES(?,?)";
        try {
            connection  = getConnection();
            statement   = connection.prepareStatement(QUERY);
            for(int addressID: addresses) {
                statement.setInt(1, doctorID);
                statement.setInt(2, addressID);
                statement.addBatch();                                   
            }
            statement.executeBatch();
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public static Address getAddress(int aid) 
            throws ClassNotFoundException, SQLException {
        Address address             = null;
        Patient patient             = null; 
        Connection connection       = null;
        PreparedStatement statement = null;
        final String QUERY = "SELECT * FROM Address WHERE addressID = ?";
        try {
            connection = getConnection();
            statement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, aid);
            
            ResultSet rs = statement.executeQuery(); rs.next();
            String city         = rs.getString("city");
            int    addressID    = rs.getInt("addressID");
            String province     = rs.getString("province");        
            String streetAddress   = rs.getString("streetAddress");
            String postalCode   = rs.getString("postalCode");                
            address = new Address(addressID, streetAddress, postalCode, city, province);
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return address;
    }

    public static Doctor getDoctorByAlias(String _alias) throws ClassNotFoundException, SQLException {
        Doctor doctor               = null; 
        Connection connection       = null;
        PreparedStatement statement = null;
        final String QUERY = "SELECT * from Doctor JOIN User ON Doctor.doctorID = User.userID WHERE alias = ?";
        try {
            connection = getConnection();
            statement  = connection.prepareStatement(QUERY);
            statement.setString(1, _alias);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Date dob         = result.getDate  ("dob");
                int gender       = result.getInt   ("gender");            
                int userID       = result.getInt   ("userID");
                String alias     = result.getString("alias");
                String lastName  = result.getString("lastName");
                String password  = result.getString("password");
                String firstName = result.getString("firstName");
                int licenseYear  = result.getInt   ("licenseYear");
                Address homeAddress = getAddress(result.getInt("homeAddressID"));           
                doctor = new Doctor(userID, firstName, lastName, alias, 
                                    password, dob, gender, licenseYear, homeAddress);
            }
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return doctor;
    }

    public static int makeAddress(String streetAddress, String postalCode, String city, String province) 
                        throws ClassNotFoundException, SQLException {
        int addressID = -1;
        Connection connection       = null;
        PreparedStatement statement = null;    
        final String QUERY = "INSERT INTO Address(streetAddress, postalCode, city, province) VALUES (?,?,?,?)";
        try {
            connection = getConnection();
            statement  = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, streetAddress);
            statement.setString(2, postalCode);
            statement.setString(3, city);
            statement.setString(4, province);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys(); resultSet.next();
            addressID = resultSet.getInt(1);  
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
        return addressID;
    }

    public static List<Doctor> searchDoctors(
            int currentUserID,
            String firstName,     String lastName,
            String streetAddress, String postalCode, 
            String city, String province, 
            Integer licenseYearStart, Integer licenseYearEnd,
            Double averageRatingStart, Double averageRatingEnd,
            Boolean recommendedByFriend
        ) throws ClassNotFoundException, SQLException {
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        Connection connection       = null;
        PreparedStatement statement = null;    
        String QUERY = 
            "SELECT DISTINCT u.*, d.* " +
                    "FROM Doctor AS d " +
                    "INNER JOIN User AS u ON d.doctorID = u.userID " +
                    "LEFT OUTER JOIN ( " +
                        "SELECT  " +
                            "doctorID, " +
                            "addressID   	AS 	workAddressID, " +
                            "streetAddress 	AS  workStreetAddress, " +
                            "postalCode  	AS  workPostalCode, " +
                            "city 	    	AS  workCity, " +
                            "province    	AS  workProvince " +
                        "FROM Doctor  " +
                        "NATURAL JOIN WorkAddresses " +
                        "NATURAL JOIN Address " +
                    ") wa ON wa.doctorID = d.doctorID  " +
                    "LEFT OUTER JOIN ( " +
                        "SELECT IFNULL(AVG(r.rating), 0) as averageRating, d2.doctorID " +
                        "FROM Review AS r " +
                        "RIGHT OUTER JOIN Doctor d2 ON d2.doctorID = r.doctorID " +
                        "GROUP BY doctorID " +
                    ") ar ON ar.doctorID = d.doctorID  " +
                    "LEFT OUTER JOIN DoctorSpecialization s ON s.doctorID = d.doctorID  " +
            " WHERE 1=1 ";

        String where = "";

        if(lastName != null && !lastName.equals("")) {
            where += " AND lastName LIKE '%" + lastName + "%'";
        }
        
        if(firstName != null && !firstName.equals("")) {
            where += " AND firstName LIKE '%" + firstName + "%'";
        }      
        
        if(streetAddress != null && !streetAddress.equals("")) {
            where += " AND wa.streetAddress LIKE '%" + streetAddress + "%'";
        }
        
        if(postalCode != null && !postalCode.equals("")) {
            where += " AND wa.postalCode LIKE '%" + postalCode + "%'";
        }        

        if(city != null && !city.equals("")) {
            where += " AND wa.city LIKE '%" + city + "'%";
        }     

        if(province != null && !province.equals("")) {
            where += " AND wa.province LIKE '%" + province + "'%";
        } 

        if(licenseYearStart != null) {
            where += " AND licenseYear >= " + licenseYearStart;
        } 

        if(licenseYearEnd != null) {
            where += " AND licenseYear =< " + licenseYearEnd;
        }        
        
        if(averageRatingStart != null) {
            where += " AND averageRating >= " + averageRatingStart;
        }  

        if(averageRatingEnd != null) {
            where += " AND averageRating =< " + averageRatingEnd;
        }     

        if(recommendedByFriend != null) {
            where += " AND (SELECT COUNT(*) FROM Review WHERE doctorID = d.doctorID AND patientID " + 
                     " IN (SELECT followeeID FROM Friendship WHERE followerID = " + averageRatingEnd + ") " + 
                     " ) " + (recommendedByFriend ? "> 0": " = 0");
        }            
        
        QUERY += where;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement(QUERY);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) doctors.add(rowToDoctor(resultSet));
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
        return doctors;
    }    

    private static void testConnection()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            con = getConnection();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    private static String MD5(String md5) {
       try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
              sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {}
        return null;
    }  
}

