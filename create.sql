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

INSERT INTO Specialization(name) VALUES ('Anesthesiologists');
INSERT INTO Specialization(name) VALUES ('Cardiologists');
INSERT INTO Specialization(name) VALUES ('Coroners');
INSERT INTO Specialization(name) VALUES ('Dentists');
INSERT INTO Specialization(name) VALUES ('Dermatologists');
INSERT INTO Specialization(name) VALUES ('Diabetologists');
INSERT INTO Specialization(name) VALUES ('Emergency physicians');
INSERT INTO Specialization(name) VALUES ('Endocrinologists');
INSERT INTO Specialization(name) VALUES ('Euthanasia doctors');
INSERT INTO Specialization(name) VALUES ('General Practitioners');
INSERT INTO Specialization(name) VALUES ('Physicians');
INSERT INTO Specialization(name) VALUES ('Physicians');
INSERT INTO Specialization(name) VALUES ('Surgeons of Glasgow');
INSERT INTO Specialization(name) VALUES ('Physicians of Edinburgh');
INSERT INTO Specialization(name) VALUES ('Surgeons of Edinburgh');
INSERT INTO Specialization(name) VALUES ('Gastroenterologists');
INSERT INTO Specialization(name) VALUES ('General practitioners');
INSERT INTO Specialization(name) VALUES ('Gynaecologists');
INSERT INTO Specialization(name) VALUES ('Hematologists');
INSERT INTO Specialization(name) VALUES ('High-altitude medicine physicians');
INSERT INTO Specialization(name) VALUES ('Hygienists');
INSERT INTO Specialization(name) VALUES ('Immunologists');
INSERT INTO Specialization(name) VALUES ('Internists');
INSERT INTO Specialization(name) VALUES ('Leprologists');
INSERT INTO Specialization(name) VALUES ('Military physicians');
INSERT INTO Specialization(name) VALUES ('Nephrologists');
INSERT INTO Specialization(name) VALUES ('Neurologists');
INSERT INTO Specialization(name) VALUES ('Neurosurgeons');
INSERT INTO Specialization(name) VALUES ('Nuclear medicine physicians');
INSERT INTO Specialization(name) VALUES ('Obstetricians');
INSERT INTO Specialization(name) VALUES ('Oncologists');
INSERT INTO Specialization(name) VALUES ('Ophthalmologists');
INSERT INTO Specialization(name) VALUES ('Orthopaedists');
INSERT INTO Specialization(name) VALUES ('Osteopathic physicians');
INSERT INTO Specialization(name) VALUES ('Otolaryngologists');
INSERT INTO Specialization(name) VALUES ('Paleopathologists');
INSERT INTO Specialization(name) VALUES ('Parasitologists');
INSERT INTO Specialization(name) VALUES ('Pathologists');
INSERT INTO Specialization(name) VALUES ('Pediatricians');
INSERT INTO Specialization(name) VALUES ('Phthisiatrists');
INSERT INTO Specialization(name) VALUES ('Podiatrists');
INSERT INTO Specialization(name) VALUES ('Psychiatrists');
INSERT INTO Specialization(name) VALUES ('Pulmonologists');
INSERT INTO Specialization(name) VALUES ('Radiologists');
INSERT INTO Specialization(name) VALUES ('Rheumatologists');
INSERT INTO Specialization(name) VALUES ('Serologists');
INSERT INTO Specialization(name) VALUES ('Surgeons');
INSERT INTO Specialization(name) VALUES ('Team physicians');
INSERT INTO Specialization(name) VALUES ('Toxicologists');
INSERT INTO Specialization(name) VALUES ('Traumatologists');
INSERT INTO Specialization(name) VALUES ('Tropical physicians');
INSERT INTO Specialization(name) VALUES ('Urologists');
INSERT INTO Specialization(name) VALUES ('Venereologists');


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
        WHERE 1=1 AND u.firstName = 'asdopi';


