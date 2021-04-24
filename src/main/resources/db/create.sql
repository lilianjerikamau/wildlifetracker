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