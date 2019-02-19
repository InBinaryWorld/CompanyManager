DROP PROCEDURE IF EXISTS makeTransfer;
DROP PROCEDURE IF EXISTS changeSalary;
DROP PROCEDURE IF EXISTS releaseEmployee;
DROP PROCEDURE IF EXISTS portfolioOperation;
DROP PROCEDURE IF EXISTS addLeave;

DELIMITER //

CREATE PROCEDURE makeTransfer (IN professionI CHAR(60))
BEGIN
	DECLARE peselC CHAR(11);
    DECLARE salaryC FLOAT;
    DECLARE budget int;
    DECLARE transactionId INT;
	DECLARE cursor_done BOOLEAN DEFAULT FALSE;
    DECLARE cursorC CURSOR FOR SELECT person,salary FROM Workers WHERE profession = professionI;  
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_done = TRUE;
	SET autocommit = 0;
    SET budget = (SELECT currentState From portfolio ORDER BY id DESC LIMIT 1);
    OPEN cursorC;
	START TRANSACTION;
    INSERT INTO portfolio VALUES (NULL,0,budget,now(),concat('Salary for ',professionI));
	SET transactionId = (SELECT id From portfolio ORDER BY id DESC LIMIT 1);
    read_loop: LOOP
		FETCH  cursorC INTO peselC,salaryC;
        SET budget = budget - salaryC;
		IF cursor_done OR budget < 0 THEN
			CLOSE cursorC;
			LEAVE read_loop;
		END IF;
        UPDATE portfolio SET `change`= `change`- salaryC, currentState = currentState - salaryC WHERE id=transactionId;
        INSERT INTO transfersMade VALUES(NULL,transactionId,peselC,salaryC);
	END LOOP;
    IF budget < 0 or (SELECT `change` from portfolio where id=transactionId)=0 THEN
		ROLLBACK;
	ELSE
		COMMIT;
	END IF;
    SET autocommit = 1;
END;//

CREATE PROCEDURE changeSalary (IN pesel CHAR(11),IN workerID INT,IN newSalary INT)
BEGIN
	SET autocommit = 0;
	START TRANSACTION;
    INSERT INTO salaryHistory VALUES(NULL,(SELECT salary FROM workers WHERE id = workerID ),newSalary,pesel,now());
    UPDATE Workers SET salary=newSalary WHERE id=workerID;
	COMMIT;
    SET autocommit = 1;
END;//

CREATE PROCEDURE releaseEmployee (IN workerID CHAR(11))
BEGIN
	DECLARE datee DATETIME;
	SET datee = (SELECT endDate FROM workers WHERE person=workerID);
    IF datee >	now() OR datee IS NULL THEN
		UPDATE Workers SET endDate=now() WHERE person=workerID;
	END IF;
END;//

CREATE PROCEDURE portfolioOperation (IN changeI INT,IN title VARCHAR(100))
BEGIN
	DECLARE budget INT;
    SET budget =(SELECT currentState From portfolio ORDER BY id DESC LIMIT 1);
    INSERT INTO portfolio VALUES(NULL,changeI,budget+changeI,now(),title);
END;//

CREATE PROCEDURE addLeave  (IN id INT,IN beginn DATETIME,IN endd DATETIME )
BEGIN
    INSERT INTO leaves VALUES(NULL,id,beginn,date_add(endd,interval 1 day));
END;//


DELIMITER ;
