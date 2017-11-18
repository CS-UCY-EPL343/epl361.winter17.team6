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
    public static final int LIST_RESTAURANT = 1,SHOW_HISTORY = 2,CHOOSE_PAYMENT = 3, LIST_INGREDIENT = 4,LIST_BRANCH_MENU = 5,HELP = 6, RESTAURANT_SCEDULE= 7,BRANCH_CATEGORIES = 8;


    private AbstractResponse mapKeywordsToResponse(){

        ArrayList<String> KeyWords = new ArrayList<>();
        String response_message= "No response";

        AbstractResponse abstract_response_class = null;

        switch (msgCode) {

            case LIST_RESTAURANT:

                String WordList_Souvlakia ;
                String WordList_Burgers;
                String WordList_Sandwich;
                String [] WordList_Souvlakia_l;
                String [] WordList_Burgers_l;
                String [] WordList_Sandwich_l;

        /*--- Burgers ,Slouvakia, Sawndich Collection---*/

                WordList_Souvlakia = FileParser.getFileContentAsString("WordList/Souvlakia_Wordlist.txt");
                WordList_Burgers = FileParser.getFileContentAsString("WordList/Burgers_Wordlist.txt");
                WordList_Sandwich = FileParser.getFileContentAsString("WordList/Sandwich_Wordlist.txt");
                //  System.out.print(WordList_Souvlakia);
                WordList_Souvlakia_l = WordList_Souvlakia.split("\n");
                WordList_Sandwich_l = WordList_Sandwich.split("\n");
                WordList_Burgers_l = WordList_Burgers.split("\n");


        /*---Setting categories codes according to keywords ----*/


                for (int index = 0 ; index < WordList_Burgers_l.length;index++){
                    if (keyWords.contains(WordList_Burgers_l[index])){
                        sc.addCategory(ServiceCaller.BURGERS);


                    }
                }

                for (int index = 0 ; index < WordList_Sandwich_l.length;index++){
                    if (keyWords.contains(WordList_Sandwich_l[index])){
                        sc.addCategory(ServiceCaller.SANDWICH);

                    }
                }

                for (int index = 0 ; index < WordList_Souvlakia_l.length;index++){
                    if (keyWords.contains(WordList_Souvlakia_l[index])){
                        sc.addCategory(ServiceCaller.SOUVLAKIA);


                    }
                }

                abstract_response_class = new ListOfRestaurantNamesResponse();
                abstract_response_class.setServiceCaller(sc);

                break;

            case SHOW_HISTORY:
                abstract_response_class = new ShowHistoryResponse();
                abstract_response_class.setServiceCaller(sc);

                break;

            case CHOOSE_PAYMENT:
                abstract_response_class = new ChoosePaymentMethodResponse();
                abstract_response_class.setServiceCaller(sc);

                break;

            case LIST_INGREDIENT:
                abstract_response_class = new ChoosePaymentMethodResponse();
                abstract_response_class.setServiceCaller(sc);

                break;

            case LIST_BRANCH_MENU:


                abstract_response_class = new ListBranchMenu();
                abstract_response_class.setServiceCaller(sc);

                break;

            case HELP:
                abstract_response_class = new ListOfRestaurantNamesResponse();
                abstract_response_class.setServiceCaller(sc);
                break;

            case RESTAURANT_SCEDULE:
                abstract_response_class = new ListOfRestaurantNamesResponse();
                abstract_response_class.setServiceCaller(sc);

                break;
            case BRANCH_CATEGORIES:

                abstract_response_class = new ListOfRestaurantNamesResponse();
                abstract_response_class.setServiceCaller(sc);

                break;

        }



        return abstract_response_class;
    }

    private void setMsgCode(int MsgCode){


            this.msgCode = MsgCode;

    }
    private void setKeyWords (List<String> keyWords){


        this.keyWords= keyWords;

    }
    public void getMsgCode(){


    }

    public String getResponce (List<String> keyWords,int expectedMsgCode,ServiceCaller sc){

        this.sc = sc;
        setMsgCode(expectedMsgCode);
        setKeyWords (keyWords);



        return   mapKeywordsToResponse().getResponse();




    }


}
