USE db1;


INSERT INTO publisher
	(email, name, surname)
VALUES
	('IliasFood@upatras.gr', 'Ilias', 'Fountoulis'),
	('KonSpil@upatras.gr', 'Kostastantinos', 'Spiliotakopoulos');
	 

INSERT INTO worker
	(email, name, surname, recruitment_date)
VALUES
	('st1059637@upatras.gr', 'Agisilaos', 'Kounelis', '1999-11-01'),
	('st1059636@upatras.gr', 'Michael', 'Karavokyris', '2005-06-30'),
	('st1059638@upatras.gr', 'Giannis', 'Koutsocheras', '2015-06-30'),
	('st1059629@upatras.gr', 'Makis', 'Papachronopoulos', '2018-06-30'),
	('st1058768@upatras.gr', 'Vasiliki', 'Tsimpouki', '2017-10-3'),
	('st14001@upatras.gr', 'George', 'Kourtis','2019-10-1'),
	('st1062268@upatras.gr','Alexis', 'Giannoutsos', '2006-08-16'),
    ('st1074427@upatras.gr','Panagiotis','Mixalopoulos','2020-01-4');
    
INSERT INTO journalist
	(email, work_experience, cv)
VALUES
	('st1059637@upatras.gr', 10, 'Google firefox'),
	('st1059636@upatras.gr', 102, 'Oscars'),
	('st1059638@upatras.gr', 2, 'Copernicus'),
	('st1058768@upatras.gr', '26', 'Architecture Design'),
	('st14001@upatras.gr', 17,'Artist'),
    ('st1074427@upatras.gr',1,'Doctor');
INSERT INTO newspaper
	(name, owner, frequency, publisher, chief_editor)
VALUES
	('Real News','Ilias Fountoulis', 'daily', 'IliasFood@upatras.gr', 'st1058768@upatras.gr'),
	('Kathimerini', 'Ilias Fountoulis', 'weekly', 'IliasFood@upatras.gr', 'st1059637@upatras.gr'),
	('Eleftherotipia', 'Kostastantinos Spiliotakopoulos', 'monthly', 'KonSpil@upatras.gr', 'st1059636@upatras.gr');

INSERT INTO paper
	(publish_date, pages, copies, newspaper)
VALUES
	('2020-4-1', 26, 10000, 'Kathimerini'),
	('2020-4-1', 15, 20000, 'Kathimerini'),
	('2020-4-1', 17, 15000, 'Eleftherotipia'),
	('2020-4-9', 25, 20000, 'Real News');

INSERT INTO category
	(name, description, my_category)
VALUES
	('Arts', 'Paintings, ...', NULL),
	('Champions League', 'Messiiii', NULL),
	('Sports', 'Ballaaa', 'Champions League');
	

INSERT INTO article
	(path, paper, title, description, category, num_of_pages, newspaper)
VALUES
	('C:\Users\PC\article1.doc', 1, 'Article1', 'Olympiakos', 'Sports', 3, 'Kathimerini'),
	('C:\Users\PC\maaaaarticle.doc', 2, 'Maaaarticle', 'Coronavirus', 'Arts', 2, 'Kathimerini'),
	('C:\Users\PC\AAAAA.doc', 2, 'BBB', 'CCCCC', 'Arts', 3, 'Kathimerini'),
	('C:\Users\PC\444545.doc', 2, '4777', 'NNNGHGT', 'Sports', 3, 'Kathimerini'),
	('C:\Users\PC\article3.doc', 1, 'Article3', 'Mitsokatis', 'Arts', 2, 'Kathimerini'),
    ('C:\Users\PC\article4.doc', 1, 'Article4', 'test', 'Arts', 2, 'Kathimerini');
    
    INSERT INTO submission
	(journalist, article, date)
VALUES
	('st1059637@upatras.gr', 'C:\Users\PC\article1.doc', '2019-06-12'),
    ('st1059638@upatras.gr', 'C:\Users\PC\article1.doc','2019-06-12'),
	('st1059638@upatras.gr', 'C:\Users\PC\maaaaarticle.doc', '2020-01-4'),
	('st1059638@upatras.gr', 'C:\Users\PC\AAAAA.doc', '2020-02-4'),
	('st1059638@upatras.gr', 'C:\Users\PC\444545.doc', '2020-01-2'),
	('st1059638@upatras.gr', 'C:\Users\PC\article3.doc', '2020-01-1'),
	('st1059638@upatras.gr', 'C:\Users\PC\article4.doc', '2020-01-3');
    
INSERT INTO key_words
	(article, key_word)
VALUES
	('C:\Users\PC\article1.doc', 'key'),
	('C:\Users\PC\article3.doc', 'key');

INSERT INTO administrative
	(email, duty, street, number, city)
VALUES
	('st1059629@upatras.gr', 'admin', 'Plaz', 69, 'patras'),
	('st1062268@upatras.gr','admin', 'Panachaiki', 54, 'patras');

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


/*
afterchecks
INSERT INTO article
	(path, paper, title, description, category, num_of_pages, newspaper)
VALUES
	('C:\Users\PC\article3333433.doc', 1, 'Article13434343', 'Olympiakos', 'Sports', 1 ,'Kathimerini');
INSERT INTO submission
	(journalist, article, date)
VALUES
	('st1059638@upatras.gr', 'C:\Users\PC\article3333433.doc', '2020-02-02');
INSERT INTO article
	(path, paper, title, description, category, num_of_pages, newspaper)
VALUES
	('C:\Users\PC\oti8elw8akanw.doc', 1, 'My_article', 'Olympiakos', 'Sports', 1, 'Kathimerini');
INSERT INTO submission
	(journalist, article, date)
VALUES
	('st1059637@upatras.gr', 'C:\Users\PC\oti8elw8akanw.doc', '2020-02-02');
*/