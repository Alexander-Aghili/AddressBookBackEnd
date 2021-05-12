package com.rest.addressbook;

import org.json.JSONObject;

public class GetContactFromJSON {

	//Recieves a String in JSON format, quick conversion to JSONObject, and then turns the info info a contact.
	public static Contact getContactFromJSON(String string) {
		JSONObject obj = new JSONObject(string);
		return new Contact(
				obj.getString("firstname"), 
				obj.getString("lastname"), 
				obj.getString("phonenumber"), 
				obj.getString("address"), 
				obj.getString("code"));
	}
	
}
