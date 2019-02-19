DROP TRIGGER IF EXISTS persons_U;
DROP TRIGGER IF EXISTS persons_I;
DROP TRIGGER IF EXISTS workers_U;
DROP TRIGGER IF EXISTS workers_I;
DROP TRIGGER IF EXISTS transfersMade_U;
DROP TRIGGER IF EXISTS transfersMade_I;
DROP TRIGGER IF EXISTS salaryHistory_U;
DROP TRIGGER IF EXISTS salaryHistory_I;
DROP TRIGGER IF EXISTS portfolio_U;
DROP TRIGGER IF EXISTS portfolio_I;
DROP TRIGGER IF EXISTS leaves_U;
DROP TRIGGER IF EXISTS leaves_I;
    
DELIMITER //
CREATE TRIGGER persons_U
BEFORE UPDATE ON Persons
FOR EACH ROW BEGIN
	IF new.pesel NOT REGEXP '^[0-9]+$' OR char_length(new.pesel) != 11 THEN
		set @msg = "Wrong PESEL";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF new.name NOT REGEXP '^[a-zA-Z.]+$' THEN
		set @msg = "Wrong Name";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF new.surname NOT REGEXP '^[a-zA-Z.]+$' THEN
		set @msg = "Wrong Surname";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
     IF timestampdiff(day,new.dateOfBirth,curdate()) < 0 THEN
    set @msg = "Wrong date of birth";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
END;//

CREATE TRIGGER persons_I
BEFORE INSERT ON Persons
FOR EACH ROW BEGIN
	IF new.pesel NOT REGEXP '^[0-9]+$' OR char_length(new.pesel) != 11 THEN
		set @msg = "Wrong PESEL";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF new.name NOT REGEXP '^[a-zA-Z.]+$' THEN
		set @msg = "Wrong Name";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF new.surname NOT REGEXP '^[a-zA-Z.]+$' THEN
		set @msg = "Wrong Surname";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
     IF timestampdiff(day,new.dateOfBirth,curdate()) < 0 THEN
    set @msg = "Wrong date of birth";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
END;//

CREATE TRIGGER workers_I 
BEFORE INSERT ON Workers 
FOR EACH ROW BEGIN

     IF new.endDate IS NOT NULL AND timestampdiff(day,new.beginDate,new.endDate)< 0 THEN
    set @msg = "Wrong date";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF new.salary < 0 THEN
    set @msg = "Wrong salary";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF timestampdiff(day,(SELECT dateOfBirth FROM Persons WHERE pesel = new.person),new.beginDate) < 18 THEN
    set @msg = "Person is underage";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF (new.beginDate < now()) then
		set @msg = "wrong date";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
    END IF;
END;//

CREATE TRIGGER workers_U 
BEFORE UPDATE ON Workers 
FOR EACH ROW BEGIN

     IF new.endDate IS NOT NULL AND timestampdiff(day,new.beginDate,new.endDate)< 0 THEN
    set @msg = "Wrong date";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF new.salary < 0 THEN
    set @msg = "Wrong salary";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF timestampdiff(day,(SELECT dateOfBirth FROM Persons WHERE pesel = new.person),new.beginDate) < 18 THEN
    set @msg = "Person is underage";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
END;//


CREATE TRIGGER transfersMade_I
BEFORE INSERT ON TransfersMade 
FOR EACH ROW BEGIN
    IF new.amount <= 0 THEN
    set @msg = "Wrong amount";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
END;//


CREATE TRIGGER transfersMade_U
BEFORE UPDATE ON TransfersMade 
FOR EACH ROW BEGIN
    IF new.amount <= 0 THEN
    set @msg = "Wrong amount";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
END;//


CREATE TRIGGER salaryHistory_U
BEFORE UPDATE ON SalaryHistory 
FOR EACH ROW BEGIN
    IF new.newSalary <= 0 THEN
    set @msg = "Wrong salary";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
END;//


CREATE TRIGGER salaryHistory_I
BEFORE INSERT ON SalaryHistory 
FOR EACH ROW BEGIN
    IF new.newSalary <= 0 THEN
    set @msg = "Wrong salary";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
END;//


CREATE TRIGGER portfolio_I
BEFORE INSERT ON Portfolio 
FOR EACH ROW BEGIN
    IF new.currentState < 0 THEN
    set @msg = "Wrong salary";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
END;//



CREATE TRIGGER portfolio_U
BEFORE UPDATE ON Portfolio 
FOR EACH ROW BEGIN
    IF new.currentState < 0 THEN
    set @msg = "Insufficient funds on the account";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
END;//


CREATE TRIGGER leaves_U
BEFORE UPDATE ON Leaves 
FOR EACH ROW BEGIN
    IF timestampdiff(day,new.beginDate,new.endDate) < 0 THEN
    set @msg = "Wrong date";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF ((SELECT ifnull((days)+timestampdiff(day,new.beginDate,new.endDate),timestampdiff(day,new.beginDate,new.endDate)) AS days  FROM
	(SELECT timestampdiff(day,beginDate,endDate) AS days FROM Leaves WHERE Leaves.worker = new.worker)A)
		> (SELECT CAST(sum(months)*26/12 AS UNSIGNED) FROM 
	(SELECT timestampdiff(month,beginDate,IFNULL(endDate,curdate())) AS months FROM Workers WHERE Workers.person = 
		(SELECT person FROM workers WHERE id=new.worker) )A)) THEN
		set @msg = "Can not take a leave";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
    END IF;
    IF (select count(*) FROM (SELECT beginDate,endDate FROM Leaves WHERE Leaves.worker = new.worker) A 
    WHERE new.beginDate <= A.beginDate and new.endDate >= A.beginDate 
		or new.beginDate <= A.endDate and new.endDate >= A.endDate 
			or A.beginDate <= new.beginDate and A.endDate>=new.endDAte)!=0 then
		set @msg = "wrong date";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
    END IF;
    IF (new.beginDate < now()) then
		set @msg = "wrong date";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
    END IF;
    if (SELECT count(*) FROM 
	(SELECT beginDate,endDate FROM Workers WHERE Workers.person = 
		(SELECT person FROM workers WHERE id=new.worker) )A where curdate() BETWEEN beginDate and endDate or beginDate <= curdate() and endDate is null)>0 THEN
        set @msg = "Employer is not working here anymore";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
    end if;
END;//

CREATE TRIGGER leaves_I
BEFORE INSERT ON Leaves 
FOR EACH ROW BEGIN
    IF timestampdiff(day,new.beginDate,new.endDate) < 0 THEN
    set @msg = "Wrong date";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
	END IF;
    IF ((SELECT ifnull(sum(days)+timestampdiff(day,new.beginDate,new.endDate),timestampdiff(day,new.beginDate,new.endDate)) AS days  FROM
	(SELECT timestampdiff(day,beginDate,endDate) AS days FROM Leaves WHERE Leaves.worker = new.worker)A)
		> (SELECT CAST(sum(months)*26/12 AS UNSIGNED) FROM 
	(SELECT timestampdiff(month,beginDate,IFNULL(endDate,curdate())) AS months FROM Workers WHERE Workers.person = 
		(SELECT person FROM workers WHERE id=new.worker) )A)) THEN
		set @msg = "Can not take a leave";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
    END IF;
    IF (select count(*) FROM (SELECT beginDate,endDate FROM Leaves WHERE Leaves.worker = new.worker) A 
    WHERE new.beginDate <= A.beginDate and new.endDate >= A.beginDate 
		or new.beginDate <= A.endDate and new.endDate >= A.endDate 
			or A.beginDate <= new.beginDate and A.endDate>=new.endDAte)!=0 then
		set @msg = "wrong date";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
    END IF;
    IF (new.beginDate < now()) then
		set @msg = "wrong date";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
    END IF;
    if (SELECT count(*) FROM 
	(SELECT beginDate,endDate FROM Workers WHERE Workers.person = 
		(SELECT person FROM workers WHERE id=new.worker) )A where curdate() BETWEEN A.beginDate and A.endDate or beginDate <= curdate() and endDate is null)=0 THEN
        set @msg = "Employer is not working here anymore";
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @msg;
    end if;
END;//




DELIMITER ;
