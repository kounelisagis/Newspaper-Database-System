USE db1;


/* INSERTS */
INSERT INTO publisher
	(email, name, surname)
VALUES
	('mymail@upatras.gr', 'Ilias', 'Fountoulis'),
	('anothermail@upatras.gr', 'Kwstas', 'Mpalaroutsos');


INSERT INTO worker
	(email, name, surname, salary, recruitment_date)
VALUES
	('st1059637@upatras.gr', 'Agisilaos', 'Kounelis', 9999, '1999-11-01'),
	('st1059636@upatras.gr', 'Michael', 'Karavokyris', 5, '2005-06-30'),
	('st1059638@upatras.gr', 'Giannis', 'Koutsocheras', 500, '2015-06-30'),
	('st1059629@upatras.gr', 'Makis', 'Papachronopoulos', 50, '2018-06-30');


INSERT INTO reporter
	(email, work_experience, cv)
VALUES
	('st1059637@upatras.gr', 10, 'nasa'),
	('st1059636@upatras.gr', 1, 'makisAE'),
	('st1059638@upatras.gr', 2, 'fbi');


INSERT INTO newspaper
	(name, owner, frequency, publisher, chief_editor)
VALUES
	('Kathimerini', 'Mitsos Papazoglou', 'daily', 'mymail@upatras.gr', 'st1059637@upatras.gr'),
	('Katheminiea', 'George Belisariou', 'monthly', 'anothermail@upatras.gr', 'st1059636@upatras.gr');


INSERT INTO paper
	(publish_date, pages, copies, newspaper)
VALUES
	('2020-4-1', 6, 10000, 'Kathimerini'),
	('2020-4-1', 7, 99999, 'Katheminiea');


INSERT INTO category
	(id, name, description, my_category)
VALUES
	(1, 'Athlitika', 'Ch League, Superleague', NULL),
	(2, 'Enhmerwshs', 'Epikairothta TWRA', NULL);


INSERT INTO article
	(path, paper, title, description, category, num_of_pages, newspaper)
VALUES
	('C:\Users\PC\article1.doc', 1, 'Article1', 'Olympiakos', 1, 3.1, 'Kathimerini'),
	('C:\Users\PC\article2.doc', 1, 'Article2', 'Tsipras', 2, 1.9, 'Kathimerini');


INSERT INTO key_words
	(article, key_word)
VALUES
	('C:\Users\PC\article1.doc', 'key'),
	('C:\Users\PC\article2.doc', 'word');


INSERT INTO administrator
	(email, duty, street, number, city)
VALUES
	('st1059629@upatras.gr', 'ton paizei', 'plaz', 69, 'patras');


INSERT INTO phone_numbers
	(admin, number)
VALUES
	('st1059629@upatras.gr', 6988567445);


INSERT INTO works
	(newspaper, worker)
VALUES
	('Kathimerini', 'st1059636@upatras.gr'),
	('Kathimerini', 'st1059637@upatras.gr'),
	('Katheminiea', 'st1059638@upatras.gr'),
	('Katheminiea', 'st1059629@upatras.gr');


INSERT INTO submission
	(reporter, article, date)
VALUES
	('st1059637@upatras.gr', 'C:\Users\PC\article1.doc', '2019-06-12'),
	('st1059636@upatras.gr', 'C:\Users\PC\article2.doc', '2018-03-20');


/*
afterchecks

INSERT INTO article
	(path, paper, title, description, category, num_of_pages, newspaper)
VALUES
	('C:\Users\PC\article3333433.doc', 1, 'Article13434343', 'Olympiakos', 1, 0.9 ,'Kathimerini');


INSERT INTO submission
	(reporter, article, date)
VALUES
	('st1059638@upatras.gr', 'C:\Users\PC\article3333433.doc', '2020-02-02');


INSERT INTO article
	(path, paper, title, description, category, num_of_pages, newspaper)
VALUES
	('C:\Users\PC\oti8elw8akanw.doc', 1, 'My_article', 'Olympiakos', 1, 1.4, 'Kathimerini');


INSERT INTO submission
	(reporter, article, date)
VALUES
	('st1059637@upatras.gr', 'C:\Users\PC\oti8elw8akanw.doc', '2020-02-02');

*/
