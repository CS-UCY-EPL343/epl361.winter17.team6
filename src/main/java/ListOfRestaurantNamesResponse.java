import java.util.ArrayList;
import java.util.List;

public class ListOfRestaurantNamesResponse extends AbstractResponse {


    public String getResponse(){
        StringBuilder response = new StringBuilder();
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        restaurantList =  getServiceCaller().getMatchingRestaurants();

        response.append("The restaurants that you have chosen are:" + "\n");
        for (Restaurant restaurant_name: restaurantList)
              response.append(restaurant_name.getName()+ "\n");



        return response.toString();
    }

}
