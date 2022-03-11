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

        //home
        get("/",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());

        //RANGERS
        //navigate to ranger creation form
        get("/create/ranger",(request, response) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            return new ModelAndView(model,"ranger_form.hbs");
        },new HandlebarsTemplateEngine());

    }}
