/* change xxxxx to your uw user id */
USE ece356db_oaogunsa; 

CREATE VIEW PatientProfileView AS 
SELECT 	
	userID,
	firstName, 
	lastName, 
	alias, 
	email,
	patientID	
FROM Patient AS p 
INNER JOIN User AS u ON p.patientID = u.userID;


