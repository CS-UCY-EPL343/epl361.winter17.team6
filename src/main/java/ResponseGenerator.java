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
        String response_message= "No response";

        AbstractResponse abstract_response_class;

        switch (msgCode) {
            case 1:
                abstract_response_class = new ListOfRestaurantNamesResponse();
                abstract_response_class.setServiceCaller(sc);

                break;


        }



        return abstract_response_class;
    }

    private void setMsgCode(int MsgCode){


            this.msgCode = MsgCode;

    }

    public void getMsgCode(){


    }

    public String getResponce (List<String> keyWords,int expectedMsgCode,ServiceCaller sc){


        setMsgCode(expectedMsgCode);


        return   mapKeywordsToResponse().getResponse();

    }


}
