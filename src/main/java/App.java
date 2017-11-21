/*
 * Created by tomis on 14/11/2017.
 *
 * Server initiated at
 * http://localhost:4567
 * */
import java.util.*;

import static spark.Spark.*;
public class App {
    private Map<String, ServiceCaller> tokenServiceCallerMap = new HashMap<String,ServiceCaller>();
    private Map<String, ResponseGenerator> tokenResponseGeneratorMap = new HashMap<String, ResponseGenerator>();
    //private ServiceCaller sc;

    public String getChatbotResponse(String usrMsg, String userToken) {
        List<String> s = new ArrayList<String>();
        ResponseGenerator userResponseGenerator = getUserResponseGenerator(userToken);
        ServiceCaller userServiceCaller = getUserServiceCaller(userToken);

        TextParser tp = new TextParser(usrMsg);
        List<String> parsedUserMessage = tp.getKeyWords();

        return userResponseGenerator.getResponce(parsedUserMessage, userServiceCaller);
    }

    private ServiceCaller getUserServiceCaller(String userToken) {
        return tokenServiceCallerMap.get(userToken);
    }

    private ResponseGenerator getUserResponseGenerator(String userToken) {
        return tokenResponseGeneratorMap.get(userToken);
    }

    public void addNewToken(String userToken ) {
        tokenServiceCallerMap.put(userToken, new ServiceCaller());
        tokenResponseGeneratorMap.put(userToken, new ResponseGenerator());
    }

    public static void main(String args[]){

    }
}