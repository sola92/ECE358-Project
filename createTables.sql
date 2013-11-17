
/* change xxxxx to your uw user id */
USE ece356db_oaogunsa; 

SELECT * from Patient JOIN User ON Patient.patientID = User.userID WHERE alias = "foo";