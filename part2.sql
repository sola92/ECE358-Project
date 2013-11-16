
/* Lab 3- Part 2- Add  constraints to DB 

 You must add to each table:
 a) Primary key constrains
 b) A referencial integrity constrains

*/
SET storage_engine=INNODB;

/* 2.a - Add primary key constrains 
Example, for the Department table you can do this with the commands:

ALTER TABLE Department ADD PRIMARY KEY(deptID);
or
DROP TABLE IF EXISTS Department;           
CREATE TABLE Department(deptID INT PRIMARY KEY,deptName VARCHAR(100),location VARCHAR(100));  

/* 2.b - Add referencial integrity constrains
Example, Employee table has a column that depends on column   deptID of the Dep[artment table

You will add the following to your CREATE statement (or you can use an ALTER command as well):

foreign key (deptID) references Department(deptID)

DROP TABLE IF EXISTS Employee;  
CREATE TABLE Employee(empID INT PRIMARY KEY,empName VARCHAR(100),job VARCHAR(100),deptID INT,salary INT,foreign key (deptID) references Department(deptID) );

/* Tables created in lab 1 */

USE ece356db_vcoste; 

DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Department;           


CREATE TABLE Department(deptID INT PRIMARY KEY,deptName VARCHAR(100),location VARCHAR(100));                    
          
  
CREATE TABLE Employee(empID INT PRIMARY KEY,empName VARCHAR(100),job VARCHAR(100),deptID INT,salary INT,
foreign key (deptID) references Department(deptID)
);

-- For the Assigned table, you need to specify a  
-- primary key that includes two columns                  
DROP TABLE IF EXISTS Assigned;             
CREATE TABLE Assigned(empID INT,projID INT,role VARCHAR(100),
PRIMARY KEY(empID, projID)
);                 
        

DROP TABLE IF EXISTS ProjectSupply;
DROP TABLE IF EXISTS Project;           
CREATE TABLE Project(projID INT PRIMARY KEY,title VARCHAR(100),budget INT,funds INT);


/* Tables created in lab 2 */


DROP TABLE IF EXISTS Supply; 

DROP TABLE IF EXISTS SupplyType;         

CREATE TABLE SupplyType(supplyTypeID INT PRIMARY KEY,
description VARCHAR(100));
         
CREATE TABLE Supply(supplyID INT PRIMARY KEY,
supplyDescription VARCHAR(100),
unitDescription VARCHAR(100),
costPerunit DECIMAL(8,2), 
typeID INT,
foreign key (typeID) references SupplyType(supplyTypeID)
); 



/* Add here statements to create your ProjectSupply table ( from your solution to part 1), adding primary key and foreign key constrains. */

         
CREATE TABLE ProjectSupply( projectID INT, 
                            supplyID INT ,
                            numberOfUnits INT,
                            PRIMARY KEY(projectID, supplyID),
                            FOREIGN KEY (projectID) REFERENCES Project(projID),
                            FOREIGN KEY (supplyID) REFERENCES Supply(supplyID)
);

/* Reinsert data into all tables 

You can use the INSERT statements provided in createTables.sql, and in  your solution for part1 (in your modified part1.sql)

Bonus: Practice using the MySQL autoincrement feature when inserting data into a table. 


*/

INSERT INTO Department(deptID, deptName, location) VALUES(3, 'marketing', 'Waterloo');             
INSERT INTO Department(deptID, deptName, location) VALUES(7, 'research', 'Guelph');            
INSERT INTO Department(deptID, deptName, location) VALUES(12, 'software', 'Toronto'); 
INSERT INTO Department(deptID, deptName, location) VALUES(13, 'computing', 'Toronto'); 

INSERT INTO Employee(empID, empName, job, deptID, salary) VALUES(23, 'Smith', 'programmer', 12, 35000); 
INSERT INTO Employee(empID, empName, job, deptID, salary) VALUES(45, 'Kelly', 'engineer', 7, 37000); 
INSERT INTO Employee(empID, empName, job, deptID, salary) VALUES(56, 'Herr', 'janitor', 7, 26000); 
INSERT INTO Employee(empID, empName, job, deptID, salary) VALUES(89, 'Williams', 'analyst', 12, 36000); 
INSERT INTO Employee(empID, empName, job, deptID, salary) VALUES(77, 'Hergot', 'secretary', 7, 28000); 
INSERT INTO Employee(empID, empName, job, deptID, salary) VALUES(66, 'Hess', 'technician', 7, 32000); 
INSERT INTO Employee(empID, empName, job, deptID, salary) VALUES(92, 'Mays', 'engineer', 7, 45000); 
INSERT INTO Employee(empID, empName, job, deptID, salary) VALUES(68, 'Morris', 'secretary', 3, 23000);  
INSERT INTO Employee(empID, empName, job, deptID, salary) VALUES(69, 'Maria', 'engineer', 3, 32000); 


INSERT INTO Project(projID, title, budget, funds) VALUES(345, 'compiler', 500000, 250000); 
INSERT INTO Project(projID, title, budget, funds) VALUES(123, 'display', 650000, 370000); 


INSERT INTO Assigned(empID, projID, role) VALUES(23, 345, 'programmer');            
INSERT INTO Assigned(empID, projID, role) VALUES(66, 123, 'programmer');        
INSERT INTO Assigned(empID, projID, role) VALUES(77, 123, 'secretary');           
INSERT INTO Assigned(empID, projID, role) VALUES(45, 123, 'manager'); 
INSERT INTO Assigned(empID, projID, role) VALUES(89, 345, 'manager');     
INSERT INTO Assigned(empID, projID, role) VALUES(92, 123, 'engineer'); 



INSERT INTO SupplyType (SELECT DISTINCT typeID, typeDescription FROM SupplyData);

INSERT INTO Supply (SELECT  SupplyID,
                            supplyDescription, 
                            unitDescription,
                            costPerunit, 
                            typeID FROM SupplyData);

INSERT INTO ProjectSupply (SELECT 345, supplyID, 4 FROM Supply WHERE SupplyID < 104 );

INSERT INTO ProjectSupply (SELECT 123, supplyID, 4 FROM Supply WHERE SupplyID >= 104);






