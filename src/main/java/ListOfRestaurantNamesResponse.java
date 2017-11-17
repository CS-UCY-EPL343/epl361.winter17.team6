import java.util.ArrayList;
import java.util.List;

public class ListOfRestaurantNamesResponse extends AbstractResponse {


    public String getResponse(){

        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        restaurantList =  getServiceCaller().getMatchingRestaurants();

        String response = restaurantList.toString();


        return response;
    }

}
