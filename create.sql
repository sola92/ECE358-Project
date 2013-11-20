/* change xxxxx to your uw user id */
USE ece356db_oaogunsa; 

DROP TABLE IF EXISTS User;
CREATE TABLE User(
	userID  	  INT AUTO_INCREMENT,
	firstName 	  VARCHAR(100), 
	lastName  	  VARCHAR(100), 
	alias	      VARCHAR(20), 
	password  	  VARCHAR(254), 
	PRIMARY KEY (userID),
	UNIQUE(alias)
);


DROP TABLE IF EXISTS Address;
CREATE TABLE Address(
	addressID   INT AUTO_INCREMENT,
	streetAddress  VARCHAR(100),		
	postalCode  VARCHAR(20),
	city 	    VARCHAR(100),
	province    VARCHAR(100), 	
	PRIMARY KEY(addressID)
);

DROP TABLE IF EXISTS Patient;
CREATE TABLE Patient(
	email 	  VARCHAR(100),
	patientID INT,
	PRIMARY KEY (patientID),
	FOREIGN KEY (patientID) REFERENCES User(userID)
		ON DELETE CASCADE
);

DROP TABLE IF EXISTS Administrator;
CREATE TABLE Administrator(
	adminID INT,
	PRIMARY KEY (adminID),
	FOREIGN KEY (adminID) REFERENCES User(userID)
		ON DELETE CASCADE
);

DROP TABLE IF EXISTS Specialization;
CREATE TABLE Specialization(
	specID INT AUTO_INCREMENT, 
	name   VARCHAR(100),
	PRIMARY KEY(specID)
);

DROP TABLE IF EXISTS Doctor;
CREATE TABLE Doctor( 	
	doctorID 		INT,
	gender 			SMALLINT, 
	dob 			DATE, 
	homeAddressID  	INT, 
	licenseYear 	INT,
    PRIMARY KEY (doctorID),
    FOREIGN KEY (homeAddressID) REFERENCES Address(addressID),
    FOREIGN KEY (doctorID)  	REFERENCES User(userID)
    	ON DELETE CASCADE
);

DROP TABLE IF EXISTS WorkAddresses;
CREATE TABLE WorkAddresses(
	doctorID  INT,
	addressID INT,
	PRIMARY KEY (doctorID, addressID),
	FOREIGN KEY (doctorID)  REFERENCES Doctor(doctorID)
		ON DELETE CASCADE,
	FOREIGN KEY (addressID) REFERENCES Address(addressID)
		ON DELETE CASCADE
);

DROP TABLE IF EXISTS DoctorSpecialization;
CREATE TABLE DoctorSpecialization(
	doctorID INT,
	specID   INT,
	PRIMARY KEY (doctorID, specID),
	FOREIGN KEY (doctorID) REFERENCES Doctor(doctorID)
		ON DELETE CASCADE,
	FOREIGN KEY (specID)   REFERENCES Specialization(specID)	
		ON DELETE CASCADE
);

DROP TABLE IF EXISTS Review;
CREATE TABLE Review(
	reviewID   INT AUTO_INCREMENT,
	doctorID   INT,
	patientID  INT,
	rating 	   SMALLINT,
	note       VARCHAR(200),
	reviewDate DATE,
	PRIMARY KEY (reviewID),
	FOREIGN KEY (doctorID)  REFERENCES Doctor (doctorID)
		ON DELETE CASCADE,
	FOREIGN KEY (patientID) REFERENCES Patient(patientID)
		ON DELETE CASCADE
);

DROP TABLE IF EXISTS Likes;
CREATE TABLE Likes(
	doctorID   INT,
	patientID  INT,
	PRIMARY KEY (doctorID, patientID),
	FOREIGN KEY (doctorID)  REFERENCES Doctor(doctorID),
	FOREIGN KEY (patientID) REFERENCES Patient(patientID)
);


DROP TABLE IF EXISTS Friendship;
CREATE TABLE Friendship(
	followerID INT,
	followeeID INT,
	PRIMARY KEY (followerID, followeeID),
	FOREIGN KEY (followerID) REFERENCES Patient(patientID)
		ON DELETE CASCADE,
	FOREIGN KEY (followeeID) REFERENCES Patient(patientID)
		ON DELETE CASCADE	
);

INSERT INTO Specialization(name) VALUES ('Allergologist');
INSERT INTO Specialization(name) VALUES ('Anesthesiologist');
INSERT INTO Specialization(name) VALUES ('Cardiologist');
INSERT INTO Specialization(name) VALUES ('Coroner');
INSERT INTO Specialization(name) VALUES ('Dentist');
INSERT INTO Specialization(name) VALUES ('Dermatologist');
INSERT INTO Specialization(name) VALUES ('Diabetologist');
INSERT INTO Specialization(name) VALUES ('Emergency physician');
INSERT INTO Specialization(name) VALUES ('Endocrinologists');
INSERT INTO Specialization(name) VALUES ('Euthanasia doctor');
INSERT INTO Specialization(name) VALUES ('Physician');
INSERT INTO Specialization(name) VALUES ('Surgeons of Glasgo');
INSERT INTO Specialization(name) VALUES ('Physicians of Edinburg');
INSERT INTO Specialization(name) VALUES ('Surgeons of Edinburg');
INSERT INTO Specialization(name) VALUES ('Gastroenterologist');
INSERT INTO Specialization(name) VALUES ('General practitioner');
INSERT INTO Specialization(name) VALUES ('Gynaecologist');
INSERT INTO Specialization(name) VALUES ('Hematologist');
INSERT INTO Specialization(name) VALUES ('High-altitude medicine physician');
INSERT INTO Specialization(name) VALUES ('Hygienist');
INSERT INTO Specialization(name) VALUES ('Immunologist');
INSERT INTO Specialization(name) VALUES ('Internist');
INSERT INTO Specialization(name) VALUES ('Leprologist');
INSERT INTO Specialization(name) VALUES ('Military physician');
INSERT INTO Specialization(name) VALUES ('Nephrologist');
INSERT INTO Specialization(name) VALUES ('Neurologist');
INSERT INTO Specialization(name) VALUES ('Neurosurgeon');
INSERT INTO Specialization(name) VALUES ('Nuclear medicine physician');
INSERT INTO Specialization(name) VALUES ('Obstetrician');
INSERT INTO Specialization(name) VALUES ('Oncologist');
INSERT INTO Specialization(name) VALUES ('Ophthalmologist');
INSERT INTO Specialization(name) VALUES ('Orthopaedist');
INSERT INTO Specialization(name) VALUES ('Osteopathic physician');
INSERT INTO Specialization(name) VALUES ('Otolaryngologist');
INSERT INTO Specialization(name) VALUES ('Paleopathologist');
INSERT INTO Specialization(name) VALUES ('Parasitologist');
INSERT INTO Specialization(name) VALUES ('Pathologist');
INSERT INTO Specialization(name) VALUES ('Pediatrician');
INSERT INTO Specialization(name) VALUES ('Phthisiatrist');
INSERT INTO Specialization(name) VALUES ('Podiatrist');
INSERT INTO Specialization(name) VALUES ('Psychiatrist');
INSERT INTO Specialization(name) VALUES ('Pulmonologist');
INSERT INTO Specialization(name) VALUES ('Radiologist');
INSERT INTO Specialization(name) VALUES ('Rheumatologist');
INSERT INTO Specialization(name) VALUES ('Serologist');
INSERT INTO Specialization(name) VALUES ('Surgeon');
INSERT INTO Specialization(name) VALUES ('Team physician');
INSERT INTO Specialization(name) VALUES ('Toxicologist');
INSERT INTO Specialization(name) VALUES ('Traumatologist');
INSERT INTO Specialization(name) VALUES ('Tropical physician');
INSERT INTO Specialization(name) VALUES ('Urologist');
INSERT INTO Specialization(name) VALUES ('Venereologist');



CREATE INDEX doctor_gender 		USING HASH ON Doctor(gender);
CREATE INDEX doctor_licenseYear USING BTREE ON Doctor(licenseYear);

CREATE INDEX user_lastName 	USING HASH ON User(lastName);
CREATE INDEX user_firstName USING HASH ON User(firstName);


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

SELECT 	DISTINCT u.*, d.*
        FROM Doctor AS d
        INNER JOIN User AS u ON d.doctorID = u.userID
        LEFT OUTER JOIN (
            SELECT 
            doctorID,
            addressID   	AS 	workAddressID,
                    streetAddress 	AS  workStreetAddress,
                    postalCode  	AS  workPostalCode,
                    city 	    	AS  workCity,
                    province    	AS  workProvince
                    FROM Doctor 
                    NATURAL JOIN WorkAddresses
                    NATURAL JOIN Address
        ) wa ON wa.doctorID = d.doctorID 
        LEFT OUTER JOIN (
            SELECT IFNULL(AVG(r.rating), 0) as averageRating, d2.doctorID
            FROM Review AS r
            RIGHT OUTER JOIN Doctor d2 ON d2.doctorID = r.doctorID
            GROUP BY doctorID
        ) ar ON ar.doctorID = d.doctorID 
        LEFT OUTER JOIN DoctorSpecialization s ON s.doctorID = d.doctorID 
        WHERE 1=1 AND firstName = 'foo' AND lastName = 'foo'
        AND gender = 1 AND workStreetAddress = 'foo' AND workPostalCode = 'foo'
        AND workCity = 'foo' AND workProvince = 'foo' AND licenseYear >= 1990;


