
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
        NATURAL JOIN (
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
        ) wa 
        NATURAL JOIN (
            SELECT AVG(rating) as averageRating, doctorID
            FROM Review
            GROUP BY doctorID
        ) ar 
        NATURAL JOIN Specialization
        WHERE 1=1 AND (
            SELECT COUNT(*) FROM Review WHERE doctorID = d.doctorID AND patientID IN (SELECT followeeID FROM Friendship WHERE followerID = 2)
        ) > 0;
