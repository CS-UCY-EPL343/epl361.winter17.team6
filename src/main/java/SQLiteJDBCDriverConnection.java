import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 
 * Created by tomis on 21/11/2017.
 */
public class SQLiteJDBCDriverConnection {
    private static final boolean DEBUG = true;
    private static final String CHATBOT_DB_URL = "jdbc:sqlite:chatbot-db/chatbot-db.db";
    private Connection conn = null;
    String connectionUrl = null;

    public Connection connect(String url) throws SQLException {
        if (conn != null && !getConnection().isClosed()) {
            System.out.println("Connection is already established.");
            System.out.println("Closing connection and creating new one...");
            getConnection().close();
        }

        this.connectionUrl = url;
        //create connection to the database.
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established at " + url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() throws SQLException {
        getConnection().close();
        System.out.println("Database connection closed");
    }


    private Connection getConnection() {
        return conn;
    }

    public void createTablesBasedOnSqlFile() {
        String databaseCreationCUser = "chatbot-db/create-tables/create-CUser.sql";
        String databaseCreationToken = "chatbot-db/create-tables/create-Token.sql";
        String databaseCreationConversation = "chatbot-db/create-tables/create-Conversation.sql";
        String databaseCreationMessage = "chatbot-db/create-tables/create-Message.sql";

        // SQL statement for creating a new table

        String sqlCUser = FileParser.getFileContentAsString(databaseCreationCUser);
        String sqlToken = FileParser.getFileContentAsString(databaseCreationToken);
        String sqlConversation = FileParser.getFileContentAsString(databaseCreationConversation);
        String sqlMessage = FileParser.getFileContentAsString(databaseCreationMessage);

        try (Statement stmt = getConnection().createStatement()) {
            // create a new table
            stmt.execute(sqlCUser);
            stmt.execute(sqlToken);
            stmt.execute(sqlConversation);
            stmt.execute(sqlMessage);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertUser(String username, String token) {
        String insertUserSql = "INSERT INTO CUser(username) VALUES(?)";
        String insertTokenSql = "INSERT INTO Token(token_code,username) VALUES(?,?)";

        try (
                PreparedStatement pstmt1 = getConnection().prepareStatement(insertUserSql);
                PreparedStatement pstmt2 = getConnection().prepareStatement(insertTokenSql)) {
            pstmt1.setString(1, username);
            pstmt1.executeUpdate();

            pstmt2.setString(1, token);
            pstmt2.setString(2, username);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMessage(String username, String conversationId, String msg_id, long timestamp, boolean is_user_message,String content ) {
        String insertMessageSql = "INSERT INTO Message (username, conv_id, msg_id,  time_stamp, is_user_msg, content) VALUES(?, ?, ?, ?,?,?)";
        if( DEBUG )
            System.out.println("Inserting " + username + " with conv_id " + conversationId +
                    "message_id :" + msg_id +" with timestamp " + timestamp + " is_user_message");
        try (PreparedStatement pstmt = getConnection().prepareStatement(insertMessageSql);) {
            pstmt.setString(1, username);
            pstmt.setString(2, conversationId);
            pstmt.setString(3, msg_id);
            pstmt.setLong(4, timestamp);
            pstmt.setBoolean(5, is_user_message);
            pstmt.setString(6, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertConversation(String username, String conversationId , long init_timestamp) {
        String insertConversationSql = "INSERT INTO Conversation (username, conv_id,init_timestamp,  status) VALUES(?, ?, ?,0)";
        if( DEBUG )
            System.out.println("Inserting " + username + " with conv_id " + conversationId + " with timestamp " + init_timestamp);

        try (
                PreparedStatement pstmt = getConnection().prepareStatement(insertConversationSql);) {
            pstmt.setString(1, username);
            pstmt.setString(2, conversationId);
            pstmt.setLong(3, init_timestamp);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserInserted(String username) {
        String sql = "SELECT * FROM CUser WHERE CUser.username = (?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                //no data returned from the query therefore the user is not inserted
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getToken(String username) {
        String sql = "SELECT Token.token_code FROM Token WHERE Token.username = (?)";
        String retrievedToken = null;
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            retrievedToken = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrievedToken;
    }

    public String getUsername(String token) {
        String sql = "SELECT Token.username FROM Token WHERE Token.token_code = (?)";
        String retrievedUserName = null;
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, token);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            retrievedUserName = rs.getString(1);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrievedUserName;
    }

    public String stringStoreConversation(String token) {
        String sql = "SELECT Token.username FROM Token WHERE Token.token_code = (?)";
        String retrievedUserName = null;
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, token);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            retrievedUserName = rs.getString(1);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrievedUserName;
    }

    public String getConversationId(String username ) {
        String sql = "SELECT Conversation.conv_id FROM Conversation WHERE Conversation.username = (?)";
        String retrievedUserName = null;

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            retrievedUserName = rs.getString(1);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrievedUserName;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        SQLiteJDBCDriverConnection s = new SQLiteJDBCDriverConnection();
        s.connect(CHATBOT_DB_URL);
        s.createTablesBasedOnSqlFile();

        ArrayList<String> usrNames = new ArrayList<>();
        usrNames.add("Kostis");
        usrNames.add("John");
        usrNames.add("O_Stephos");
        usrNames.add("Mpaglamas");

        for (String usrName : usrNames) {
            System.out.println(s.isUserInserted(usrName));
            s.insertUser(usrName, usrName + "_token");
            System.out.println(s.isUserInserted(usrName));
            System.out.println(s.getToken(usrName));
            System.out.println("Retrieved Username " + s.getUsername(s.getToken(usrName)));
        }

        String conv_id = UUID.randomUUID().toString();
        s.insertConversation(usrNames.get(0), conv_id, 448484815);
        s.closeConnection();
    }


    public List<JSONObject> getMessages(String username, String conversationId) {
        String sql = "SELECT * FROM Message WHERE username = (?) AND conv_id = (?) ORDER BY time_stamp ASC";
        List<JSONObject> retrievedMessages = new ArrayList<>();

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, conversationId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("username",rs.getString("username") );
                jo.put("conv_id", rs.getString("conv_id"));
                jo.put("msg_id",rs.getString("msg_id") );
                jo.put("time_stamp", rs.getString("time_stamp"));
                jo.put("is_user_msg",rs.getInt("is_user_msg") );
                jo.put("content", rs.getString("content"));

                retrievedMessages.add(jo);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retrievedMessages;
    }
}
