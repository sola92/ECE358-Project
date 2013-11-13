/* change xxxxx to your uw user id */
USE ece536db_vcoste; 

DROP TABLE IF EXISTS Address;
CREATE TABLE Address(
	addressID  	  INT,
	streetNumber  VARCHAR(100),	
	streetName    VARCHAR(100),		
	postalCode    VARCHAR(20),
	city 	      VARCHAR(100),
	province      VARCHAR(100), 
	PRIMARY KEY(addressID)
);

DROP TABLE IF EXISTS Patient;
CREATE TABLE Patient(
	patientID INT,
	alias 	  VARCHAR(20), 
	firstName VARCHAR(100), 
	lastName  VARCHAR(100), 
	email 	  VARCHAR(100),
	password  VARCHAR(254), 
	PRIMARY KEY(patientID)
);

DROP TABLE IF EXISTS Specialization;
CREATE TABLE Specialization(
	specID INT, 
	name   VARCHAR(100),
	PRIMARY KEY(specID)
);

DROP TABLE IF EXISTS Doctor;
CREATE TABLE Doctor( 	
	doctorID 		INT,
	alias 			VARCHAR(20), 
	firstName  		VARCHAR(100), 
	lastName 		VARCHAR(100), 
	gender 			SMALLINT, 
	dob 			DATE, 
	homeAddressID  	VARCHAR(100), 
	licenseYear 	INT,
    PRIMARY KEY (doctorID),
    FOREIGN KEY (homeAddressID) REFERENCES Address(addressID)
);

DROP TABLE IF EXISTS WorkAddresses;
CREATE TABLE WorkAddresses(
	doctorID  INT,
	addressID INT,
	PRIMARY KEY (doctorID, addressID),
	FOREIGN KEY (doctorID)  REFERENCES Doctor(doctorID),
	FOREIGN KEY (addressID) REFERENCES Address(addressID)
);

DROP TABLE IF EXISTS DoctorSpecialization;
CREATE TABLE DoctorSpecialization(
	doctorID INT,
	specID   INT,
	PRIMARY KEY (doctorID, specID),
	FOREIGN KEY (doctorID) REFERENCES Doctor(doctorID),
	FOREIGN KEY (specID)   REFERENCES Specialization(specID)	
);

DROP TABLE IF EXISTS Review;
CREATE TABLE Review(
	doctorID   INT,
	patientID  INT,
	rating 	   SMALLINT,
	note       VARCHAR(200),
	reviewDate DATE,
	PRIMARY KEY (doctorID, patientID)
	FOREIGN KEY (doctorID)  REFERENCES Doctor(doctorID),
	FOREIGN KEY (patientID) REFERENCES Patient(patientID),
);

DROP TABLE IF EXISTS Friendship;
CREATE TABLE Friendship(
	followerID INT,
	followeeID INT,
	PRIMARY KEY (followerID, followeeID)
	FOREIGN KEY (followerID) REFERENCES Patient(patientID),
	FOREIGN KEY (followeeID) REFERENCES Patient(patientID),	
);

CREATE VIEW DocterProfileView AS 
SELECT *
FROM 
	(SELECT doctorID
		   	alias,
		   	firstName,
		   	lastName,
		   	gender,
		  	licenseYear FROM Doctor) d
	NATURAL JOIN WorkAddresses
	NATURAL JOIN Address
	NATURAL JOIN DoctorSpecialization
	NATURAL JOIN Specialization
	INNER JOIN (
	 SELECT AVG(rating) AS averageRating, doctorID FROM Review
	 GROUP BY doctorID
	) AS r 
	ON r.doctorID = d.doctorID




