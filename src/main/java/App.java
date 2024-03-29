import Models.Animal;
import Models.Endangered;
import Models.Sightings;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import sun.awt.X11.XSystemTrayPeer;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        String layout = "templates/layout.hbs";

        //home
        get("/",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());


        //Animals
        //navigate to animal creation form
        get("/animal/new",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"animal_form.hbs");
        },new HandlebarsTemplateEngine());

        // Posting animals form details
        post("/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            try {
                Animal animal = new Animal(name);
                animal.save();
            } catch (IllegalArgumentException exception) {
                System.out.println("Please enter an animal name.");
            }
            return new ModelAndView(model,"animal_form.hbs");
        }, new HandlebarsTemplateEngine());

        //View all animals
        get("/view/animals",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("animals",Animal.all());
            return new ModelAndView(model,"animal_view.hbs");
        },new HandlebarsTemplateEngine());


        //Endangered Animals
        //navigate to endangered animal creation form
        get("/endangered/new",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"endangered.hbs");
        },new HandlebarsTemplateEngine());

        //Post endangered
        post("/endangered/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            String health = request.queryParams("health");
            String age = request.queryParams("age");
            try {
                Endangered endangered = new Endangered(name, health, age);
                endangered.save();
            } catch (IllegalArgumentException exception) {
                System.out.println("Please enter all input fields.");
            }
            model.put("animals",Animal.all());
            return new ModelAndView(model,"animal_view.hbs");
        }, new HandlebarsTemplateEngine());


        //Sightings
        //navigate to Sighting form
        get("/sighting/new",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"sighting_form.hbs");
        },new HandlebarsTemplateEngine());

        //View all Sightings
        get("/view/sightings",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("sightings", Sightings.all());
            model.put("Animal", Animal.class);
            return new ModelAndView(model,"sighting_view.hbs");
        },new HandlebarsTemplateEngine());


        //Post Sightings form details
        post("/sighting/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String animal_id = request.queryParams("animal_id");
            System.out.println(animal_id);
            String location = request.queryParams("location");
            String ranger_name = request.queryParams("rangerName");

            try {
                Sightings sightings = new Sightings(animal_id, location, ranger_name);
                sightings.save();
            } catch (IllegalArgumentException exception) {
                System.out.println("Please enter Ranger name.");
            }
            return new ModelAndView(model,"sighting_form.hbs");
        }, new HandlebarsTemplateEngine());


    }}
