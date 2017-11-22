package com.chatbot.foody;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
        sqlDb.createTablesBasedOnSqlFile();
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
            System.out.println(init_timestamp);

            String token = null;
            boolean user_already_in_db = sqlDb.isUserInserted(username);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("user_already_in_db", user_already_in_db);
            String conversationId = null; UUID.randomUUID().toString();

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
            if (user_already_in_db) {
                token = sqlDb.getToken(username);
                conversationId = sqlDb.getConversationId(username);
                if (DEBUG)
                    System.out.println("User" + username + " is already logged in the db." +
                            "\n\tThe token is retrieved from the DB" +
                            "\n\ttoken: " + token +
                            "\n\tconvid: "+ conversationId );
                List<JSONObject> messagesRetrieved = sqlDb.getMessages(username,conversationId);
                jsonResponse.put("messages_retrieved", messagesRetrieved );
            } else {
                token = (UUID.randomUUID()).toString();
                conversationId =(UUID.randomUUID()).toString();
                sqlDb.insertUser(username,token);
                System.out.println("User " + username + " not yet in db." +
                        "\n\tThe token created is " +
                        "\n\ttoken: " + token +
                        "\n\tThe convid created is " +
                        "\n\tconvid: " +conversationId);
                sqlDb.insertConversation(username, conversationId, Long.parseLong(init_timestamp));
            }


            app.addNewToken(token);
            System.out.println("The conversation id that was created is: " + conversationId);
            jsonResponse.put("token", token);
            jsonResponse.put("convid", conversationId);

            return jsonResponse.toString();
        });

        post("/getmsg", (req, res) -> {
            res.type("application/json");
            res.status(200);
            JSONObject jsonResponse = new JSONObject();

            String usrMsg = req.queryParams("usrmsg");
            String msgToStore = req.queryParams("msgtostore");
            String userToken = req.queryParams("token");
            String timestamp  = req.queryParams("timestamp");
            String conversationId = req.queryParams("convid");

            if (usrMsg == null || userToken == null || timestamp == null) {
                res.status(412);
                jsonResponse.put("error", "required parameters are not set");
                return jsonResponse.toString();
            }

            Date userMsgTimestamp = new Date(Long.parseLong(timestamp));

            String username = sqlDb.getUsername(userToken);
            if (DEBUG)
                System.out.println("User (retrieved from db): " + username + " send a new message." +
                        "\n\ttoken:  " + userToken +
                        "\n\ttimestamp " + userMsgTimestamp +
                        "\n\tusrmsg: " + usrMsg  +
                        "\n\tmsgtostore: " + msgToStore);

            if (DEBUG)
                System.out.println("Check if conversationUser (retrieved from db): " + username + " send a new message." +
                        "\n\ttoken:  " + userToken +
                        "\n\ttimestamp " + userMsgTimestamp +
                        "\n\tusrmsg: " + usrMsg  +
                        "\n\tmsgtostore: " + msgToStore +
                        "\n\tconvid: " + conversationId);
            String userMessageId = UUID.randomUUID().toString();
            //store the message from the user to the database
            sqlDb.insertMessage(username, conversationId,userMessageId,Long.parseLong(timestamp), true, msgToStore);
            String responseMsg = app.getChatbotResponse(usrMsg, userToken);

            jsonResponse.put("token", userToken);
            jsonResponse.put("responsemsg", responseMsg);
            jsonResponse.put("usermsgid", userMessageId );

            return jsonResponse.toString();
        });
    post("/sendresph", (req, res) -> {
        res.type("application/json");
        res.status(200);
        JSONObject jsonResponse = new JSONObject();

        String token = req.queryParams("token");
        String responseMsg = req.queryParams("responsemsg");
        String conversationId = req.queryParams("convid");
        String timestamp = req.queryParams("timestamp");
        if(token== null || responseMsg == null|| conversationId == null|| timestamp == null) {
            res.status(412);
            jsonResponse.put("error", "required parameters are not set");
            return jsonResponse.toString();
        }
        String username = sqlDb.getUsername(token);
        //store the chatbot responce to the database
        String responseMessageId = UUID.randomUUID().toString();
        System.out.println("Inserting sendresponce to message " +
                "\n\tusername: " + username +
                "\n\tconvid :" +  conversationId +
                "\n\tresponsemsgid: " + responseMessageId +
                "\n\ttimestamp: " + Long.parseLong(timestamp) +
                "\n\tresponsemsg: " + responseMsg);
        sqlDb.insertMessage(username, conversationId,responseMessageId,Long.parseLong(timestamp), false, responseMsg);
        jsonResponse.put("success", "send message inserted to db");
        return jsonResponse;
    });
    }
}
