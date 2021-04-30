# WILDLIFETRACKER
This is a java application that allows Rangers to track wildlife sightings an  area.



## Author
[Lilian Kamau](https://github.com/lilianjerikamau/lilianjerikamau.github.io)

## Installing
After cloning to your local machine navigate to the folder you cloned into and open it with intellij.

Navigate into the src/main/java/App.java and click run in intellij.
Go to your browser and type localhost:4567
## Contributing

If you want to put out a pull request you first have to send us the sample code that you want to add to our repository for cross-checking before we allow the pull.
## Versioning

We use Github for versioning. This is the first version of this application

## Technologies

This project was generated with
* Java 
* Intellij Idea
* Spark
* Postgresql
##SQL
* Launch postgres
* Type in psql
* Run these commands
* CREATE DATABASE wildlifetracker;
* \c wildlifetracker;
 * CREATE TABLE animals (id SERIAL PRIMARY KEY, name VARCHAR, endangered BOOLEAN);
 *  CREATE TABLE endangered (id SERIAL PRIMARY KEY, name VARCHAR,health VARCHAR,age VARCHAR);
  * CREATE TABLE locations (id SERIAL PRIMARY KEY, ranger VARCHAR,location VARCHAR,animalId int);
  * CREATE DATABASE wildlifetracker_test WITH TEMPLATE wildlifetracker;


## Live Demo
https://heroesapp.herokuapp.com/squad

## License & copyright

[MIT](https://choosealicense.com/licenses/mit/) © [Lilian Kamau](https://github.com/lilianjerikamau/lilianjerikamau.github.io)



