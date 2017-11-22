//import com.sun.org.apache.xpath.internal.compiler.Keywords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by steph on 15/11/2017.
 */


public class ResponseGenerator {

    /*Keywords from text parser*/
    List <String> keyWords;

    /*Message code for producing desired response*/
    int msgCode ;

    /*Service Caller for the User */
    ServiceCaller sc;

    /*Restaraunts that will be used in List Menu , List Restaurant*/

    private int selectedRestaurantId=0 ;
    private List<Integer> selectedMenuId;

    /*Declaring Finals*/
    public static final int LIST_RESTAURANT = 1,SHOW_HISTORY = 2,CHOOSE_PAYMENT = 3, LIST_ITEMS = 4,LIST_BRANCH_MENU = 5,HELP = 6, RESTAURANT_SCEDULE= 7,BRANCH_CATEGORIES = 8, INVALID=9;
    public static final String KEY_LIST_RESTAURANT  = "want", KEY_BRANCH  = "res_id",KEY_ITEMS_VIEW = "usr_menu";
    public static final String[] KEY_LIST__FOOD = {"souvlakia", "burger" ,"sandwich"};

    private AbstractResponse mapKeywordsToResponse(){


        /*Declaring class for response*/
        AbstractResponse abstract_response = null;


        switch (msgCode) {

            case LIST_RESTAURANT:

                /*---Setting categories Restarants according to keywords ----*/

                if (keyWords.contains(KEY_LIST__FOOD[0]))
                    sc.addCategory(ServiceCaller.SOUVLAKIA);

                if (keyWords.contains(KEY_LIST__FOOD[1]))
                    sc.addCategory(ServiceCaller.BURGERS);

                if (keyWords.contains(KEY_LIST__FOOD[2]))
                    sc.addCategory(ServiceCaller.SANDWICH);

                abstract_response = new ListOfRestaurantNamesResponse();


                break;

            case SHOW_HISTORY:

                abstract_response = new ShowHistoryResponse();

                break;

            case CHOOSE_PAYMENT:
                /*TODO: Create Choose_Payment class and Implement */
                break;

            case LIST_ITEMS:
                /*Create class and send Menu Ids*/
                abstract_response  = new ListIngredientResponse();
                ((ListIngredientResponse)abstract_response).set_menuIds_Int(selectedMenuId);
                break;

            case LIST_BRANCH_MENU:
                /*Create class and send Restaurant IDs*/
                abstract_response  = new ListBranchMenu();
                ((ListBranchMenu)abstract_response).setRestaurantId(selectedRestaurantId);

                break;

            case HELP:
                abstract_response = new HelpResponse();
                break;

            case INVALID:
                abstract_response = new HelpResponse("INVALID MESSAGE\n");
                break;

            case RESTAURANT_SCEDULE:
                abstract_response = new ListRestaurantScheduleResponse();
                break;

            case BRANCH_CATEGORIES:

                abstract_response = new ListBranchFoodCategoriesResponse();
                break;

        }

        abstract_response.setServiceCaller(sc);
        return abstract_response;
    }

    /*Setters*/

    private void setMsgCode(int MsgCode){
            this.msgCode = MsgCode;
    }

    private void setServiceCaller(ServiceCaller sc){
        this.sc = sc;
    }

    private void setKeyWords (List<String> keyWords){

        this.keyWords= keyWords;
    }
    public  void  setSelectedRest(int sR){

        this.selectedRestaurantId = sR;
    }
    public  void  setSelectedMenuId(List<Integer> MenuId){

        this.selectedMenuId = MenuId;
    }


    public void produceMessageCode (List<String> keyWords){

        for(String food : KEY_LIST__FOOD)
          if (keyWords.contains(food) || keyWords.contains(KEY_LIST_RESTAURANT)) {
              setMsgCode(LIST_RESTAURANT);
              return;
          }

        if (keyWords.contains(KEY_BRANCH)) {
            setMsgCode(LIST_BRANCH_MENU);
            setSelectedRest( Integer.parseInt(keyWords.get(LIST_RESTAURANT)) );
            return;
        }

        if (keyWords.contains(KEY_ITEMS_VIEW)) {

            selectedMenuId = new ArrayList<Integer>();
            setMsgCode(LIST_ITEMS);

            /*Get the Integers from the keywords Sting Array*/
            keyWords.remove(0);

            /*Return all the ids in the menu*/
            for (String item : keyWords)
                selectedMenuId.add(Integer.parseInt(item));

            return;
        }

        if (keyWords.isEmpty()) {
            setMsgCode(INVALID);
            return;
        }
        else
            setMsgCode(HELP);

        return;
    }


    public String getResponce (List<String> keyWords,ServiceCaller sc){

        /*Set service Caller for user*/
       setServiceCaller(sc);
       setKeyWords (keyWords);


       System.out.println("MEEEEESAGE"+keyWords);
       produceMessageCode(keyWords);


       return   mapKeywordsToResponse().getResponse();




    }


}
