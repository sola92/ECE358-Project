package ece356;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
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
                            "INSERT INTO Review(followerID, followerID) VALUES (?,?)");
            statement.setInt(1, followerID);
            statement.setInt(2, followerID);
        } finally {
            if (statement  != null) statement.close();
            if (connection != null) connection.close();
        }            
    }           
    
    public static void addDoctor(String firstName, 
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

