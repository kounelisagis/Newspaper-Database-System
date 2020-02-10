USE db1;


DROP PROCEDURE IF EXISTS PaperInfo;

DELIMITER $
CREATE PROCEDURE PaperInfo(IN paper_id INT, IN newspaper VARCHAR(128))
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
		WHERE works.worker = submission.journalist AND works.newspaper = newspaper
	)
	ORDER BY article.order_in_paper ASC;

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
			WHERE worker.email = submission.journalist AND submission.article = current_path;

			SET sum = sum + pages_needed;
		END IF;
	UNTIL(finishedFlag = 1)   
	END REPEAT;
	CLOSE bcursor;

	SELECT paper.pages INTO pages_available FROM paper WHERE paper.id = paper_id AND paper.newspaper = newspaper;

	IF (pages_available > sum) THEN  
		SET pages_available = pages_available - sum + 1;
		SELECT pages_available;
	END IF;
END$
DELIMITER ;


/*--------------------*/


DROP PROCEDURE IF EXISTS CalculateSalary;

DELIMITER $
CREATE PROCEDURE CalculateSalary(IN journalist_email VARCHAR(30), OUT new_salary FLOAT)
BEGIN
	DECLARE journalist_workexperience INT;
	DECLARE worker_recruitment_date DATE;
	DECLARE months_dif FLOAT;
	DECLARE total_months INT;
	
	SELECT journalist.work_experience INTO journalist_workexperience FROM journalist WHERE journalist.email = journalist_email;
	SELECT worker.recruitment_date INTO worker_recruitment_date FROM worker WHERE worker.email = journalist_email;

	SET months_dif = TIMESTAMPDIFF(MONTH, worker_recruitment_date, CURDATE());
	SET total_months = months_dif + journalist_workexperience;

	SET new_salary = 650 + (total_months * 0.05) * 650;
END$
DELIMITER ;


/*--------------------*/


DROP TRIGGER IF EXISTS SetSalary;

DELIMITER $
CREATE TRIGGER SetSalary BEFORE INSERT ON worker
FOR EACH ROW
BEGIN
	SET NEW.salary = 650;
END$
DELIMITER ;


/*--------------------*/


DROP TRIGGER IF EXISTS CheckForChief;

DELIMITER $
CREATE TRIGGER CheckForChief BEFORE INSERT ON submission
FOR EACH ROW
BEGIN	
	IF EXISTS (SELECT * FROM newspaper WHERE chief_editor = NEW.journalist) THEN
		UPDATE article
		SET state = 'ACCEPTED'
		WHERE path = NEW.article;
	END IF;
END$
DELIMITER ;


/*--------------------*/


DROP TRIGGER IF EXISTS CheckForPages;

DELIMITER $
CREATE TRIGGER CheckForPages BEFORE INSERT ON article
FOR EACH ROW
BEGIN
	DECLARE available_pages INT;
	DECLARE written_pages FLOAT;

	SELECT paper.pages INTO available_pages
	FROM paper
	WHERE NEW.paper = paper.id AND NEW.newspaper = paper.newspaper;

	SELECT SUM(article.num_of_pages) INTO written_pages
	FROM article
	WHERE NEW.paper = article.paper AND NEW.newspaper = article.newspaper;

	IF (written_pages + NEW.num_of_pages > available_pages) THEN
		SIGNAL SQLSTATE VALUE '45000'
		SET MESSAGE_TEXT = 'No space available';
	END IF;
END$
DELIMITER ;
