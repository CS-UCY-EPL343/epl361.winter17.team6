import java.util.List;

/**
 * Created by tomis on 17/11/2017.
 */
public class TestApp {
    public String getChatbotResponse(String usrMsg) {
        TextParser tp = new TextParser(usrMsg);
        List<String> parsedUserMessage = tp.getKeyWords();

        return null;
    }
    public static void main(String args[]) {


    }
}
