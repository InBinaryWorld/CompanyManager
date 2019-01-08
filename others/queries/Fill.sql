DROP PROCEDURE IF EXISTS fill_Persons;
DROP PROCEDURE IF EXISTS fill_Workers;

DELIMITER //

CREATE PROCEDURE fill_Persons()
BEGIN
	DECLARE i INT;
    DECLARE dateV DATETIME;
	SET i = 0;
	WHILE i < 200 DO
		SET dateV = date_add(date_add(now(),interval - FLOOR(RAND()*(10)+22) year),interval FLOOR(RAND()*(365)) day);
		SET i = i + 1;
		INSERT INTO Persons VALUES (
        concat((SELECT DATE_FORMAT(dateV,'%y%m%d')), FLOOR(RAND()*(90000)+10000)),
        (SELECT first_name FROM sakila.actor ORDER BY RAND() LIMIT 1),
        (SELECT last_name FROM sakila.actor ORDER BY RAND() LIMIT 1),
        dateV);
	END WHILE;

END;//

CREATE PROCEDURE fill_Workers()
BEGIN

	DECLARE i INT;
    DECLARE peselC char(11);
	DECLARE cursor_done BOOLEAN DEFAULT FALSE;
    DECLARE cursorC CURSOR FOR SELECT pesel FROM Persons ORDER BY RAND();  
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_done = TRUE;
    OPEN cursorC;
	IF cursor_done THEN    	
		CLOSE cursorC;
	END IF;
    
	SET i = 0;
	WHILE i < 50 DO
		SET i = i + 1;
		FETCH  cursorC INTO peselC;
		INSERT INTO Workers VALUES (
        NULL,
        peselC,
        'AKTOR',
        FLOOR(RAND()*(9001)+1000),
        date_add(date_add(now(),interval -FLOOR(RAND()*(10)+11) year),interval FLOOR(RAND()*(365)) day),
        date_add(date_add(date_add(now(),interval -FLOOR(RAND()*(10)) year),interval FLOOR(RAND()*(365)) day),interval FLOOR(RAND()*(15)) year));
	END WHILE;
    
    SET i = 0;
	WHILE i < 33 DO
		SET i = i + 1;
		FETCH  cursorC INTO peselC;
		INSERT INTO Workers VALUES (
        NULL,
        peselC,
        'AGENT',
        FLOOR(RAND()*(4001)+1000),
        date_add(date_add(now(),interval -FLOOR(RAND()*(10)+11) year),interval FLOOR(RAND()*(365)) day),
        date_add(date_add(now(),interval -FLOOR(RAND()*(10)) year),interval FLOOR(RAND()*(365)) day));
	END WHILE;
    
    SET i = 0;
	WHILE i < 13 DO
		SET i = i + 1;
		FETCH  cursorC INTO peselC;
		INSERT INTO Workers VALUES (
        NULL,
        peselC,
        'INFORMATYK',
        FLOOR(RAND()*(10001)+5000),
        date_add(date_add(now(),interval -FLOOR(RAND()*(10)+11) year),interval FLOOR(RAND()*(365)) day),
        date_add(date_add(now(),interval -FLOOR(RAND()*(10)) year),interval FLOOR(RAND()*(365)) day));
	END WHILE;
    
    SET i = 0;
	WHILE i < 2 DO
		SET i = i + 1;
		FETCH  cursorC INTO peselC;
		INSERT INTO Workers VALUES (
        NULL,
        peselC,
        'REPORTER',
        FLOOR(RAND()*(8001)+2000),
        date_add(date_add(now(),interval -FLOOR(RAND()*(10)+11) year),interval FLOOR(RAND()*(365)) day),
        date_add(date_add(now(),interval -FLOOR(RAND()*(10)) year),interval FLOOR(RAND()*(365)) day));
	END WHILE;
    
    SET i = 0;
	WHILE i < 77 DO
		SET i = i + 1;
		FETCH  cursorC INTO peselC;
		INSERT INTO Workers VALUES (
        NULL,
        peselC,
        'SPRZEDAWCA',
        FLOOR(RAND()*(4001)+2000),
        date_add(date_add(now(),interval -FLOOR(RAND()*(10)+11) year),interval FLOOR(RAND()*(365)) day),
        date_add(date_add(now(),interval -FLOOR(RAND()*(10)) year),interval FLOOR(RAND()*(365)) day));
	END WHILE;
    
    CLOSE cursorC;
END;//

DELIMITER ;

CALL fill_Persons();
#CALL fill_Workers();