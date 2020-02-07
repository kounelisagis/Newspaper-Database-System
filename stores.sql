
/* 3c */

DROP TRIGGER IF EXISTS setSalary;

DELIMITER $
CREATE TRIGGER setSalary BEFORE INSERT ON worker
FOR EACH ROW
BEGIN
	SET NEW.salary = 650;
END$
DELIMITER ;

/* 3d */

DROP TRIGGER IF EXISTS checkForChief;

DELIMITER $
CREATE TRIGGER checkForChief BEFORE INSERT ON submission
FOR EACH ROW
BEGIN
	DECLARE sameEmailCount INT;
	SET sameEmailCount = 0;
	
	SELECT COUNT(*) INTO sameEmailCount 
	FROM newspaper
	WHERE email = NEW.reporter;	

	IF(sameEmailCount>0) THEN
		UPDATE article
		SET state = 'ACCEPTED'
		WHERE path = NEW.article;
	END IF;
END$
DELIMITER ;
