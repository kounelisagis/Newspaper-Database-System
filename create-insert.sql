/* CREATE */

CREATE DATABASE IF NOT EXISTS db1;
USE db1;


CREATE TABLE IF NOT EXISTS publisher (
	email VARCHAR(128) NOT NULL,
	password BINARY(60) NOT NULL,
	name VARCHAR(128) NOT NULL,
	surname VARCHAR(128) NOT NULL,
	PRIMARY KEY(email)
);


CREATE TABLE IF NOT EXISTS worker (
	email VARCHAR(128) NOT NULL,
	password BINARY(60) NOT NULL,
	name VARCHAR(128) NOT NULL,
	surname VARCHAR(128) NOT NULL,
	salary FLOAT NOT NULL,
	recruitment_date DATE NOT NULL,
	PRIMARY KEY(email)
);


CREATE TABLE IF NOT EXISTS journalist (
	email VARCHAR(128) NOT NULL,
	work_experience INT NOT NULL, /* number of months */
	cv VARCHAR(128) NOT NULL,
	PRIMARY KEY(email),
	CONSTRAINT CON0 FOREIGN KEY (email) REFERENCES worker(email)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS newspaper (
	name VARCHAR(128) NOT NULL,
	owner VARCHAR(128) NOT NULL,
	frequency ENUM('daily', 'weekly', 'monthly') NOT NULL,
	publisher VARCHAR(128) NOT NULL,
	chief_editor VARCHAR(128) NOT NULL,
	PRIMARY KEY(name),
	CONSTRAINT CON1 FOREIGN KEY (publisher) REFERENCES publisher(email)
	ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT CON2 FOREIGN KEY (chief_editor) REFERENCES journalist(email)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS paper (
	id INT NOT NULL,
	publish_date DATE NOT NULL,
	pages INT DEFAULT 30 NOT NULL,
	copies INT NOT NULL,
	copies_returned INT DEFAULT NULL,
	newspaper VARCHAR(128) NOT NULL,
	PRIMARY KEY(id, newspaper),
	CONSTRAINT CON3 FOREIGN KEY (newspaper) REFERENCES newspaper(name)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS category (
	name VARCHAR(128) NOT NULL,
	description VARCHAR(128) NOT NULL,
	my_category VARCHAR(128),
	PRIMARY KEY(name),
	CONSTRAINT CON4 FOREIGN KEY (my_category) REFERENCES category(name)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS article (
	path VARCHAR(128) NOT NULL,
	title VARCHAR(128) NOT NULL,
	order_in_paper INT,
	description VARCHAR(128) NOT NULL,
	state ENUM('INITIAL', 'ACCEPTED', 'REJECTED', 'CHANGES_NEEDED') DEFAULT 'INITIAL' NOT NULL,
	check_date DATE DEFAULT NULL,
	paper INT DEFAULT NULL,
	category VARCHAR(128) DEFAULT NULL,
	num_of_pages INT NOT NULL,
	newspaper VARCHAR(128) NOT NULL,
    comments VARCHAR(128),
	PRIMARY KEY(path),
	CONSTRAINT CON5 FOREIGN KEY (paper) REFERENCES paper(id)
	ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT CON6 FOREIGN KEY (category) REFERENCES category(name)
	ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT CON7 FOREIGN KEY (newspaper) REFERENCES newspaper(name)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS key_words (
	article VARCHAR(128) NOT NULL,
	key_word VARCHAR(128) NOT NULL,
	PRIMARY KEY(article, key_word),
	CONSTRAINT CON8 FOREIGN KEY (article) REFERENCES article(path)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS administrative (
	email VARCHAR(128) NOT NULL,
	duty ENUM('Secretary', 'Logistics') DEFAULT 'Secretary' NOT NULL,
	street VARCHAR(128) NOT NULL,
	number INT NOT NULL,
	city VARCHAR(128) NOT NULL,
	PRIMARY KEY(email),
	CONSTRAINT CON9 FOREIGN KEY (email) REFERENCES worker(email)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS phone_numbers (
	admin VARCHAR(128) NOT NULL,
	number BIGINT NOT NULL,
	PRIMARY KEY(admin, number),
	CONSTRAINT CON10 FOREIGN KEY (admin) REFERENCES administrative(email)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS works (
	newspaper VARCHAR(128) NOT NULL,
	worker VARCHAR(128) NOT NULL,
	PRIMARY KEY(newspaper, worker),
	CONSTRAINT CON11 FOREIGN KEY (newspaper) REFERENCES newspaper(name)
	ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT CON12 FOREIGN KEY (worker) REFERENCES worker(email)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS submission (
	journalist VARCHAR(128) NOT NULL,
	article VARCHAR(128) NOT NULL,
	date DATE NOT NULL,
	PRIMARY KEY(journalist, article),
	CONSTRAINT CON13 FOREIGN KEY (journalist) REFERENCES journalist(email)
	ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT CON14 FOREIGN KEY (article) REFERENCES article(path)
	ON DELETE CASCADE ON UPDATE CASCADE
);


/* INSERTS */

INSERT INTO publisher
	(email, password, name, surname)
VALUES
	('IliasFood@upatras.gr', '$2y$12$7jYXT5pWl3wx4vdB5KldU.KUEMgDNTCESFsUnGS9sQr3ZHolo/HVO', 'Ilias', 'Fountoulis'),
	('KonSpil@upatras.gr', '$2y$12$ZUqxd3v1zXXi0P86wofK9.5if7BDV9yUtBtPI1xSKbBzSoHkB8/xy', 'Kostastantinos', 'Spiliotakopoulos');
	 

INSERT INTO worker
	(email, password, name, surname, recruitment_date)
VALUES
	('st1059637@upatras.gr', '$2y$12$gCYNxnplf/Zj7EG4S6ga5uhFlDt6RWmRJq2sxJ8JdV.T.fXZiO7Ei', 'Agisilaos', 'Kounelis', '1999-11-01'),
	('st1059636@upatras.gr', '$2y$12$6chGN2qSM9tkKewbq7ka2uO2L/zGlkAnr9vPhAUuACnHSDxDPSruK', 'Michael', 'Karavokyris', '2005-06-30'),
	('st1059638@upatras.gr', '$2y$12$cgFrvZkfCf/8n69KlXoBnO50CP7RWmAZP7iiHriLixIWN.GZsB4l2', 'Giannis', 'Koutsocheras', '2015-06-30'),
	('st1059629@upatras.gr', '$2y$12$GDrBslJslsA0IEXiaK3IBec88bGUGmmhwVvgzFG1yAcm9yyGT/Dku', 'Makis', 'Papachronopoulos', '2018-06-30'),
	('st1058768@upatras.gr', '$2y$12$eK7Ug4PpmL5kOWa5aXCsIeDQEM2ef7x5ADNhBRGzpgOfzLDZJW5yy', 'Vasiliki', 'Tsimpouki', '2017-10-3'),
	('st14001@upatras.gr', '$2y$12$fGYm7yKjDfkJk3gs1zREPOiKVIh8zdx5ooB1RcQl3HlkD8JlhgTOy', 'George', 'Kourtis','2019-10-1'),
	('st1062268@upatras.gr', '$2y$12$4Hq3Rxc1vfqlSvRDp9QwdONV4h3ae3PwaEJWAzUY5YkSY6K3lQb2q', 'Alexis', 'Giannoutsos', '2006-08-16'),
    ('st1074427@upatras.gr', '$2y$12$lLIhAus6SpOG2TWdBawNmetCXzuMvca4h1EnhSteycQoUyjTarnei', 'Panagiotis','Mixalopoulos','2020-01-4');
    
    
INSERT INTO journalist
	(email, work_experience, cv)
VALUES
	('st1059637@upatras.gr', 10, 'Google firefox'),
	('st1059636@upatras.gr', 102, 'Oscars'),
	('st1059638@upatras.gr', 2, 'Copernicus'),
	('st1058768@upatras.gr', '26', 'Architecture Design'),
	('st14001@upatras.gr', 17, 'Artist'),
    ('st1074427@upatras.gr',1, 'Doctor');
    
    
INSERT INTO newspaper
	(name, owner, frequency, publisher, chief_editor)
VALUES
	('Real News','Ilias Fountoulis', 'daily', 'IliasFood@upatras.gr', 'st1058768@upatras.gr'),
	('Kathimerini', 'Ilias Fountoulis', 'weekly', 'IliasFood@upatras.gr', 'st1059637@upatras.gr'),
	('Eleftherotipia', 'Kostastantinos Spiliotakopoulos', 'monthly', 'KonSpil@upatras.gr', 'st1059636@upatras.gr');
    

INSERT INTO paper
	(publish_date, pages, copies, newspaper)
VALUES
	('2020-03-01', 15, 10000, 'Kathimerini'),
	('2020-03-07', 20, 20000, 'Kathimerini'),
	('2020-01-01', 18, 20000, 'Real News'),
    ('2020-03-09', 24, 26000, 'Real News'),
    ('2020-03-14', 30, 20000, 'Eleftherotipia');


INSERT INTO category
	(name, description, my_category)
VALUES
	('General', 'World News', NULL),
	('Events', 'Now', NULL),
	('Style', 'Fashion', NULL),
	('Arts', 'Paintings', 'Style'),
	('Sports', 'Winners', NULL),
    ('Soccer', 'Top scorers', 'Sports'),
   	('Champions League', 'The best of Europe', 'Soccer');


INSERT INTO article
	(path, paper, title, description, category, num_of_pages, newspaper)
VALUES
	('C:\\Users\\PC\\coronavirus.doc', 1, 'Coronavirus', '1500 deaths', 'General', 1, 'Kathimerini'),
	('C:\\Users\\PC\\brexit.doc', 2, 'Brexit', 'Britain exits Europe', 'General', 2, 'Kathimerini'),
	('C:\\Users\\PC\\mwc.doc', 2, 'MWC Cancelled', 'Mobile World Congress', 'Events', 3, 'Kathimerini'),
	('C:\\Users\\PC\\tsitsipas.doc', 1, 'Tsitsipas beats Djokovic', 'Great Win', 'Sports', 4, 'Kathimerini'),
	('C:\\Users\\PC\\ny_galleries.doc', 1, 'New York Art Galleries', 'Artistic Movement', 'Arts', 5, 'Kathimerini'),
    ('C:\\Users\\PC\\acropolis.doc', 1, 'Acropolis Museum', 'Free entrance during the weekend', 'Arts', 2, 'Eleftherotipia'),
    ('C:\\Users\\PC\\carnival.doc', 1, 'Carnival Group Travel', 'Patras Carnival', 'Events', 5, 'Eleftherotipia'),
    ('C:\\Users\\PC\\messi.doc', 1, 'Messi hat trick', 'Barcelona', 'Champions League', 3, 'Eleftherotipia'),
    ('C:\\Users\\PC\\designers.doc', 1, 'Emerging Designers', '2020', 'Style', 3, 'Real News'),
    ('C:\\Users\\PC\\redbull.doc', 2, 'Red Bull Homerun', 'Snowboard', 'Sports', 5, 'Real News'),
    ('C:\\Users\\PC\\oil.doc', 2, 'Oil Paintings', 'Linseed oil, poppy seed oil', 'Arts', 7, 'Real News');
    
    
    INSERT INTO submission
	(journalist, article, date)
VALUES
	('st1059638@upatras.gr', 'C:\\Users\\PC\\coronavirus.doc', '2019-06-12'),
    ('st1059638@upatras.gr', 'C:\\Users\\PC\\brexit.doc','2019-10-23'),
	('st1059638@upatras.gr', 'C:\\Users\\PC\\mwc.doc', '2020-01-04'),
	('st1059637@upatras.gr', 'C:\\Users\\PC\\mwc.doc', '2020-02-04'),
	('st1059638@upatras.gr', 'C:\\Users\\PC\\tsitsipas.doc', '2020-02-06'),
	('st1059637@upatras.gr', 'C:\\Users\\PC\\ny_galleries.doc', '2020-02-06'),
	('st14001@upatras.gr', 'C:\\Users\\PC\\acropolis.doc', '2019-10-02'),
    ('st14001@upatras.gr', 'C:\\Users\\PC\\carnival.doc', '2020-02-12'),
    ('st1059636@upatras.gr', 'C:\\Users\\PC\\messi.doc', '2020-01-30'),
    ('st1074427@upatras.gr', 'C:\\Users\\PC\\designers.doc', '2020-01-5'),
    ('st1058768@upatras.gr', 'C:\\Users\\PC\\redbull.doc', '2019-12-12'),
    ('st1074427@upatras.gr', 'C:\\Users\\PC\\oil.doc', '2020-02-10');
    
    
INSERT INTO key_words
	(article, key_word)
VALUES
	('C:\\Users\\PC\\coronavirus.doc', 'key'),
	('C:\\Users\\PC\\brexit.doc', 'word');
    

INSERT INTO administrative
	(email, duty, street, number, city)
VALUES
	('st1059629@upatras.gr', 'Secretary', 'Plaz', 69, 'Patras'),
	('st1062268@upatras.gr', 'Logistics', 'Panachaiki', 54, 'Patras');
    

INSERT INTO phone_numbers
	(admin, number)
VALUES
	('st1059629@upatras.gr', 6988567445),
	('st1062268@upatras.gr', 6944236666);
    

INSERT INTO works
	(newspaper, worker)
VALUES
	('Eleftherotipia', 'st1059636@upatras.gr'),
    ('Eleftherotipia', 'st1059629@upatras.gr'),
    ('Eleftherotipia','st14001@upatras.gr'),
	('Kathimerini', 'st1059637@upatras.gr'),
	('Kathimerini', 'st1059638@upatras.gr'),
    ('Kathimerini', 'st1062268@upatras.gr'),
	('Real News', 'st1074427@upatras.gr'),
    ('Real News', 'st1058768@upatras.gr');
