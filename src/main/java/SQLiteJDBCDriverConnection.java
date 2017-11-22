import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

/**
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

    public void insertConversation(String username, String conversationId) {
        String insertConversationSql = "INSERT INTO Conversation (username, conv_id,  status) VALUES(?, ?, 0)";

        try (
                PreparedStatement pstmt = getConnection().prepareStatement(insertConversationSql);) {
            pstmt.setString(1, username);
            pstmt.setString(2, conversationId);
            pstmt.executeQuery();
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
            System.out.println(s.getUsername(s.getToken(usrName)));
        }

        String conv_id = UUID.randomUUID().toString();
        s.insertConversation(usrNames.get(0), conv_id);
        s.closeConnection();
    }
}
