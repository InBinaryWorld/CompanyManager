DROP DATABASE IF EXISTS CompanyManager;
CREATE DATABASE CompanyManager;

USE CompanyManager;

CREATE TABLE Persons (
	pesel char(11) NOT NULL UNIQUE, 
    `name` varchar(30) NOT NULL, 
    surname varchar(40) NOT NULL, 
	dateOfBirth DATETIME NOT NULL,
    PRIMARY KEY(PESEL));
    
CREATE TABLE Workers(
	id INT NOT NULL AUTO_INCREMENT,
	person char(11) NOT NULL, 
    profession varchar(60) NOT NULL, 
    salary int NOT NULL,
    beginDate DATETIME NOT NULL,
    endDate DATETIME,
    FOREIGN KEY(person) REFERENCES Persons(pesel), 
    PRIMARY KEY(id));
     
CREATE TABLE Portfolio(
	id INT AUTO_INCREMENT, 
    `change` int NOT NULL, 
    currentState int NOT NULL,
    changeDate DATETIME NOT NULL,
    title VARCHAR(100),
    PRIMARY KEY(id));
    
CREATE TABLE `Leaves`(
	id INT NOT NULL AUTO_INCREMENT, 
    worker int NOT NULL, 
    beginDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    FOREIGN KEY(worker) REFERENCES Workers(id), 
    PRIMARY KEY(id));
    
CREATE TABLE TransfersMade(
	id INT AUTO_INCREMENT, 
    transactionId int NOT NULL, 
    person char(11) NOT NULL, 
    amount int NOT NULL,
    FOREIGN KEY(transactionId) REFERENCES Portfolio(id), 
    FOREIGN KEY(person) REFERENCES Persons(pesel), 
    PRIMARY KEY(id));
    
    
CREATE TABLE SalaryHistory(
	id INT AUTO_INCREMENT, 
    oldSalary INT NOT NULL,
    newSalary INT NOT NULL, 
    person char(11) NOT NULL, 
    changeDate DATETIME NOT NULL,
    FOREIGN KEY(person) REFERENCES Persons(pesel), 
    PRIMARY KEY(id));
     
    
    
INSERT INTO portfolio VALUES(NULL,0,1000000,now(),'Initial budget');