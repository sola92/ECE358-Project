package ece356.project.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    public static final String nid = "vcoste";
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
    
    public static int queryAlias(String alias)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        int ret;

        try {
            //con = getConnection();
            /* Build SQL query */
            String query = "SELECT count(alias) FROM User WHERE alias = " + alias;
            pstmt = con.prepareStatement(query);
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();
            ret = resultSet.getInt("count(alias)");
            return ret;
        } finally {
            
        }
    }
    private static int addUser(String firstName, String lastName, String alias, String password)
            throws ClassNotFoundException, SQLException {
        {
            Connection con = null;
            PreparedStatement pstmt = null;
            try {
                int ret = 0;
                String query = "INSERT INTO User VALUES(userID, firstName, lastName, alias, password)";
                con = getConnection();
                pstmt = con.prepareStatement(query);
                pstmt.setString(2, firstName);
                pstmt.setString(3, lastName);
                pstmt.setString(4, alias);
                pstmt.setString(5, password);
                pstmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()){
                    ret=rs.getInt(1);
                }
                
                return ret;
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
    }
    
    public static void addPatient(String firstName, String lastName, String alias, String password, String email)
            throws ClassNotFoundException, SQLException {
        
            Connection con = null;
            PreparedStatement pstmt = null;
            ArrayList ret = null;
            try {
                
                con = getConnection();
                int userID = addUser(firstName, lastName, alias, password);
                
                pstmt = con.prepareStatement("INSERT INTO Patient VALUES(patientID, email)");
                pstmt.setInt(1, userID);
                pstmt.setString(2, email);
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
                int userID = addUser(firstName, lastName, alias, password);
                
                pstmt = con.prepareStatement("INSERT INTO Doctor VALUES(doctorID, gender, dob, homeAddressID, licenseYear)");
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
    
    public static void addAdministrator(String firstName, String lastName, String alias, String password)
            throws ClassNotFoundException, SQLException {
        
            Connection con = null;
            PreparedStatement pstmt = null;
            ArrayList ret = null;
            try {
                
                con = getConnection();
                int userID = addUser(firstName, lastName, alias, password);
                
                pstmt = con.prepareStatement("INSERT INTO Patient VALUES(adminID)");
                pstmt.setInt(1, userID);
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
}

