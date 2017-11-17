import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by steph on 15/11/2017.
 */


public class ResponseGenerator {

    List <String> keyWords;
    int msgCode ;
    ServiceCaller sc;
    public static final int LIST_RESTAURANT = 1;


    private AbstractResponse mapKeywordsToResponse(){
        ArrayList<String> KeyWords = new ArrayList<>();
        AbstractResponse ab_response = new AbstractResponse() {
            @Override
            public String getResponse() {


                return null;
            }
        };

        return ab_response;
    }

    private void setMsgCode(int MsgCode){


            this.msgCode = MsgCode;

    }

    public void getMsgCode(){


    }

    public String getResponce (List<String> keyWords,int expectedMsgCode,ServiceCaller sc){

        String response_message= "No response";

        AbstractResponse abstract_response_class;

        switch (expectedMsgCode) {
            case 1:
                abstract_response_class = new ListOfRestaurantNamesResponse();
                abstract_response_class.setServiceCaller(sc);

                response_message=abstract_response_class.getResponse();

                break;


        }



        return response_message ;
    }


}
