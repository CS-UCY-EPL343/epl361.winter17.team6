import java.util.ArrayList;
import java.util.List;

public class ListOfRestaurantNamesResponse extends AbstractResponse {
    private ServiceCaller sc;

    public String getResponse(){

        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        sc = getServiceCaller();
        restaurantList = sc.getMatchingRestaurants();

        String response = restaurantList.toString();


        return response;
    }

}
