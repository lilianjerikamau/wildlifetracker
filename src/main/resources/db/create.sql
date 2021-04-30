SET MODE Postgresql;

CREATE TABLE IF NOT EXISTS animals
(
    id
    int
    PRIMARY
    KEY
    auto_increment,
    name
    VARCHAR,
    endangered
    BOOLEAN
);
CREATE TABLE IF NOT EXISTS endangered (
                                          id int PRIMARY KEY auto_increment,
                                          name VARCHAR,
                                          health VARCHAR,
                                          age VARCHAR

);

CREATE TABLE IF NOT EXISTS locations (
                                         id  int NOT NULL PRIMARY KEY auto_increment,
                                         ranger VARCHAR,
                                         location VARCHAR,
                                         animalId int
);