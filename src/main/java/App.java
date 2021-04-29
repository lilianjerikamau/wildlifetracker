//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import models.Animal;
//import models.Endangered;
//import models.Sql2oAnimalDao;
//import models.Sql2oEndangeredDao;
//import org.sql2o.Sql2o;
//import spark.ModelAndView;
//import spark.template.handlebars.HandlebarsTemplateEngine;
//import static spark.Spark.*;
//
//public class App {
//    public static void main(String[] args) { //type “psvm + tab” to autocreate this
//        staticFileLocation("/public");
//        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
//        Sql2oAnimalDao animalDao = new Sql2oAnimalDao(sql2o);
//        Sql2oEndangeredDao endangeredDao = new Sql2oEndangeredDao(sql2o);
//
//
//        //get: show all animals in all endangered and show all endangered
//        get("/", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Endangered> allEndangered = endangeredDao.getAll();
//            model.put("endangered", allEndangered);
//            List<Animal> animals = animalDao.getAll();
//            model.put("animals", animals);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: show a form to create a new endangered
//        get("/endangered/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Endangered> endangered = endangeredDao.getAll(); //refresh list of links for navbar
//            model.put("endangered", endangered);
//            return new ModelAndView(model, "endangered-form.hbs"); //new layout
//        }, new HandlebarsTemplateEngine());
//
//        //post: process a form to create a new endangered
//        post("/endangered", (req, res) -> { //new
//            Map<String, Object> model = new HashMap<>();
//            String name = req.queryParams("name");
//            String health = req.queryParams("health");
//            String age = req.queryParams("age");
//            Endangered newEndangered = new Endangered(name,health,age);
//            endangeredDao.add(newEndangered);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//
//        //get: delete all endangered and all animals
//        get("/endangered/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            endangeredDao.clearAllEndangered();
//            animalDao.clearAllAnimals();
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get: delete all animals
//        get("/animals/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            animalDao.clearAllAnimals();
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get a specific endangered (and the animals it contains)
//        get("/animals/:id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfEndangeredToFind = Integer.parseInt(req.params("id")); //new
//            Endangered foundEndangered = endangeredDao.findById(idOfEndangeredToFind);
//            model.put("endangered", foundEndangered);
//            List<Animal> allAnimalByEndangered = endangeredDao.getAllAnimalsByCategory(idOfEndangeredToFind);
//            model.put("animals", allAnimalByEndangered);
//            model.put("endangered", endangeredDao.getAll()); //refresh list of links for navbar
//            return new ModelAndView(model, "endangered-detail.hbs"); //new
//        }, new HandlebarsTemplateEngine());
//
//        //get: show a form to update a endangered
//        get("/endangered/:id/edit", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            model.put("editEndangered", true);
//            Endangered endangered = endangeredDao.findById(Integer.parseInt(req.params("id")));
//            model.put("endangered", endangered);
//            model.put("endangeredAnimals", endangeredDao.getAll()); //refresh list of links for navbar
//            return new ModelAndView(model, "endangered-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //post: process a form to update a endangered category
//        post("/endangered/:id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfEndangeredToEdit = Integer.parseInt(req.params("id"));
//            String newName = req.queryParams("newEndangeredName");
//            String newHealth = req.queryParams("newEndangeredHealth");
//            String newAge = req.queryParams("newEndangeredAge");
//            endangeredDao.update( idOfEndangeredToEdit, newName,newHealth,newAge);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get: delete an individual animal
//        get("/endangered/:endangeredId/animals/:animalId/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfAnimalToDelete = Integer.parseInt(req.params("id"));
//            animalDao.deleteById(idOfAnimalToDelete);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get: show new animal form
//        get("/animals/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Endangered> endangered = endangeredDao.getAll();
//            model.put("endangered", endangered);
//            return new ModelAndView(model, "animal-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //animal: process new animal form
//        post("/animals", (req, res) -> { //URL to make new task on POST route
//            Map<String, Object> model = new HashMap<>();
//            List<Endangered> allEndangered = endangeredDao.getAll();
//            model.put("endangered", allEndangered);
//            String name = req.queryParams("name");
//            int endangeredId = Integer.parseInt(req.queryParams("endangeredId"));
//            Animal newAnimal = new Animal(name, endangeredId);        //See what we did with the hard coded categoryId?
//            animalDao.add(newAnimal);
////            List<Animal> animalsSoFar = animalDao.getAll();
////            for (Animal taskItem: animalsSoFar
////                 ) {
////                System.out.println(taskItem);
////            }
//////            System.out.println(tasksSoFar);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get: show an individual animal that is nested in a endangered
//        get("/endangered/:endangeredId/animals/:animalId", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfAnimalToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
//            Animal foundAnimal = animalDao.findById(idOfAnimalToFind); //use it to find task
//            int idOfEndangeredToFind = Integer.parseInt(req.params("endangeredId"));
//            Endangered foundEndangered = endangeredDao.findById(idOfEndangeredToFind);
//            model.put("endangered", foundEndangered);
//            model.put("animal", foundAnimal); //add it to model for template to display
//            model.put("endangeredAnimals", endangeredDao.getAll()); //refresh list of links for navbar
//            return new ModelAndView(model, "animal-detail.hbs"); //individual task page.
//        }, new HandlebarsTemplateEngine());
//
//        //get: show a form to update an animal
//        get("/animals/:id/edit", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Endangered> allEndangered = endangeredDao.getAll();
//            model.put("endangeredAnimals", allEndangered);
//            Animal animal = animalDao.findById(Integer.parseInt(req.params("id")));
//            model.put("animal", animal);
//            model.put("editAnimal", true);
//            return new ModelAndView(model, "animal-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //animal: process a form to update a task
//        post("/animals/:id", (req, res) -> { //URL to update task on POST route
//            Map<String, Object> model = new HashMap<>();
//            int animalToEditId = Integer.parseInt(req.params("id"));
//            String newName = req.queryParams("name");
//            int newEndangeredId = Integer.parseInt(req.queryParams("endangeredId"));
//            animalDao.update(animalToEditId, newName, newEndangeredId);  // remember the hardcoded categoryId we placed? See what we've done to/with it?
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//    }
//}
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
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oAnimalDao taskDao = new Sql2oAnimalDao(sql2o);
        Sql2oEndangeredDao categoryDao = new Sql2oEndangeredDao(sql2o);
        Sql2oSightingDao sightingDao = new Sql2oSightingDao(sql2o);


        //get: show all tasks in all categories and show all categories
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Endangered> allCategories = categoryDao.getAll();
            model.put("categories", allCategories);
            List<Animal> tasks = taskDao.getAll();
            model.put("tasks", tasks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new category
        get("/categories/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Endangered> categories = categoryDao.getAll(); //refresh list of links for navbar
            model.put("categories", categories);
            return new ModelAndView(model, "endangered-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new category
        post("/categories", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String health = req.queryParams("health");
            String age = req.queryParams("age");
            Endangered newCategory = new Endangered(name,health,age);
            categoryDao.add(newCategory);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/sightings/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals = taskDao.getAll();
            model.put("animals", animals);
            return new ModelAndView(model, "sighting-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        post("/sightings", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String ranger = req.queryParams("ranger");
            String location = req.queryParams("location");
            int animalId = Integer.parseInt(req.params("animalId"));
            Sighting newSighting = new Sighting(ranger,location,animalId);
            sightingDao.add(newSighting);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all categories and all tasks
        get("/categories/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            categoryDao.clearAllEndangered();
            taskDao.clearAllAnimals();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all tasks
        get("/tasks/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            taskDao.clearAllAnimals();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: show a form to update a category
        get("/categories/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editCategory", true);
            Endangered category = categoryDao.findById(Integer.parseInt(req.params("id")));
            model.put("category", category);
            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "endangered-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a category
        post("/categories/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newName");
            String newHealth = req.queryParams("newHealth");
            String newAge = req.queryParams("newAge");
            categoryDao.update(idOfCategoryToEdit, newName,newHealth,newAge);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/categories/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("id"));
            categoryDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: delete an individual task
        get("/tasks/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("id"));
            taskDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new task form
        get("/tasks/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Endangered> categories = categoryDao.getAll();
            model.put("categories", categories);
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new task form
        post("/tasks", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            List<Endangered> allCategories = categoryDao.getAll();
            model.put("categories", allCategories);
            String name = req.queryParams("name");
            Animal newTask = new Animal(name);        //See what we did with the hard coded categoryId?
            taskDao.add(newTask);
//            List<Task> tasksSoFar = taskDao.getAll();
//            for (Task taskItem: tasksSoFar
//                 ) {
//                System.out.println(taskItem);
//            }
//            System.out.println(tasksSoFar);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual task that is nested in a category
        get("/tasks/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Animal foundTask = taskDao.findById(idOfTaskToFind); //use it to find task
            model.put("task", foundTask); //add it to model for template to display
            return new ModelAndView(model, "animal-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/tasks/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Animal task = taskDao.findById(Integer.parseInt(req.params("id")));
            model.put("task", task);
            model.put("editTask", true);
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a task
        post("/tasks/:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            int taskToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("name");
            taskDao.update(taskToEditId, newContent);  // remember the hardcoded categoryId we placed? See what we've done to/with it?
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());




        //post: process a form to create a new category

        //get: delete an individual task
        get("/sightings/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("sightingId"));
            sightingDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all categories and all tasks
        get("/sightings/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            sightingDao.clearAllSightings();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a category
        get("/sightings/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editSighting", true);
            Sighting category = sightingDao.findById(Integer.parseInt(req.params("sightingId")));
            model.put("category", category);
            model.put("categories", sightingDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "sighting-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a category
        post("/sightings/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToEdit = Integer.parseInt(req.params("sightingId"));
            String newRanger = req.queryParams("newRanger");
            String newLocation = req.queryParams("newLocation");
            int newAnimalId = Integer.parseInt(req.params("animalId"));
            sightingDao.update(idOfCategoryToEdit, newRanger,newLocation,newAnimalId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/sightings/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToFind = Integer.parseInt(req.params("id")); //new
            Sighting foundCategory = sightingDao.findById(idOfCategoryToFind);
            model.put("category", foundCategory);
            model.put("categories", sightingDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "sighting-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

    }
}