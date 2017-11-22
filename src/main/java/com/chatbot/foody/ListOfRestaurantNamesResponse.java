package com.chatbot.foody;

import java.util.ArrayList;
import java.util.List;

public class ListOfRestaurantNamesResponse extends AbstractResponse {
    private static final String CLASS_NAME = "clickable-rest";

    public String getResponse(){

        StringBuilder response = new StringBuilder();
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        int index=0;
        restaurantList =  getServiceCaller().getMatchingRestaurants();
        response.append("<ul>");
        response.append("<li>Τα εστιατορία για τις επιλογες σας είναι :</li>");


        for (Restaurant r: restaurantList)
            response.append("<li class=\"" + CLASS_NAME + "\"" + " id = "+CLASS_NAME+"-"+r.getId()+ " onclick=\"sendId("+ r.getId() + ")\">" + "\t" + r.getName()+ "</li>");
        response.append("</ul>");
/*Call ListBranch Menu and Set restaurant */

        response.append("Παρακαλώ επιλέξετε !" + "\n");
        return response.toString();
    }

}
