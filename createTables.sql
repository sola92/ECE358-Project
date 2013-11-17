
/* change xxxxx to your uw user id */
USE ece356db_oaogunsa; 

/*SELECT * 
FROM (
    SELECT * FROM Patient INNER JOIN User 
    ON Patient.patientID = User.userID
    WHERE alias LIKE '%peter%' AND patientID <> 2
) AS p
WHERE (SELECT COUNT(*) FROM Friendship WHERE followerID = 2 AND followeeID = p.patientID) = 0*/

SELECT * FROM Friendship;

/*SELECT * FROM User;*/