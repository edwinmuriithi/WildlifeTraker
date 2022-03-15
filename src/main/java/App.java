import Models.Animal;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

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


        //RANGERS
        //navigate to ranger creation form
        get("/ranger/new",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"ranger_form.hbs");
        },new HandlebarsTemplateEngine());


        //Animals
        //navigate to animal creation form
        get("/animal/new",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"animal_form.hbs");
        },new HandlebarsTemplateEngine());
        // posting animals form details
        post("/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            try {
                Animal animal = new Animal(name);
                animal.save();
            } catch (IllegalArgumentException exception) {
                System.out.println("Please enter an animal name.");
            }
//            response.redirect("/animals");
            return new ModelAndView(model,"animal_form.hbs");
        }, new HandlebarsTemplateEngine());

        get("/view/animals",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("animals",Animal.all());
            return new ModelAndView(model,"animal_view.hbs");
        },new HandlebarsTemplateEngine());

        //Sightings
        get("/view/sightings",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("animals",Animal.all());
            return new ModelAndView(model,"sighting_view.hbs");
        },new HandlebarsTemplateEngine());

        //Endangered Animals
        //navigate to endangered animal creation form
        get("/endangered/new",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"endangered.hbs");
        },new HandlebarsTemplateEngine());

        //navigate to Sighting form
        get("/sighting/new",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"sighting_form.hbs");
        },new HandlebarsTemplateEngine());



//        post("/create/animal/new",(request, response) -> {
//            Map<String,Object> model=new HashMap<String, Object>();
//            String type=request.queryParams("type");
//            System.out.println(type);
//            String health=request.queryParams("health");
//            System.out.println(health);
//            String age=request.queryParams("age");
//            System.out.println(age);
//            String name=request.queryParams("name");
//            System.out.println(name);
//            if(type.equals(EndangeredAnimals.ANIMAL_TYPE)){
//                EndangeredAnimals endangered=new EndangeredAnimals(name,EndangeredAnimals.ANIMAL_TYPE,health,age);
//                endangered.save();
//            }
//            else {
//                Animals animal=new Animals(name,Animals.ANIMAL_TYPE);
//                animal.save();
//            }
//
//            return new ModelAndView(model,"animal-form.hbs");
//        },new HandlebarsTemplateEngine());


    }}
