/* change xxxxx to your uw user id */
USE ece356db_oaogunsa; 

CREATE INDEX doctor_gender USING HASH ON Doctor(gender);
CREATE INDEX doctor_licenseYear USING BTREE ON Doctor(licenseYear);

CREATE INDEX user_firstName USING HASH ON User(firstName);
CREATE INDEX user_lastName USING HASH ON User(lastName);

CREATE INDEX user_streetAddress USING HASH ON Address(streetAddress);
CREATE INDEX user_province USING HASH ON Address(province);
CREATE INDEX user_city USING HASH ON Address(city);
CREATE INDEX user_postalCode USING HASH ON Address(postalCode);

CREATE INDEX specialization_name USING HASH ON Specialization(name);


