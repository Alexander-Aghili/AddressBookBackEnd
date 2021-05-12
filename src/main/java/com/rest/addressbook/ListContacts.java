package com.rest.addressbook;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/listContacts")
public class ListContacts {
	
	JSONObject jsonMain = new JSONObject();
	JSONArray jsonContactArray = new JSONArray();
	JSONObject jsonContact = new JSONObject();
	
	//Returns a JSON that contains the JSONArray of Contacts in JSON format. Say that 5 times over
	@GET
	@Produces("application/json")
	public Response returnListOfBooksJSON() {
		ArrayList<Contact> contactList = AdjustDatabase.listContacts();
		String jsonString = "" + jsonInfoOfContacts(contactList); //I don't remember why I add the "" but I remember it not working otherwise so leave it.
		return Response.status(200).entity(jsonString).build();
		
	}
	
	//Turns the ArrayList of contacts into the JSON format mentioned above.
	private JSONObject jsonInfoOfContacts(ArrayList<Contact> contactList) {
		for (Contact contact: contactList) {
			jsonContact = new JSONObject();
			
			jsonContact.put("firstname", contact.getFirstName());
			jsonContact.put("lastname", contact.getLastName());
			jsonContact.put("phonenumber", contact.getPhoneNumber());
			jsonContact.put("address", contact.getAddress());
			jsonContact.put("id", contact.getID());
			
			jsonContactArray.put(jsonContact);
			jsonContact = null;
		}
		jsonMain.put("Contacts", jsonContactArray);
		return jsonMain;
	}
	

}
