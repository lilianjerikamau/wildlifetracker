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
    BOOLEAN,
    endangeredId int,
);
CREATE TABLE IF NOT EXISTS endangered (
                                          id int PRIMARY KEY auto_increment,
                                          name VARCHAR,
                                          health VARCHAR,
                                          age int

);