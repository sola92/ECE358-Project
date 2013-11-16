/* Lab 3 -part 3 - Creating a view */

USE ece356db_vcoste; 



/* Add some more rows to the Project table(needed for example below) */

INSERT INTO Project(projID, title, budget, funds) VALUES(645, 'Medical Center Construction', 10000000, 2500000);
INSERT INTO Project(projID, title, budget, funds) VALUES(623, '2013 Conference', 6500, 3000);
INSERT INTO Project(projID, title, budget, funds) VALUES(624, '2014 Conference', 7500, 7500);

DROP VIEW IF EXISTS
    ProjectLowBudget;

CREATE VIEW ProjectLowBudget AS 
SELECT projID,title, budget  FROM Project WHERE budget <=500000;

DROP VIEW IF EXISTS
    EmpDepView;
CREATE VIEW EmpDepView AS 
SELECT Department.deptID, deptName, location, empID, empName, job FROM Employee NATURAL JOIN Department;


/* Query using this view */

SELECT * FROM ProjectLowBudget WHERE title like "%Conference%";

SELECT * FROM EmpDepView;

SELECT * FROM EmpDepView WHERE deptID = 3;

SELECT * FROM EmpDepView NATURAL JOIN (Select * from Assigned NATURAL JOIN Project) x;









