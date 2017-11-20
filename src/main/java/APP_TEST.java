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
public class APP_TEST {

    private ServiceCaller sc;

    APP_TEST() {
        sc = new ServiceCaller();
    }

    public String getChatbotResponse(String usrMsg) {


        TextParser tp = new TextParser(usrMsg);
        List<String> parsedUserMessage = tp.getKeyWords();
        String responceMessage = (new ResponseGenerator()).getResponce(tp.getKeyWords(),sc);
        System.out.println("Those are the ttextapp keywords: " + tp.getKeyWords());
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
//
//        post("/initiate", (req, res) -> {
//            app = new App();
//
//          return responseMsg;
//        });

        post("/hello", (req, res) -> {
            APP_TEST app = new APP_TEST();
            String usrMsg = req.body();




            String responseMsg = app.getChatbotResponse(usrMsg);
            res.status(200);
            res.type("text/html");
            return responseMsg ;
        });

    }
}