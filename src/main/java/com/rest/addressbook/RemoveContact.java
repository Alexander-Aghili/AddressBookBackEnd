package com.rest.addressbook;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/removeContact")
public class RemoveContact {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeContact(String contactString) {
		Contact contact = GetContactFromJSON.getContactFromJSON(contactString);
		String response = AdjustDatabase.deleteContact(contact);
		return Response.status(201).entity(response).build();
	}
	
}
