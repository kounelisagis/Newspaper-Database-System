CREATE DATABASE IF NOT EXISTS db1;
USE db1;


INSERT INTO publisher VALUES
  ('tegopoulos@upatras.gr', 'Christos', 'Tegopoulos'),
  ('kakaounakis@upatras.gr', 'Nikos', 'Kakaounakis'),
  ('kouris@upatras.gr', 'Giorgos', 'Kouris');


INSERT INTO worker VALUES
  ('st1059637@upatras.gr', 'Agisilaos', 'Kounelis', 1000, '1999-11-01'),
  ('st1059636@upatras.gr', 'Michael', 'Karavokuris', 1500, '2005-06-30'),
  ('st1059638@upatras.gr', 'Giannis', 'Koutsocheras', 700, '2015-06-30'),
  ('st1059629@upatras.gr', 'Makis', 'Papachronopoulos', 650, '2018-06-30');


INSERT INTO reporter VALUES 
  ('st1059636@upatras.gr', '6 months', 'KsaplopoulosAE'),
  ('st1059638@upatras.gr', '2 hours', 'FBI'),
  ('st1059629@upatras.gr', '10 years', 'Nasa');


INSERT INTO newspaper VALUES
  ('Eleftherotypia ', 'Kostas Tasoglou', 'daily', 'tegopoulos@upatras.gr', 'st1059636@upatras.gr'),
  ('Kathimerini', 'Nikos Mitropoulos', 'daily', 'kakaounakis@upatras.gr', 'st1059638@upatras.gr'),
  ('Bima', 'Tasos Garofalakis', 'weekly', 'kouris@upatras.gr', 'st1059629@upatras.gr');


INSERT INTO works VALUES
  ('Eleftherotypia', 'st1059637@upatras.gr'),
  ('Kathimerini', 'st1059636@upatras.gr'),
  ('Kathimerini', 'st1059638@upatras.gr'),
  ('Bima', 'st1059629@upatras.gr');


INSERT INTO page VALUES 
  (1, '2-4-2020', 7, 99999, 'Eleftherotypia', 'st1059638@upatras.gr'),
  (2, '1-4-2020', 5, 10000, 'Kathimerini', 'st1059636@upatras.gr'),
  (3, '2-4-2020', 5, 99999, 'Kathimerini', 'st1059629@upatras.gr'),
  (4, '2-4-2020', 3, 99999, 'Eleftherotypia', 'st1059629@upatras.gr');


INSERT INTO category VALUES
  (1, 'Athlitika', 'Ch League, Superleague', NULL),
  (2, 'Enhmerwshs', 'Epikairothta TWRA', NULL);
  

INSERT INTO article VALUES 
  ('C:\Users\PC\article1.doc', 'Article1', 1, 'Olympiakos', 'INITIAL', 1, 1, 'st1059636@upatras.gr'),
  ('C:\Users\PC\article2.doc', 'Article2', 2, 'Political Crisis', 'INITIAL', 1, 2, 'st1059638@upatras.gr'),
  ('C:\Users\PC\article3.doc', 'Article3', 1, 'National Elections', 'INITIAL', 2, 2, 'st1059629@upatras.gr');


INSERT INTO key_words VALUES
  ('C:\Users\PC\article1.doc', 'Christmas'),
  ('C:\Users\PC\article1.doc', 'New Year'),
  ('C:\Users\PC\article2.doc', 'word');

  
INSERT INTO administrator VALUES
  ('st1059637@upatras.gr', 'Manager', 'Favierou', 6, 'Patras, Greece');


INSERT INTO phone_numbers VALUES
  ('st1059637@upatras.gr', 6985986324);


INSERT INTO submission VALUES
  ('st1059636@upatras.gr', 'C:\Users\PC\article1.doc', '12-06-2019'),
  ('st1059638@upatras.gr', 'C:\Users\PC\article2.doc', '20-03-2018'),
  ('st1059629@upatras.gr', 'C:\Users\PC\article3.doc', '10-06-2012');
 