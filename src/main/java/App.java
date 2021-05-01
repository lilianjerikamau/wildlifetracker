
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;


public class App {
    public static void main(String[] args) { //type “psvm + tab” to autocreate this
        staticFileLocation("/public");
//        String connectionString = "jdbc:postgresql://localhost:5432/wildlifetracker"; //connect to todolist, not todolist_test!
//        Sql2o sql2o = new Sql2o(connectionString, "sherry", "password");
        String connectionString = "jdbc:postgresql://ec2-23-21-76-49.compute-1.amazonaws.com:5432/d5ke60mfrbhs04"; //!
        Sql2o sql2o = new Sql2o(connectionString, "rzzhsloqtcovkw", "348c62a0b9d3dff2a71a7f0f6efbf115e87371bc311b409d76d639beab236337");
        Sql2oAnimalDao animalDao = new Sql2oAnimalDao(sql2o);
        Sql2oEndangeredDao endangeredDao = new Sql2oEndangeredDao(sql2o);
        Sql2oSightingDao sightingDao = new Sql2oSightingDao(sql2o);


        //get: show all animals in all endangered and show all endangered
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Endangered> allCategories = endangeredDao.getAll();
            model.put("endangered", allCategories);
            List<Animal> animals = animalDao.getAll();
            model.put("animals", animals);
            List<Sighting> sightings = sightingDao.getAll();
            model.put("sightings", sightings);
            System.out.println(sightings);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new endangered
        get("/endangered/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Endangered> endangered = endangeredDao.getAll(); //refresh list of links for navbar
            model.put("endangered", endangered);
            return new ModelAndView(model, "endangered-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());
        post("/endangered/new", (req, res) -> { //URL to make new animal on POST route
            Map<String, Object> model = new HashMap<>();
            List<Endangered> allCategories = endangeredDao.getAll();
            model.put("endangered", allCategories);
            String name = req.queryParams("name");
            String health = req.queryParams("health");
            String age = req.queryParams("age");
            Endangered newEndangered = new Endangered(name, health, age);
            endangeredDao.add(newEndangered);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/sightings/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals = animalDao.getAll();
            model.put("animals", animals);
            return new ModelAndView(model, "sighting-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        post("/sightings", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String ranger = req.queryParams("ranger");
//            System.out.println(ranger);
            String location = req.queryParams("location");
//            System.out.println(location);
            int animalId = Integer.parseInt(req.queryParams("animalId"));
//            System.out.println(animalId);
            Sighting newSighting = new Sighting(ranger, location, animalId);
            sightingDao.add(newSighting);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/animals/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Endangered> endangered = endangeredDao.getAll();
            model.put("endangered", endangered);
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/animals", (req, res) -> { //URL to make new animal on POST route
            Map<String, Object> model = new HashMap<>();
            List<Endangered> allCategories = endangeredDao.getAll();
            model.put("endangered", allCategories);
            String name = req.queryParams("name");
//            System.out.println(name);
            Animal newTask = new Animal(name);
            animalDao.add(newTask);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        get("/endangered/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("id"));
            endangeredDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/endangered/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            endangeredDao.clearAllEndangered();
            animalDao.clearAllAnimals();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/animals/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            animalDao.clearAllAnimals();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/endangered/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editEndangered", true);
            Endangered endangered = endangeredDao.findById(Integer.parseInt(req.params("id")));
            model.put("endangered", endangered);
            model.put("endangeredAll", endangeredDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "endangered-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/endangered/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newName");
            System.out.println(newName);
            String newHealth = req.queryParams("newHealth");
            System.out.println(newHealth);
            String newAge = req.queryParams("newAge");
            System.out.println(newAge);
            endangeredDao.update(idOfCategoryToEdit, newName, newHealth, newAge);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        get("/animals/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Animal animal = animalDao.findById(Integer.parseInt(req.params("id")));
            model.put("animal", animal);
            model.put("editAnimal", true);
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/animals/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newName");
            System.out.println(newName);
            animalDao.update(idOfCategoryToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        get("/animals/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("id"));
            animalDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/sightings/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            sightingDao.clearAllSightings();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/animals/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Animal foundTask = animalDao.findById(idOfTaskToFind); //use it to find animal
            model.put("animal", foundTask); //add it to model for template to display
            return new ModelAndView(model, "animal-detail.hbs"); //individual animal page.
        }, new HandlebarsTemplateEngine());
        get("/endangered/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Endangered foundTask = endangeredDao.findById(idOfTaskToFind); //use it to find animal
            model.put("endangered", foundTask); //add it to model for template to display
            return new ModelAndView(model, "endangered-detail.hbs"); //individual animal page.
        }, new HandlebarsTemplateEngine());

        get("/sightings/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("id"));
            sightingDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/sightings/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToFind = Integer.parseInt(req.params("id")); //new
            Sighting foundCategory = sightingDao.findById(idOfCategoryToFind);
            model.put("endangered", foundCategory);
            model.put("endangeredAll", sightingDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "sighting-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

        get("/sightings/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editSighting", true);
            Sighting endangered = sightingDao.findById(Integer.parseInt(req.params("id")));
            model.put("sighting", endangered);
            model.put("endangeredAll", sightingDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "sighting-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/sightings/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToEdit = Integer.parseInt(req.params("id"));
            String newRanger = req.queryParams("newRanger");
            System.out.println(newRanger);
            String newLocation = req.queryParams("newLocation");
            System.out.println(newLocation);
            int newAnimalId = Integer.parseInt(req.queryParams("newAnimalId"));
            sightingDao.update(idOfCategoryToEdit, newRanger, newLocation, newAnimalId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }


}