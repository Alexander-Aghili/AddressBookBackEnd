package com.rest.addressbook;

import java.io.Serializable;
import java.util.Random;

//----------------------------------------------------------------------------------------------
    /* Alexander Aghili
     * COMP 232
     */
//----------------------------------------------------------------------------------------------


@SuppressWarnings("serial")
public class Contact implements Comparable<Contact>, Serializable {

    private final int codeLength = 8;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String id;

    public Contact(String firstName, String lastName, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.id = createID();
    }

    public Contact(String firstName, String lastName, String phoneNumber, String address, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getID() {
        return this.id;
    }

    //Creates random ID
    private String createID() {
        Random rand = new Random();
        String[] chars = new String[]{"A","B","C","D","E","F","G","H","I","J", "K", "L", "M", "N", "O", "P", "Q", "R","S","T","U", "V", "W", "X", "Y", "Z",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String code = "";
        for(int i = 0; i < codeLength; i++) {
            code += chars[rand.nextInt(36)];
        }

        return code;
    }

    @Override
    public String toString() {
        return "\n First Name: " + firstName + "\n Last Name: " + lastName +
                "\n Phone Number: " + phoneNumber + "\n Address : " + address + "\n ID: " + id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Contact) {
            Contact newContact = (Contact) obj;
            if (this.firstName.equals(newContact.getFirstName()) && 
                    this.lastName.equals(newContact.getLastName()) &&
                    this.phoneNumber.equals(newContact.getPhoneNumber()) &&
                    this.address.equals(newContact.getAddress()))       
                return true;
            else
                return false;
        }
        else
            return false;
    }

    @Override
    public int compareTo(Contact o) {
        if (this.lastName.equals(o.lastName)) {
            if (this.firstName.equals(o.firstName)) {
                return this.phoneNumber.compareTo(o.phoneNumber);
            } else
                return this.firstName.compareTo(o.firstName);
        } else
            return this.lastName.compareTo(o.lastName);
    }

}

