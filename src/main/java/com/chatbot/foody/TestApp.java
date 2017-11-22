package com.chatbot.foody;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by tomis on 17/11/2017.
 */
public class TestApp {
    public ServiceCaller sc;

    TestApp() {
        sc = new ServiceCaller();
    }
    public String getChatbotResponse(String usrMsg) {
        List<String> s = new ArrayList<String>();
        s.add("res_Id");
        s.add("87");

        TextParser tp = new TextParser(usrMsg);
     //   List<String> parsedUserMessage = tp.getKeyWords();
        String responceMessage = (new ResponseGenerator()).getResponce(s, sc);

        return responceMessage;
    }

    public static void main(String args[]) {
        TestApp ta = new TestApp();
        String usrMsg = "souvlakia";
        System.out.println("User: " + usrMsg);
        String chatbotResponce = ta.getChatbotResponse(usrMsg);
        System.out.println("Chatbot: " + chatbotResponce);
    }
}
