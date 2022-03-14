package Models;

import org.sql2o.Sql2o;

public class Db {

//    Heroku Db Connect
//    static String connectionString = "jdbc:postgresql://ec2-3-216-221-31.compute-1.amazonaws.com:5432/daq0dss1uqjbpp"; //!
//    static Sql2o sql2o = new Sql2o(connectionString, "laynxhqaenrxke", "0e0e26df811d068f818734dcc3eb18a0ae91836ddf34c621d55f6a84e1fceb62"); //!

    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker","muriithi","123456");
}

