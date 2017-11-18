import java.util.ArrayList;
import java.util.List;

public class ListOfRestaurantNamesResponse extends AbstractResponse {


    public String getResponse(){

        StringBuilder response = new StringBuilder();
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        int index=0;
        restaurantList =  getServiceCaller().getMatchingRestaurants();

        response.append("Τα εστιατορία για τις επιλογες σας είναι :" + "\n");

        for (Restaurant restaurant_name: restaurantList)
            response.append(++index + "\t" + restaurant_name.getName()+ "\n");


        response.append("Παρακαλώ επιλέξετε αριθμό :" + "\n");
        return response.toString();
    }

}
