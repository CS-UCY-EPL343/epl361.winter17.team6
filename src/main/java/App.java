/*
 * Created by tomis on 14/11/2017.
 *
 * Server initiated at
 * http://localhost:4567
 * */
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import static spark.Spark.*;
public class App {

    private ServiceCaller sc;

    App() {
        sc = new ServiceCaller();
    }

    public String getChatbotResponse(String usrMsg) {
        List<String> s = new ArrayList<String>();
        s.add("souvlakia");

        TextParser tp = new TextParser(usrMsg);
        List<String> parsedUserMessage = tp.getKeyWords();
        String responceMessage = (new ResponseGenerator()).getResponce(s, ResponseGenerator.LIST_RESTAURANT, sc);

        return responceMessage;
    }

    public static void main(String args[]){

        int sessionNum = 0;
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

//        post("/initiate", (req, res) -> {
//
//            app = new App();
//
//           return responseMsg;
//        });

        post("/hello", (req, res) -> {
            App app = new App();
            String usrMsg = req.body();
            String responseMsg = app.getChatbotResponse(usrMsg);
            res.status(200);
            res.type("text/plain");
            return responseMsg ;
        });

    }
}