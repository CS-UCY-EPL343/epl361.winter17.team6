/*
 * Created by tomis on 14/11/2017.
 *
 * Server initiated at
 * http://localhost:4567
 * */
import static spark.Spark.*;
public class App {

    public static void main(String args[]){

        get("/hello", (req, res) -> "Hello World");

    }
}