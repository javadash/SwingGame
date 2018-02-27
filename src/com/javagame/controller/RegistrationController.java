package com.javagame.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.javagame.controller.*;
import com.javagame.model.*;
import com.javagame.view.*;
import com.mysql.jdbc.PreparedStatement;

public class RegistrationController implements ActionListener {
	private User user;
    private DbManager dbManager;
    private RegistrationWindow registrationWindow;

    public RegistrationController(RegistrationWindow registrationWindow) {
        this.registrationWindow = registrationWindow;
        this.dbManager = new DbManager();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("registerButton")) {
			if(InputValidation()) {
				if(dbManager.addNewUser(user)){
					JOptionPane.showMessageDialog(null,
							"Registration was Successful");
				}
			}
        }
    }
    
	private Boolean InputValidation()
	{
		
		user = registrationWindow.getUserInput();
		String confirmPassword = new String(registrationWindow.getPassword());
		Boolean userDuplicate = dbManager.validate_userName(user.getUserName());
		
		if(user.getUserName().equals("")) // Username
		{
			JOptionPane.showMessageDialog(null,"Please enter a valid Username.","Error", JOptionPane.ERROR_MESSAGE);
			//registrationWindow.txtUsername.requestFocusInWindow(); 
			return false;
		}
		
		if(userDuplicate) // Username already exists
		{
			JOptionPane.showMessageDialog(null,"Sorry username already exist!","Error", JOptionPane.ERROR_MESSAGE);
			//txtUsername.requestFocusInWindow(); 
			return false;
		}
		
		if(user.getPassword().equals("")) // Password
		{
			JOptionPane.showMessageDialog(null,"Please enter a password.","Error", JOptionPane.ERROR_MESSAGE);
			//txtPassword.requestFocusInWindow(); 
			return false;
		}
		
		if(confirmPassword.equals("")) // Confirm Password
		{
			JOptionPane.showMessageDialog(null,"Please confirm your password","Error", JOptionPane.ERROR_MESSAGE);
			//txtConfirmPassword.requestFocusInWindow(); 
			return false;
		}
		
		if(!user.getPassword().equals(confirmPassword)) // Password math
		{
			JOptionPane.showMessageDialog(null, "Passwords do not match!","Error", JOptionPane.ERROR_MESSAGE);
			//txtPassword.requestFocusInWindow(); 
			return false;
		}	
		
		if(user.getFirstName().equals("")) // Name
		{
			JOptionPane.showMessageDialog(null, "Please enter a valid First Name.","Error", JOptionPane.ERROR_MESSAGE);
			//txtName.requestFocusInWindow(); 
			return false;
		}	
		
		if(user.getLastName().equals("")) // Name
		{
			JOptionPane.showMessageDialog(null, "Please enter a valid Last Name.","Error", JOptionPane.ERROR_MESSAGE);
			//txtName.requestFocusInWindow(); 
			return false;
		}
		
		if(user.getEmail().equals("")) // Name
		{
			JOptionPane.showMessageDialog(null, "Please enter a valid Email Address.","Error", JOptionPane.ERROR_MESSAGE);
			//txtName.requestFocusInWindow(); 
			return false;
		}
		return true;
	}
	
/*	//Method validates the username & password
    private boolean validate_userName(String username) {
        try{           
            Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/gamesdb" ,"root",null);     
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("Select * from users where username=?");
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();                        
            if(rs.next())            
                return true;    
            else
                return false;            
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
       
    }*/
    
	//Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				RegistrationWindow frame = new RegistrationWindow();
				frame.setVisible(true);
			}
		});
	}

	

}
