import java.sql.*;

/**
 *
 * Created by tomis on 21/11/2017.
 */
public class SQLiteJDBCDriverConnection {
    private static final boolean DEBUG = true;
    private static final String CHATBOT_DB_URL= "jdbc:sqlite:chatbot-db/chatbot-db.db";
    private Connection conn = null;

    public Connection connect(String url) throws SQLException {
        if(conn != null && !getConnection().isClosed()) {
            System.out.println("Connection is already established.");
            System.out.println("Closing connection and creating new one...");
            getConnection().close();
        }

        //create connection to the database.
        try {
            conn = DriverManager.getConnection(url);
            if (DEBUG)
                System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() throws SQLException {
        getConnection().close();
    }



    public Connection getConnection() {
        return conn;
    }

    public void createTablesBasedOnSqlFile() {
        String databaseCreationConversation = "chatbot-db/create-tables/create-Conversation.sql";
                String databaseCreationCUser ="chatbot-db/create-tables/create-CUser.sql";
                String databaseCreationMessage ="chatbot-db/create-tables/create-Message.sql";
                String databaseCreationToken="chatbot-db/create-tables/create-Token.sql";

                // SQL statement for creating a new table

        String sqlToken         =   FileParser.getFileContentAsString(databaseCreationToken);
        String sqlCUser         =   FileParser.getFileContentAsString(databaseCreationCUser);
        String sqlConversation  =   FileParser.getFileContentAsString(databaseCreationConversation);
        String sqlMessage       =   FileParser.getFileContentAsString(databaseCreationMessage);

        try (Statement stmt = getConnection().createStatement()) {
            // create a new table
            stmt.execute(sqlToken);
            stmt.execute(sqlConversation);
            stmt.execute(sqlCUser);
            stmt.execute(sqlMessage);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insert(String username, String token) {
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

    public  boolean isUserInserted(String username) {
        String sql = "SELECT * FROM CUser WHERE CUser.username = (?)";

        try (PreparedStatement pstmt  = getConnection().prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst() ) {
               //no data returned from the query therefore the user is not inserted
               return false;
            }
            else
            {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        SQLiteJDBCDriverConnection s = new SQLiteJDBCDriverConnection();
        s.connect(CHATBOT_DB_URL);
        s.createTablesBasedOnSqlFile();


        System.out.println(s.isUserInserted("Kostis"));
        s.insert("Kostis", "kostis_token");
        System.out.println(s.isUserInserted("Kostis"));
        s.closeConnection();
    }
}
