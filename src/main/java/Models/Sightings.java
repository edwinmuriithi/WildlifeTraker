package Models;

import java.sql.Timestamp;

import org.sql2o.*;

import java.util.List;


public class Sightings implements DatabaseManagement {

    private int id;
    private int animal_id;
    private String location;
    private String ranger_name;
    private Timestamp timestamp;

    // constructor for sighting which implements abstract method save in Database management class

    public Sightings(int animal_id, String location, String ranger_name) {
        if (ranger_name.equals("")) {
            throw new IllegalArgumentException("Please enter Ranger name.");
        }
        this.animal_id = animal_id;
        this.location = location;
        this.ranger_name = ranger_name;

        this.save();
    }
    //get methods
    public int getId(){
        return id;
    }

    public int getAnimalId(){
        return animal_id;
    }

    public String getLocation(){
        return location;
    }

    public String getRangerName(){
        return ranger_name;
    }

    public String getTimeSeen(){
        return String.format("%1$TD %1$TR", timestamp);
    }

    //set methods for Sightings
    public void setLocation(String location) {
        this.location = location;
    }

    public void setRangerName(String rangerName) {
        this.ranger_name = rangerName;
    }

    //Overriding save  method && implement method save() from Database management class

    @Override
    public void save() {
        String sql = "INSERT INTO sightings (animal_id, location, ranger_name, timestamp) VALUES (:animal_id, :location, :ranger_name, now());";
        System.out.println("INSERT INTO sightings (animal_id, location, ranger_name, timestamp) VALUES (:animal_id, :location, :ranger_name, now());");
        try (Connection con = Db.sql2o.open()) {
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("animal_id", this.animal_id)
                    .addParameter("location", this.location)
                    .addParameter("ranger_name", this.ranger_name)
                    .executeUpdate()
                    .getKey();
        }
    }

    //Listing all sightings from  sightings table
    public static List<Sightings> all() {
        String sql = "SELECT * FROM sightings ORDER BY timestamp DESC;";

        try (Connection con = Db.sql2o.open()) {
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sightings.class);
        }
    }

    //Listing sighting by animal id
    public static List<Sightings> allByAnimal(int animalId) {
        try(Connection con = Db.sql2o.open()) {
            String sql = "SELECT * FROM sightings WHERE animal_id = :animalId ORDER BY timestamp DESC";
            return con.createQuery(sql)
                    .addParameter("animalId", animalId)
                    .executeAndFetch(Sightings.class);
        }
    }

    //Overriding sighting
    public boolean equals(Object otherSighting){
        if(!(otherSighting instanceof Sightings)){
            return false;
        }else{
            Sightings newSighting = (Sightings) otherSighting;
            return this.getAnimalId()==newSighting.getAnimalId() && this.getRangerName().equals(newSighting.getRangerName());
        }
    }

    // finding a sighting using its id && with unchecked exception  that ensures index number entered by the user is within the range of the array.
    public static Sightings find(int id) {
        try(Connection con = Db.sql2o.open()) {
            String sql = "SELECT * FROM sightings WHERE id=:id;";
            Sightings sighting = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sightings.class);
            return sighting;
        } catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }

    //implement method delete() from Database management class
    public void delete(){
        try(Connection con = Db.sql2o.open()) {
            String sql = "DELETE FROM sightings WHERE id=:id;";
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }

    //update the Sightings table && throwing an exception incase the id is not mapped
    public void update() {
        String sql = "UPDATE sightings SET location = :location, ranger_name = :ranger_name WHERE id = :id";

        try(Connection con = Db.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("location", location)
                    .addParameter("rangername", ranger_name)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }

}