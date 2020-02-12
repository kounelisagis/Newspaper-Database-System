USE db1;


DROP PROCEDURE IF EXISTS PaperInfo;

DELIMITER $
CREATE PROCEDURE PaperInfo(IN paper_id INT, IN newspaper VARCHAR(128))
BEGIN
	DECLARE finishedFlag INT;
	DECLARE current_path VARCHAR(128);
	DECLARE sum INT DEFAULT 1;
	DECLARE pages_available INT;
	DECLARE current_title VARCHAR(128);
	DECLARE check_date DATE;
	DECLARE pages_needed INT;


	/* Each select fetches path, title, pages_needed */
	DECLARE bcursor CURSOR FOR
	SELECT article.path, article.title, article.num_of_pages, article.check_date FROM article
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
		FETCH bcursor INTO current_path, current_title, pages_needed, check_date;
		IF(finishedFlag = 0)
		THEN

			SELECT current_title as title, worker.name, worker.surname, check_date, sum as start_page, pages_needed
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
CREATE PROCEDURE CalculateSalary(IN journalist_email VARCHAR(128), IN curr_date DATE, OUT new_salary FLOAT)
BEGIN
	DECLARE journalist_workexperience INT;
	DECLARE worker_recruitment_date DATE;
	DECLARE months_dif INT;
	DECLARE total_months INT;
	
	SELECT journalist.work_experience INTO journalist_workexperience FROM journalist WHERE journalist.email = journalist_email;
	SELECT worker.recruitment_date INTO worker_recruitment_date FROM worker WHERE worker.email = journalist_email;

	SET months_dif = TIMESTAMPDIFF(MONTH, worker_recruitment_date, curr_date);
	IF months_dif <= 0 THEN
		SET new_salary = 0;
	ELSE
		SET total_months = months_dif + journalist_workexperience;
		SET new_salary = 650 + (total_months * 0.05) * 650;
	END IF;
END$
DELIMITER ;


/*--------------------*/


DROP PROCEDURE IF EXISTS CalculateFinancialData;

DELIMITER $
CREATE PROCEDURE CalculateFinancialData(IN months INT, OUT total_sum FLOAT)
BEGIN
	DECLARE current_email VARCHAR(128);
	DECLARE current_sum INT;
	DECLARE current_salary FLOAT;
	DECLARE currentDate DATE;
	DECLARE current_recruitment_date DATE;
	DECLARE finishedFlag INT;

	DECLARE bcursor CURSOR FOR
	SELECT worker.email, worker.recruitment_date FROM journalist LEFT JOIN worker ON journalist.email = worker.email;

	DECLARE CONTINUE HANDLER FOR NOT FOUND
	SET finishedFlag = 1;
	
	
	SET total_sum = 0;
	
	SET finishedFlag = 0;
	OPEN bcursor;
	REPEAT
		SET current_sum = 0;
		FETCH bcursor INTO current_email, current_recruitment_date;
		IF(finishedFlag = 0) THEN
			SET currentDate = DATE_SUB(CURDATE(), INTERVAL months MONTH);
			IF currentDate < current_recruitment_date THEN
				SET currentDate = current_recruitment_date;
			END IF;
			WHILE currentDate < CURDATE() DO
				CALL CalculateSalary(current_email, currentDate, current_salary);
				SET current_sum = current_sum + current_salary;
				SET currentDate = DATE_ADD(currentDate, INTERVAL 1 MONTH);
			END WHILE;
			SELECT current_email, current_sum;
			SET total_sum = total_sum + current_sum;
		END IF;
	UNTIL(finishedFlag = 1)
	END REPEAT;
	CLOSE bcursor;
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


DROP TRIGGER IF EXISTS SetPaperId;

DELIMITER $
CREATE TRIGGER SetPaperId BEFORE INSERT ON paper
FOR EACH ROW
BEGIN
	DECLARE paper_max_id INT;
	
	SELECT MAX(id) INTO paper_max_id
	FROM paper
	WHERE newspaper = NEW.newspaper;
	
	IF (paper_max_id IS NULL) THEN
		SET paper_max_id = 0;
	END IF;
	
	SET NEW.id = paper_max_id + 1;
END$
DELIMITER ;


/*--------------------*/


DROP TRIGGER IF EXISTS CheckForChief;

DELIMITER $
CREATE TRIGGER CheckForChief BEFORE INSERT ON submission
FOR EACH ROW
BEGIN
	DECLARE paper_max INT;
	DECLARE newspaper_name VARCHAR(128);
	DECLARE paper_id VARCHAR(128);

	IF EXISTS (SELECT * FROM newspaper WHERE chief_editor = NEW.journalist) THEN
	
		SELECT name INTO newspaper_name FROM newspaper WHERE chief_editor = NEW.journalist;
		
		SELECT paper INTO paper_id
		FROM article
		WHERE path = NEW.article;
	
		SELECT MAX(article.order_in_paper) INTO paper_max
		FROM article
		WHERE paper_id = article.paper AND newspaper_name = article.newspaper;
		
		IF (paper_max IS NULL) THEN
			SET paper_max = 0;
		END IF;

		UPDATE article
		SET state = 'ACCEPTED', order_in_paper =  paper_max + 1, check_date = CURDATE()
		WHERE path = NEW.article;
	END IF;
END$
DELIMITER ;


/*--------------------*/


DROP TRIGGER IF EXISTS CheckForPagesInsert;

DELIMITER $
CREATE TRIGGER CheckForPagesInsert BEFORE INSERT ON article
FOR EACH ROW
BEGIN
	DECLARE available_pages INT;
	DECLARE accepted_pages INT;

	SELECT paper.pages INTO available_pages
	FROM paper
	WHERE NEW.paper = paper.id AND NEW.newspaper = paper.newspaper;

	SELECT SUM(article.num_of_pages) INTO accepted_pages
	FROM article
	WHERE NEW.paper = article.paper AND NEW.newspaper = article.newspaper AND article.state = "ACCEPTED";
		
	IF (accepted_pages + NEW.num_of_pages > available_pages) THEN
		SIGNAL SQLSTATE VALUE '45000'
		SET MESSAGE_TEXT = 'No space available';
	END IF;
END$
DELIMITER ;


/*--------------------*/


DROP TRIGGER IF EXISTS CheckForPagesUpdate;

DELIMITER $
CREATE TRIGGER CheckForPagesUpdate BEFORE UPDATE ON article
FOR EACH ROW
BEGIN
	DECLARE available_pages INT;
	DECLARE accepted_pages INT;
	DECLARE paper_max INT;
	
	SELECT paper.pages INTO available_pages
	FROM paper
	WHERE NEW.paper = paper.id AND NEW.newspaper = paper.newspaper;

	SELECT SUM(article.num_of_pages) INTO accepted_pages
	FROM article
	WHERE NEW.paper = article.paper AND NEW.newspaper = article.newspaper AND article.state = "ACCEPTED";
	
	IF (accepted_pages IS NULL) THEN
		SET accepted_pages = 0;
	END IF;

	
	IF (OLD.state = "INITIAL" AND NEW.state = "ACCEPTED" AND accepted_pages + NEW.num_of_pages <= available_pages) THEN
		SELECT MAX(article.order_in_paper) INTO paper_max
		FROM article
		WHERE NEW.paper = article.paper AND NEW.newspaper = article.newspaper;
		
		IF (paper_max IS NULL) THEN
			SET paper_max = 0;
		END IF;
				
		SET NEW.order_in_paper = paper_max + 1;
		
		SET NEW.check_date = CURDATE();

	ELSEIF (OLD.state = "INITIAL" AND NEW.state = "ACCEPTED" AND accepted_pages + NEW.num_of_pages > available_pages) THEN
		SET NEW.state = "CHANGES_NEEDED";
		SET NEW.chief_comments = "You need to reduce the number of pages!";
	ELSEIF (NEW.state = "INITIAL" AND accepted_pages + NEW.num_of_pages > available_pages) THEN
		SIGNAL SQLSTATE VALUE '45000'
		SET MESSAGE_TEXT = 'No space available';
	END IF;
END$
DELIMITER ;

