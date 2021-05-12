package com.rest.addressbook;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/editContact")
public class EditContactService {

	@Path("/firstname")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editContactFirstName(String contactString) {
		Contact contact = GetContactFromJSON.getContactFromJSON(contactString);
		String response = AdjustDatabase.editContact(contact, "firstname");
		return Response.status(201).entity(response).build();
	}
	
	@Path("/lastname")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editContactLastName(String contactString) {
		Contact contact = GetContactFromJSON.getContactFromJSON(contactString);
		String response = AdjustDatabase.editContact(contact, "lastname");
		return Response.status(201).entity(response).build();
	}
	
	@Path("/phonenumber")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editContactPhoneNumber(String contactString) {
		Contact contact = GetContactFromJSON.getContactFromJSON(contactString);
		String response = AdjustDatabase.editContact(contact, "phonenumber");
		return Response.status(201).entity(response).build();
	}
	
	@Path("/address")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editContactAddress(String contactString) {
		Contact contact = GetContactFromJSON.getContactFromJSON(contactString);
		String response = AdjustDatabase.editContact(contact, "address");
		return Response.status(201).entity(response).build();
	}
	
}
