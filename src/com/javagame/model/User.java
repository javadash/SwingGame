package com.javagame.model;

public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
   
    public User(){
       
    }
   
    public User(String firstName, String lastName, String username, String password, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
        this.password = password;
        this.email = email;
    }

    public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
