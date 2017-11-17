import java.util.List;

/**
 * Created by tomis on 17/11/2017.
 */
public class TestApp {
    public ServiceCaller sc;

    TestApp() {
        sc = new ServiceCaller();
    }
    public String getChatbotResponse(String usrMsg) {
        TextParser tp = new TextParser(usrMsg);
        List<String> parsedUserMessage = tp.getKeyWords();
        String responceMessage = (new ResponseGenerator()).getResponce(parsedUserMessage, 1, sc);

        return responceMessage;
    }

    public static void main(String args[]) {
        TestApp ta = new TestApp();
        String usrMsg = "I want souvlakia";
        System.out.println("User: " + usrMsg);
        String chatbotResponce = ta.getChatbotResponse(usrMsg);
        System.out.println("Chatbot: " + chatbotResponce);
    }
}
