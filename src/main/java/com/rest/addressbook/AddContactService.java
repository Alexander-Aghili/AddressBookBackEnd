package com.rest.addressbook;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
/*
 * Alexander Aghili
 * Comp 232
 * 
 * REST API built with Apache TOMCAT 8.5, compiled and run in Java 8, and built with Maven.
 */

/*
 * All of the REST POST services have the following format: 
 * Receives a JSON but in a String format.
 * Contact is created from the JSON, using the static getContactFromJSON method in the GetContactFromJSON class
 * Adjusts the database as necessary and gets a response from the database.
 * Returns the message, either an error from the database or ok. 
 * However, he messaging was just put in for testing and the client controller doesn't use the message.
 */
@Path("/addContact")
public class AddContactService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addContact(String contactString) {
		Contact contact = GetContactFromJSON.getContactFromJSON(contactString);
		String response = AdjustDatabase.addContact(contact);
		return Response.status(201).entity(response).build();
	}
	
}
