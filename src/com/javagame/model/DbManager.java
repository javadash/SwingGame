package com.javagame.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.PreparedStatement;

public class DbManager {
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/gamesdb";
    final String USER = "root";
    final String PASS = null;

    private Connection connection;
    private PreparedStatement pStatement;
    private ResultSet resultSet;
    
    public DbManager() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public boolean validate_userName(String username) {
        try{               
            pStatement = (PreparedStatement) connection.prepareStatement("Select * from users where username=?");
            pStatement.setString(1, username);
            resultSet = pStatement.executeQuery();                        
            if(resultSet.next())            
                return true;    
            else
                return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }
    
    public boolean addNewUser(User user) {
        try{               
            pStatement = (PreparedStatement) connection.prepareStatement("insert into  gamesdb.users (name, surname, username, password, email) values (?, ?, ?, ? , ?)");
            pStatement.setString(1, user.getFirstName());
            pStatement.setString(2, user.getLastName());
            pStatement.setString(3, user.getUserName());
            pStatement.setString(4, hashPassword(user.getPassword()));
            pStatement.setString(5, user.getEmail());
            int numRows = pStatement.executeUpdate();                        
            if(numRows > 0)            
                return true;    
            else
                return false;
        }
        catch(Exception e){
            e.printStackTrace();

            return false;
        }
    }
    
 // You need to close the resultSet
    private void close() {
      try {
        if (resultSet != null) {
          resultSet.close();
        }

        if (connection != null) {
          connection.close();
        }
      } catch (Exception e) {

      }
    }
    
    public String hashPass(String password) throws NoSuchAlgorithmException {
    	String md5Hash;
        MessageDigest mdEnc = MessageDigest.getInstance("MD5"); 
        mdEnc.update(password.getBytes(), 0, password.length());
        md5Hash = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted 
        return md5Hash;
    }
    
    private String hashPassword(String passwordToHash){
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
        
    public void checkLogin(User user) throws SQLException, NoSuchAlgorithmException {
    	String passHashed = hashPass(user.getPassword());
    	try {
    		pStatement = (PreparedStatement) connection.prepareStatement("Select username, passoword from users");
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                if (user.getUserName().equals(resultSet.getString("username"))) {
                    if (passHashed.equals(resultSet.getString("Password"))) {
                        System.out.println("Logged in.");
                    } else {
                        System.out.println("Incorrect password - login combination.");
                    }
                } else {
                    System.out.println("Incorrect log in.");
                }
            }

        }  catch (SQLException e) {
            throw e;
        } finally {
        	resultSet.close();
            pStatement.close();
            connection.close();
            /*if (resultSet != null) try {rs.close();} catch (SQLException se){}
            if (stmt != null) try {stmt.close();} catch (SQLException se) {}
            if (con != null) try {con.close();} catch (SQLException se) {}*/
        } 
    }
    
    
}
