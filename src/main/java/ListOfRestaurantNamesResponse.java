import java.util.ArrayList;
import java.util.List;

public class ListOfRestaurantNamesResponse extends AbstractResponse {


    public String getResponse(){

        StringBuilder response = new StringBuilder();
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        int index=0;
        restaurantList =  getServiceCaller().getMatchingRestaurants();
        String selectedRes = "";
        response.append("Τα εστιατορία για τις επιλογες σας είναι :" + "\n");

        for (Restaurant r: restaurantList)
            response.append("<a id=\"clickable-rest\""+ selectedRes +  " on-click=\"sendId("+ r.getId() + ")\">" + "\t" + r.getName()+ "</a>" +"\n");




        response.append("Παρακαλώ επιλέξετε αριθμό :" + "\n");
        return response.toString();
    }

}
