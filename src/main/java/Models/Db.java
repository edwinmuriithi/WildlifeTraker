package Models;

import org.sql2o.Sql2o;

public class Db {
    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker","muriithi","123456");
}
