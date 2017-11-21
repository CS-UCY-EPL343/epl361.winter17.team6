import org.json.JSONObject;

import java.util.UUID;

import static spark.Spark.*;


/**
 *
 * Created by tomis on 20/11/2017.
 */
public class Main {
    private static final boolean DEBUG = true;


    public static void main(String args[]) {
        final App app = new App();

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


        post("/init", (req, res) -> {
            res.type("application/json");
            String token = (UUID.randomUUID()).toString();
            app.addNewToken(token);
            if(DEBUG)
                System.out.println("New user logged in. Token created is\n\t " + token);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("token", token);

            return jsonResponse.toString();
        });

        post("/getmsg", (req, res) -> {
            res.type("application/json");
            res.status(200);

            String usrMsg = req.queryParams("usrmsg");
            String userToken = req.queryParams("token");
            if( DEBUG ) {
                System.out.println("The user message is " + usrMsg);
                System.out.println("The token is " + userToken);
            }

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("token", userToken);

            if(usrMsg == null || userToken == null ) {
                res.status(412);
                jsonResponse.put("error" , "required parameters are not set");
                return jsonResponse.toString();
            }
            String responseMsg = app.getChatbotResponse(usrMsg,userToken);
            jsonResponse.put("responsemsg", responseMsg);
            return jsonResponse.toString();
        });
    }
}
