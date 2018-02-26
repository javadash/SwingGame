package com.javagame.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        
/*        finally {
        	close();
        }*/

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

    
}
