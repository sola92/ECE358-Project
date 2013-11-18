
/* change xxxxx to your uw user id */
USE ece356db_oaogunsa; 

/*SELECT * 
FROM (
    SELECT * FROM Patient INNER JOIN User 
    ON Patient.patientID = User.userID
    WHERE alias LIKE '%peter%' AND patientID <> 2
) AS p
WHERE (SELECT COUNT(*) FROM Friendship WHERE followerID = 2 AND followeeID = p.patientID) = 0*/

/*DROP VIEW IF EXISTS DocterProfileView; 
CREATE VIEW DocterProfileView AS 
    SELECT * FROM Doctor AS d
    INNER JOIN Address AS ha ON d.homeAddressID = ha.addressID
    NATURAL JOIN WorkAddresses AS wa
    
    NATURAL JOIN DoctorSpecialization AS
    NATURAL JOIN Specialization
    INNER JOIN Address AS a ON d.homeAddressID = a.addressID*/


/*SELECT * FROM User;*/

/*SELECT * FROM DocterProfileView;*/


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


SELECT * FROM User;

