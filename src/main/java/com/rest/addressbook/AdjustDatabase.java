package com.rest.addressbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.rest.addressbook.*;

/*
 * Adjusting the MySQL database 
 * Reason I made it static is because I didn't want to have to do AdjustDatabase ab = new AdjustDatabse() for every Rest call.
 * Seems like a time waster to me.
 */
public class AdjustDatabase {
	
	private static Connection connect = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static PreparedStatement preparedStatement = null;
	
	//Values are hidden, if using, put in appropriate username and password
	private static final String username = "*****";
	private static final String password = "********";
	//Sql domain, in this case is the AWS, could be localhost.
	private static final String sqlDomain = "contact-database.crumhr25o7ma.us-east-1.rds.amazonaws.com";
	
	//Adds contact
	//Returns string, ok if no error, otherwise returns error message. Used for testing but didn't see a reason to change it in
	// case client needed to know when it returned.
	public static String addContact(Contact contact) {
		String response = "";
		try {
			initializeDB();
			//It was really cool debugging for 4 hours, and it was a missing ) in the String below.
			preparedStatement = connect.prepareStatement("insert into contactdb.contacts values(default, ?, ?, ?, ?, ?)"); 
			addContactPreparedStatement(contact);
			preparedStatement.executeUpdate();
			response = "ok";
		} catch (ClassNotFoundException | SQLException e) {
			response = e.getMessage();
		} finally {
			close();
		}
		return response;
	}
	
	private static void addContactPreparedStatement(Contact contact) throws SQLException {
		preparedStatement.setString(1, contact.getFirstName());
		preparedStatement.setString(2, contact.getLastName());
		preparedStatement.setString(3, contact.getPhoneNumber());
		preparedStatement.setString(4, contact.getAddress());
		preparedStatement.setString(5, contact.getID());
		
	}

	//REST services sends in an updated contact as well as what it wants to edit.
	//Reason I did this was to prevent having to initdb for all four methods. Also makes it easier to scale if more info in contact wanted to be added.
	public static String editContact(Contact contact, String edit) {
		String response = "";
		try {
			initializeDB();
			//Checks which parts wants to be altered and adjusts as necessary.
			if (edit.equals("firstname")) {
				editFirstName(contact);
			} else if (edit.equals("lastname")) {
				editLastName(contact);
			} else if (edit.equals("phonenumber")) {
				editPhoneNumber(contact);
			} else if (edit.equals("address")) {
				editAddress(contact);
			}
			preparedStatement.executeUpdate();
			
			response = "ok";
		} catch (ClassNotFoundException | SQLException e) {
			response = e.getMessage();
		} finally {
			close();
		}
		return response;
	}
	
	//All four methods are about the same with some alterations as necessary. 
	//Letting MySQL do the searching by adding where IDCode = ? since IDCode is unique.
	private static void editFirstName(Contact contact) throws SQLException {
		preparedStatement = connect.prepareStatement("UPDATE contactdb.contacts SET firstname = ? where IDCode = ?");
		preparedStatement.setString(1, contact.getFirstName());
		preparedStatement.setString(2, contact.getID());
	}
	
	private static void editLastName(Contact contact) throws SQLException {
		preparedStatement = connect.prepareStatement("UPDATE contactdb.contacts SET lastname = ? where IDCode = ?");
		preparedStatement.setString(1, contact.getLastName());
		preparedStatement.setString(2, contact.getID());
	}
	
	private static void editPhoneNumber(Contact contact) throws SQLException {
		preparedStatement = connect.prepareStatement("UPDATE contactdb.contacts SET phonenumber = ? where IDCode = ?");
		preparedStatement.setString(1, contact.getPhoneNumber());
		preparedStatement.setString(2, contact.getID());
	}

	private static void editAddress(Contact contact) throws SQLException {
		preparedStatement = connect.prepareStatement("UPDATE contactdb.contacts SET address = ? where IDCode = ?");
		preparedStatement.setString(1, contact.getAddress());
		preparedStatement.setString(2, contact.getID());
	}
	
	
	public static String deleteContact(Contact contact) {
		String response = "";
		try {
			initializeDB();
			preparedStatement = connect.prepareStatement("delete from contactdb.contacts where IDCode = ?");
			preparedStatement.setString(1, contact.getID());
			preparedStatement.executeUpdate();
			response = "ok";
		} catch (ClassNotFoundException | SQLException e) {
			response = e.getMessage();
		} finally {
			close();
		}
		return response;
	}
	
	//Gets all SQL info from DB and turns return statement into a contact, adding it to an arraylist and return that to the REST service.
	public static ArrayList<Contact> listContacts() {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		try {
			initializeDB();
			ResultSet results = getDatabaseInfo();
			while(results.next()) {
				Contact contact = createContact(results);
				contacts.add(contact);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
		} finally {
			close();
		}
		return contacts;
		
	}
	
	//Returns contact from ResultSet info
	private static Contact createContact(ResultSet results) throws SQLException {
		Contact contact = new Contact(results.getString("firstname"),
				results.getString("lastname"), 
				results.getString("phonenumber"), 
				results.getString("address"), 
				results.getString("IDCode")
				);
		return contact;
				
	}
	
	private static ResultSet getDatabaseInfo() throws SQLException {
		return statement.executeQuery("select * from contactdb.contacts");
	}
	
	
	//All methods must init the DB when starting up to establish a connection.
	private static void initializeDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://" + sqlDomain + ":3306/contactdb", username, password);
		statement = connect.createStatement();
	}
	
	//All must close the DB at the end to ensure no leakage.
	private static void close() {
		try {
			if(resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
