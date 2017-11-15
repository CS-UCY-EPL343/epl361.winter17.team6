/*
 * Created by tomis on 14/11/2017.
 *
 * Server initiated at
 * http://localhost:4567
 * */
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;

import static spark.Spark.*;
public class App {

    public static void main(String args[]){
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        post("/hello", (req, res) -> {
            String userMessage = req.body();
            res.status(200);
            res.type("text/plain");

            return "Hello World. The message from the user is: " + userMessage;
        });

    }
}