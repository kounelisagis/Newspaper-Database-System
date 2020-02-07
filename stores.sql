USE db1;

DROP PROCEDURE IF EXISTS paperSummary;
DELIMITER $
CREATE PROCEDURE paperSummary(IN pageid INT, IN newspapername VARCHAR(128))
BEGIN
	DECLARE finishedFlag INT;
    DECLARE current_path VARCHAR(128);
    DECLARE sum FLOAT DEFAULT 1.0;
	DECLARE pages_available FLOAT;
	DECLARE current_title VARCHAR(128);
	DECLARE checkdate DATE;
	DECLARE pages_needed FLOAT;


	/* Each select fetches path, title, pages_needed */
    DECLARE bcursor CURSOR FOR
	SELECT article.path, article.title, article.num_of_pages, article.checkdate FROM article
	WHERE article.state = 'ACCEPTED' AND article.path in (
		SELECT submission.article FROM works, submission
		WHERE works.worker = submission.reporter AND works.newspaper = newspapername
	)
    ORDER BY article.num_in_page ASC;

    DECLARE CONTINUE HANDLER FOR NOT FOUND
	SET finishedFlag = 1;

	SET finishedFlag = 0;

    OPEN bcursor;
    REPEAT
		FETCH bcursor INTO current_path, current_title, pages_needed, checkdate;
        IF(finishedFlag = 0)
        THEN

			SELECT current_title as title, worker.name, worker.surname, checkdate, FLOOR(sum) as start_page, pages_needed
			FROM worker, submission
			WHERE worker.email = submission.reporter AND submission.article = current_path;

			SET sum = sum + pages_needed;
        END IF;
    UNTIL(finishedFlag = 1)   
	END REPEAT;
    CLOSE bcursor;

	SELECT page.pages INTO pages_available FROM page WHERE page.id = pageid;

    IF (pages_available > sum) THEN  
		SET pages_available = pages_available - sum + 1;
		SELECT pages_available;
    END IF;

END$
DELIMITER ;



/*--------------------*/



DROP PROCEDURE IF EXISTS calculate_salary;
DELIMITER $
CREATE PROCEDURE calculateSalary(IN reporter_email VARCHAR(30), OUT new_salary FLOAT)
BEGIN
	DECLARE reporter_workexperience INT;
	DECLARE worker_startdate DATE;
    DECLARE months_dif FLOAT;
    DECLARE total_months INT;
	
	SELECT reporter.work_before_startdate INTO reporter_workexperience FROM reporter WHERE reporter.email = reporter_email;
	SELECT worker.startdate INTO worker_startdate FROM worker WHERE worker.email = reporter_email;

    SET months_dif = FLOOR(DATEDIFF(CURDATE(), worker_startdate)/30.0);
    SET total_months = months_dif + reporter_workexperience;

	SET new_salary = 650 + (total_months * 0.05) * 650;

END$
DELIMITER ;


/*--------------------*/


DROP TRIGGER IF EXISTS setSalary;
DELIMITER $
CREATE TRIGGER setSalary BEFORE INSERT ON worker
FOR EACH ROW
BEGIN
	SET NEW.salary = 650;
END$
DELIMITER ;


/*--------------------*/


DROP TRIGGER IF EXISTS checkForChief;

DELIMITER $
CREATE TRIGGER checkForChief BEFORE INSERT ON submission
FOR EACH ROW
BEGIN	
	IF EXISTS (SELECT * FROM newspaper WHERE chief_editor = NEW.reporter) THEN
		UPDATE article
		SET state = 'ACCEPTED'
		WHERE path = NEW.article;
	END IF;

END$
DELIMITER ;



DROP TRIGGER IF EXISTS checkForPages;

DELIMITER $
CREATE TRIGGER checkForPages BEFORE INSERT ON article
FOR EACH ROW
BEGIN	
	/*IF EXISTS (SELECT * FROM newspaper WHERE chief_editor = NEW.reporter) THEN
		UPDATE article
		SET state = 'ACCEPTED'
		WHERE path = NEW.article;
	END IF;*/
	DECLARE @returnvalue INT
	EXEC @returnvalue = SP_One

END$
DELIMITER ;

