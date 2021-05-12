# AddressBookBackEnd
This is the REST API for Address Book Project

The services perform the POST and GET requests.
AdjustContact takes in the information from the requests and adjutss the SQL database as neccessary.

Username and password are hidden as *. Adjust to own username and password for assosiated database if you want to run this on your own.

AddressBookBackEnd.war is contained in this repo. It is just the .war file uploaded to the AWS Elastic Beanstalk used to host the links.

This runs on Apache Tomcat 8.5, code is compiled with Java 8, and on AWS is run on JRE 8 as well. War is built with Maven. 
