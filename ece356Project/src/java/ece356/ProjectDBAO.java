package ece356;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
    public static void testConnection()
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

    private static Review rowToReview(ResultSet result) throws SQLException {
        int reviewID     = result.getInt("reviewID");
        int doctorID     = result.getInt("doctorID");
        int patientID    = result.getInt("patientID");
        int rating       = result.getInt("rating");
        String note      = result.getString("note");
        int reviewDate   = result.getInt("reviewDate");   
        return new Review(rating, reviewID, java.sql.Date(reviewDate), note, doctorID, patientID);    
    }    
    
    public static Connection getConnection()
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
    
    public static List<Patient> searchPatientsByAlias(String alias)
            throws ClassNotFoundException, SQLException {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        Connection connection       = null;
        PreparedStatement statement = null;        
        String query = "SELECT * FROM Patient JOIN User WHERE alias LIKE ? ";
        try {
            connection   = getConnection();
            statement    = connection.prepareStatement(query);
            statement.setString(1, "%" + alias + "%");
            ResultSet resultSet = statement.executeQuery(); resultSet.next();
            while(resultSet.next()) patients.add(rowToPatient(resultSet));            
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return patients;
    }    

    //might have to return a list of aliases instead of the full patient
    public static List<Patient> listPatientByAlias()
            throws ClassNotFoundException, SQLException {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        Connection connection       = null;
        PreparedStatement statement = null;        
        String query = "SELECT alias FROM Patient;";
        try {
            connection   = getConnection();
            statement    = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(); resultSet.next();
            while(resultSet.next()) patients.add(rowToPatient(resultSet));            
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return patients;
    }   

    // for admin use
    public static List<Patient> listAllPatients()
            throws ClassNotFoundException, SQLException {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        Connection connection       = null;
        PreparedStatement statement = null;        
        String query = "SELECT * FROM Patient;";
        try {
            connection   = getConnection();
            statement    = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(); resultSet.next();
            while(resultSet.next()) patients.add(rowToPatient(resultSet));            
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return patients;
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

    public static List<Review> getReviews(int doctorID)
            throws ClassNotFoundException, SQLException {
        ArrayList<Review> reviews = new ArrayList<Review>();
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement("SELECT * FROM Review WHERE doctorID = ? ORDER BY reviewDate DESC;");
            statement.setInt(1, doctorID);
            ResultSet resultSet = statement.executeQuery(); 
            while(resultSet.next()) {
                reviews.add(rowToReview(resultSet)); 
            }
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
        return reviews;
    }    

    public static List<Review> getReviewsByDateAndKeyword(int date, String keyword)
            throws ClassNotFoundException, SQLException {
        ArrayList<Review> reviews = new ArrayList<Review>();
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement("SELECT * FROM Review WHERE reviewDate >= DATE_SUB(CURDATE(), ?) AND note = ?;");
            statement.setDate(1, new java.sql.Date(date));
            statement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                reviews.add(rowToReview(resultSet)); 
            }
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
        return reviews;
    }   

    public static void deleteReviewsByDateAndKeyword(int date, String keyword)
            throws ClassNotFoundException, SQLException {
        ArrayList<Review> reviews = new ArrayList<Review>();
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement("DELETE FROM Review WHERE reviewDate >= DATE_SUB(CURDATE(), ?) AND note = ?;");
            statement.setDate(1, new java.sql.Date(date));
            statement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
    }   

    public static void makeFriendship(int followerID, int followeeID) 
            throws ClassNotFoundException, SQLException {
        if(followerID == followeeID) throw new IllegalArgumentException("user cannot follow themselves");
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement(
                            "INSERT INTO Friendship(followerID, followerID) VALUES (?,?)");
            statement.setInt(1, followerID);
            statement.setInt(2, followerID);
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
    }           
    
    public static int makeDoctor(String firstName, 
                                  String lastName, 
                                  String alias, 
                                  String password, 
                                  boolean gender, 
                                  int dob, 
                                  int homeAddressID, 
                                  int license)
            throws ClassNotFoundException, SQLException {
        
            Connection con = null;
            PreparedStatement pstmt = null;
            ArrayList ret = null;
            try {
                
                con = getConnection();
                int userID = makeUser(firstName, lastName, alias, password);
                
                pstmt = con.prepareStatement("INSERT INTO Doctor VALUES(?,?,?,?,?)");
                pstmt.setInt(1, userID);
                pstmt.setBoolean(2, gender);
                pstmt.setInt(3, dob);
                pstmt.setInt(4, homeAddressID);
                pstmt.setInt(5, license);
                pstmt.executeUpdate();

                return userID;
            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
                throw new SQLException();
            }   
    }

    public static int makeAddress(String streetName, 
                                  String postalCode, 
                                  String city, 
                                  String province)
            throws ClassNotFoundException, SQLException {
        
            Connection connection       = null;
        PreparedStatement statement = null;  
        int addressID = 0;                
        connection   = getConnection();
        try {
            String query = "INSERT INTO Address(streetName, postalCode, city, province) VALUES(?,?,?,?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, streetName);
            statement.setString(2, postalCode);
            statement.setString(3, city);
            statement.setString(4, province);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys(); 
            rs.next();
            addressID = rs.getInt(1);            
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return addressID;
    }

    public static void linkDoctorWorkAddress(int doctorID, int addressID)
            throws ClassNotFoundException, SQLException {
        
        Connection connection       = null;
        PreparedStatement statement = null;                  
        connection   = getConnection();
        try {
            String query = "INSERT INTO WorkAddresses VALUES(?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, doctorID);
            statement.setInt(2, addressID);
            statement.executeUpdate();            
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public static int makeSpecialization(String specName)
            throws ClassNotFoundException, SQLException {
        
        Connection connection       = null;
        PreparedStatement statement = null;  
        int specID = 0;                
        connection   = getConnection();
        try {
            String query = "INSERT INTO Specialization(name) VALUES(?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, specName);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys(); 
            rs.next();
            specID = rs.getInt(1);            
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
        return specID;
    }

    public static void linkDoctorSpecialization(int doctorID, int specID)
            throws ClassNotFoundException, SQLException {
        
        Connection connection       = null;
        PreparedStatement statement = null;            
        connection   = getConnection();
        try {
            String query = "INSERT INTO DoctorSpecialization VALUES(?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, doctorID);
            statement.setInt(2, specID);
            statement.executeUpdate();            
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }
    }



/*
    public static User getDoctorByAlias(String _alias) throws ClassNotFoundException, SQLException {
        Patient patient             = null; 
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement("SELECT * from Doctor JOIN User ON Doctor.doctorID = User.userID WHERE alias = ?");
            statement.setString(1, _alias);
            ResultSet result = statement.executeQuery();
            Date dob         = result.getDate  ("dob");
            int gender       = result.getString("gender");            
            int userID       = result.getInt   ("userID");
            String email     = result.getString("email");
            String alias     = result.getString("alias");
            String lastName  = result.getString("lastName");
            String password  = result.getString("password");
            String firstName = result.getString("firstName");
            Date licenseYear = result.getDate  ("gender");
            Address homeAddress = result.getString("gender");
            patient = new Patient(userID, firstName, lastName, alias, password, email);    
            statement.close();

        } finally {
            if (statement != null)   statement.close();
            if (connection != null)  connection.close();
        }
        return patient;
    }

    public static User getAddress(int aid) throws ClassNotFoundException, SQLException {
        Patient patient             = null; 
        Connection connection       = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement  = connection.prepareStatement("SELECT * from Doctor JOIN User ON Doctor.doctorID = User.userID WHERE alias = ?");
            statement.setString(1, _alias);
            ResultSet result = statement.executeQuery();
            Date dob         = result.getDate  ("dob");
            int gender       = result.getString("gender");            
            int userID       = result.getInt   ("userID");
            String email     = result.getString("email");
            String alias     = result.getString("alias");
            String lastName  = result.getString("lastName");
            String password  = result.getString("password");
            String firstName = result.getString("firstName");
            Date licenseYear = result.getDate  ("gender");
            Address homeAddress = result.getString("gender");
            patient = new Patient(userID, firstName, lastName, alias, password, email);    
            statement.close();

        } finally {
            if (statement != null)   statement.close();
            if (connection != null)  connection.close();
        }
        return patient;
    }*/

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

