import org.json.JSONObject;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static spark.Spark.*;


/**
 * Created by tomis on 20/11/2017.
 */
public class Main {
    private static final boolean DEBUG = true;
    private static final String DB_URL = "jdbc:sqlite:chatbot-db/chatbot-db.db";

    public static void main(String args[]) throws SQLException {
        final App app = new App();
        final SQLiteJDBCDriverConnection sqlDb = new SQLiteJDBCDriverConnection();

        try {
            sqlDb.connect(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            res.status(200);

            String username = req.queryParams("username");
            String init_timestamp = req.queryParams("timestamp");

            String token = null;
            JSONObject jsonResponse = new JSONObject();

            if( username == null || init_timestamp == null ) {
                res.status(412);
                jsonResponse.put("error", "required parameters are not set");
                return jsonResponse.toString();
            }
            Date conversationTimestamp = new Date(Long.parseLong(init_timestamp));
            if (DEBUG)
                System.out.println("New user logged in." +
                        "\n\tusername: " + username +
                        "\n\tconversation timestamp: " + conversationTimestamp +
                        "\n\tdate is: " + conversationTimestamp.toString());
            if (sqlDb.isUserInserted(username)) {
                token = sqlDb.getToken(username);
                if (DEBUG)
                    System.out.println("User" + username + " is already logged in the db." +
                            "\n\tThe token is retrieved from the DB" +
                            "\n\ttoken: " + token);
            } else {
                token = (UUID.randomUUID()).toString();
                sqlDb.insertUser(username,token);
                System.out.println("User " + username + " not yet in db." +
                        "\n\tThe token created is " +
                        "\n\ttoken: " + token);
            }
            app.addNewToken(token);
            jsonResponse.put("token", token);

            return jsonResponse.toString();
        });

        post("/getmsg", (req, res) -> {
            res.type("application/json");
            res.status(200);
            JSONObject jsonResponse = new JSONObject();

            String usrMsg = req.queryParams("usrmsg");
            String userToken = req.queryParams("token");
            String timestamp  = req.queryParams("timestamp");
            Date userMsgTimestamp = new Date(Long.parseLong("timestamp"));

            String username = sqlDb.getUsername("token");
            if (DEBUG)
                System.out.println("User : " + username + " send a new message at " +
                        "\n\ttoken:  " + userToken +
                        "\n\ttimestamp " + userMsgTimestamp +
                        "\n\tusrmsg: " + usrMsg );


            jsonResponse.put("token", userToken);

            if (usrMsg == null || userToken == null || timestamp == null) {
                res.status(412);
                jsonResponse.put("error", "required parameters are not set");
                return jsonResponse.toString();
            }

            String responseMsg = app.getChatbotResponse(usrMsg, userToken);
            jsonResponse.put("responsemsg", responseMsg);
            return jsonResponse.toString();
        });
    }
}
