# Newspaper Database System

#### Team
Michail Karavokyris<br>
Agisilaos Kounelis

#### Technology Stack
JDK 13<br>
JavaFX<br>
MySQL

#### Run
```sh
mvn clean compile package javafx:run
```

# Relational Model
![Relational Model](https://raw.githubusercontent.com/kounelisagis/Newspaper-Database-System/master/_images/REL.png)

# Entity Relationship Diagram
![Entity Relationship Diagram](https://raw.githubusercontent.com/kounelisagis/Newspaper-Database-System/master/_images/ER.png)


#### Accounts

| email | password | Bcrypt Hash | Role |
| ------ | ------ | ------ | ------ |
IliasFood@upatras.gr | 1234 | $2y$12$7jYXT5pWl3wx4vdB5KldU.KUEMgDNTCESFsUnGS9sQr3ZHolo/HVO | Publisher
KonSpil@upatras.gr | abc | $2y$12$ZUqxd3v1zXXi0P86wofK9.5if7BDV9yUtBtPI1xSKbBzSoHkB8/xy | Publisher
st1059629@upatras.gr | mysql | $2y$12$GDrBslJslsA0IEXiaK3IBec88bGUGmmhwVvgzFG1yAcm9yyGT/Dku | Administrative
st1062268@upatras.gr | skype | $2y$12$4Hq3Rxc1vfqlSvRDp9QwdONV4h3ae3PwaEJWAzUY5YkSY6K3lQb2q | Administrative
st1059636@upatras.gr | java | $2y$12$6chGN2qSM9tkKewbq7ka2uO2L/zGlkAnr9vPhAUuACnHSDxDPSruK | Editor in Chief
st1059637@upatras.gr | patras | $2y$12$gCYNxnplf/Zj7EG4S6ga5uhFlDt6RWmRJq2sxJ8JdV.T.fXZiO7Ei | Editor in Chief
st1058768@upatras.gr | ceid | $2y$12$eK7Ug4PpmL5kOWa5aXCsIeDQEM2ef7x5ADNhBRGzpgOfzLDZJW5yy | Editor in Chief
st1059638@upatras.gr | news | $2y$12$cgFrvZkfCf/8n69KlXoBnO50CP7RWmAZP7iiHriLixIWN.GZsB4l2 | Editor in Chief
st14001@upatras.gr | CEID | $2y$12$fGYm7yKjDfkJk3gs1zREPOiKVIh8zdx5ooB1RcQl3HlkD8JlhgTOy | Journalist
st1074427@upatras.gr | jdbc | $2y$12$lLIhAus6SpOG2TWdBawNmetCXzuMvca4h1EnhSteycQoUyjTarnei | Journalist
