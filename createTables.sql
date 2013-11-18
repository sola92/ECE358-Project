/* change xxxxx to your uw user id */
USE ece356db_oaogunsa; 

CREATE VIEW DoctorProfileView AS 
SELECT 	
	userID,
	firstName, 
	lastName, 
	alias, 
	doctorID,
	gender, 
	licenseYear		
FROM Doctor AS d 
INNER JOIN User AS u ON d.doctorID = u.userID;



