USE db1;

/* INSERTS */
INSERT INTO publisher VALUES
  ('mymail@upatras.gr', 'Ilias', 'Fountoulis'),
  ('anothermail@upatras.gr', 'Kwstas', 'Mpalaroutsos');


INSERT INTO worker VALUES
  ('st1059637@upatras.gr', 'Agisilaos', 'Kounelis', 99999, '1999-11-01'),
  ('st1059636@upatras.gr', 'Michael', 'Karavokyris', 5, '2005-06-30'),
  ('st1059638@upatras.gr', 'Giannis', 'Koutsocheras', 500, '2015-06-30'),
  ('st1059629@upatras.gr', 'Makis', 'Papachronopoulos', 50, '2018-06-30');


INSERT INTO reporter VALUES 
  ('st1059637@upatras.gr', 10, 'nasa'),
  ('st1059636@upatras.gr', 1, 'makisAE'),
  ('st1059638@upatras.gr', 2, 'fbi');


INSERT INTO newspaper VALUES
  ('Kathimerini', 'Mitsos Papazoglou', 'daily', 'mymail@upatras.gr', 'st1059637@upatras.gr'),
  ('Katheminiea', 'George Belisariou', 'monthly', 'anothermail@upatras.gr', 'st1059636@upatras.gr');


INSERT INTO page VALUES 
  (1, '2020-4-1', 5, 10000, 'Kathimerini'),
  (2, '2020-4-1', 7, 99999, 'Katheminiea');


INSERT INTO category VALUES
  (1, 'Athlitika', 'Ch League, Superleague', NULL),
  (2, 'Enhmerwshs', 'Epikairothta TWRA', NULL);
  

INSERT INTO article VALUES 
  ('C:\Users\PC\article1.doc', 'Article1', 1, 'Olympiakos', 'INITIAL','2020-3-1', 1, 1, 3),
  ('C:\Users\PC\article2.doc', 'Article2', 2, 'Tsipras', 'ACCEPTED','2020-3-1', 1, 2, 2);

INSERT INTO key_words VALUES
  ('C:\Users\PC\article1.doc', 'key'),
  ('C:\Users\PC\article2.doc', 'word');

  
INSERT INTO administrator VALUES
  ('st1059629@upatras.gr', 'ton paizei', 'plaz', 69, 'patras');


INSERT INTO phone_numbers VALUES
  ('st1059629@upatras.gr', 6988567445);


INSERT INTO works VALUES
  ('Kathimerini', 'st1059636@upatras.gr'),
  ('Kathimerini', 'st1059637@upatras.gr'),
  ('Katheminiea', 'st1059638@upatras.gr'),
  ('Katheminiea', 'st1059629@upatras.gr');


INSERT INTO submission VALUES
  ('st1059637@upatras.gr', 'C:\Users\PC\article1.doc', '2019-06-12'),
  ('st1059636@upatras.gr', 'C:\Users\PC\article2.doc', '2018-03-20');

/*
afterakia

INSERT INTO article VALUES 
  ('C:\Users\PC\article3333433.doc', 'Article13434343', 3, 'Olympiakos', 'INITIAL','2020-02-01', 1, 1, 1.4);

INSERT INTO submission VALUES
  ('st1059638@upatras.gr', 'C:\Users\PC\article3333433.doc', '2020-02-02');

INSERT INTO article VALUES 
  ('C:\Users\PC\oti8elw8akanw.doc', 'oti8elw', 4, 'Olympiakos', 'INITIAL','2020-02-01', 1, 1, 0.9);

INSERT INTO submission VALUES
  ('st1059637@upatras.gr', 'C:\Users\PC\oti8elw8akanw.doc', '2020-02-02');
*/
