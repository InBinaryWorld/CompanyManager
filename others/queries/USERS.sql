DROP USER IF EXISTS 'Boss'@'localhost';
DROP USER IF EXISTS 'Manager'@'localhost';
DROP USER IF EXISTS 'Recruiter'@'localhost';
DROP USER IF EXISTS 'Accountant'@'localhost';

CREATE USER 'Boss'@'localhost' IDENTIFIED BY 'Boss';
GRANT ALL ON *.* TO 'Boss'@'localhost';


CREATE USER 'Manager'@'localhost' IDENTIFIED BY 'Manager';
GRANT EXECUTE ON PROCEDURE companymanager.changeSalary TO 'Manager'@'localhost';
GRANT EXECUTE ON PROCEDURE companymanager.releaseEmployee TO 'Manager'@'localhost';
GRANT EXECUTE ON PROCEDURE companymanager.addLeave TO 'Manager'@'localhost';
GRANT INSERT ON companymanager.workers TO 'Manager'@'localhost';
GRANT INSERT,UPDATE ON companymanager.persons TO 'Manager'@'localhost';
GRANT INSERT,SELECT ON companymanager.leaves TO 'Manager'@'localhost';
GRANT SELECT ON companymanager.persons TO 'Manager'@'localhost';
GRANT SELECT ON companymanager.workers TO 'Manager'@'localhost';
GRANT SELECT ON companymanager.salaryhistory TO 'Manager'@'localhost';

CREATE USER 'Recruiter'@'localhost' IDENTIFIED BY 'Recruiter';
GRANT INSERT,UPDATE ON companymanager.persons TO 'Recruiter'@'localhost';
GRANT INSERT ON companymanager.workers TO 'Recruiter'@'localhost';
GRANT SELECT ON companymanager.persons TO 'Recruiter'@'localhost';
GRANT SELECT ON companymanager.workers TO 'Recruiter'@'localhost';

CREATE USER 'Accountant'@'localhost' IDENTIFIED BY 'Accountant';
GRANT EXECUTE ON PROCEDURE companymanager.makeTransfer TO 'Accountant'@'localhost';
GRANT EXECUTE ON PROCEDURE companymanager.portfolioOperation TO 'Accountant'@'localhost';
GRANT SELECT ON companymanager.portfolio TO 'Accountant'@'localhost';
GRANT SELECT ON companymanager.transfersmade TO 'Accountant'@'localhost';
