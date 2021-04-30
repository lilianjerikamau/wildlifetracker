
CREATE DATABASE wildlifetracker;
\c wildlifetracker;
CREATE TABLE animals (id SERIAL PRIMARY KEY, name VARCHAR, endangered BOOLEAN);
CREATE TABLE endangered (id SERIAL PRIMARY KEY, name VARCHAR,health VARCHAR,age VARCHAR);
CREATE TABLE locations (id SERIAL PRIMARY KEY, ranger VARCHAR,location VARCHAR,animalId int);
CREATE DATABASE wildlifetracker_test WITH TEMPLATE wildlifetracker;