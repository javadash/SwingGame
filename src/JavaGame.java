import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import com.mysql.jdbc.PreparedStatement;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPasswordField;

public class JavaGame extends JFrame {
	
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirmPassword;
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtEmail;
	private JButton registerButton;

	public JavaGame() {
		this.setResizable(false);
		this.setMinimumSize(new  Dimension(600, 480));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Registration Page");
		
		txtUsername = new JTextField(20);
		txtPassword = new JPasswordField(20);
		txtConfirmPassword = new JPasswordField(20);
		txtName = new JTextField(15);
		txtSurname = new JTextField(25);
		txtEmail = new JTextField(50);	
		registerButton = new JButton("Register");
		//registerButton.addActionListener(this);
		
		JPanel registrationPanel = new JPanel();
		registrationPanel.setLayout(new GridLayout(7, 2, 10, 20));
		registrationPanel.add(new JLabel("First Name:"));
        registrationPanel.add(txtName);
        registrationPanel.add(new JLabel("Last Name:"));
        registrationPanel.add(txtSurname);
        registrationPanel.add(new JLabel("Username:"));
        registrationPanel.add(txtUsername);
        registrationPanel.add(new JLabel("Password:"));
        registrationPanel.add(txtPassword);
        registrationPanel.add(new JLabel("Re-enter Password:"));
        registrationPanel.add(txtConfirmPassword);
        registrationPanel.add("South", registerButton);
		
		this.setContentPane(registrationPanel);
		this.pack();

		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(RegisterData()) {
					JOptionPane.showMessageDialog(null,
							"Registration was Successful");
				}
			}
		});
	}
	
	private Boolean RegisterData()
	{
		
		String strUsername = txtUsername.getText();
		String strPassword = new String(txtPassword.getPassword());
		String strConfirmPassword = new String(txtConfirmPassword.getPassword());
		String strName = txtName.getText();
		String strEmail = txtEmail.getText();
		Boolean userDuplicate = validate_userName(strUsername);
		
		if(strUsername.equals("")) // Username
		{
			JOptionPane.showMessageDialog(null,
					"Please Input (Username)");
			txtUsername.requestFocusInWindow(); 
			return false;
		}
		if(userDuplicate) // Username already exists
		{
			JOptionPane.showMessageDialog(null,
					"Sorry username already exist!");
			txtUsername.requestFocusInWindow(); 
			return false;
		}
		if(strPassword.equals("")) // Password
		{
			JOptionPane.showMessageDialog(null,
					"Please Input (Password)");
			txtPassword.requestFocusInWindow(); 
			return false;
		}
		
		if(strConfirmPassword.equals("")) // Confirm Password
		{
			JOptionPane.showMessageDialog(null,
					"Please Input (Confirm Password)");
			txtConfirmPassword.requestFocusInWindow(); 
			return false;
		}
		if(!strPassword.equals(strConfirmPassword)) // Password math
		{
			JOptionPane.showMessageDialog(null,
					"Please Input (Password Not Match!)");
			txtPassword.requestFocusInWindow(); 
			return false;
		}		
		if(strName.equals("")) // Name
		{
			JOptionPane.showMessageDialog(null,
					"Please Input (Name)");
			txtName.requestFocusInWindow(); 
			return false;
		}	
		
		if(strEmail.equals("")) // Email
		{
			JOptionPane.showMessageDialog(null,
					"Please Input (Email)");
			txtEmail.requestFocusInWindow(); 
			return false;
		}	
		
/*		Connection connect = null;
		Statement s = null;
		Boolean status = false;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager.getConnection(""
					+ "jdbc:mysql://localhost/mydatabase"
					+ "?user=root&password=root");

			s = connect.createStatement();
			
			// SQL Insert
			String sql = "INSERT INTO member "
					+ "(Username,Password,Email,Name) "
					+ "VALUES ('" + strUsername + "','"
					+ strPassword + "','"
					+ strEmail + "'" + ",'"
					+ strName + "') ";
			s.execute(sql);
		
			
			// Reset Text Fields
			txtUsername.setText("");
			txtPassword.setText("");
			txtConfirmPassword.setText("");
			txtName.setText("");
			txtEmail.setText("");
				
			status  = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}*/

/*		try {
			if (s != null) {
				s.close();
				connect.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}*/
		
		return true;

	}

	
	//Method validates the username & password
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
    }
	
    
    
    
/*	public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == button) {
        //Send data over socket
            String text = textArea.getText();
            out.println(text);
            textArea.setText(new String(""));
            //Receive text from server
            try {
                String line = in.readLine();
                System.out.println("Text received :" + line);
            } catch (IOException e) {
                System.out.println("Read failed");
                System.exit(1);
            }
        }
    }*/
	
	//Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JavaGame frame = new JavaGame();
				frame.setVisible(true);
			}
		});
	}

	
}