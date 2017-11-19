import java.util.ArrayList;
import java.util.List;

public class ListBranchMenu extends AbstractResponse{

private int restaurantId;

    public void setRestaurantId(int resId){
        this.restaurantId = resId;

    }
    public String getResponse() {

        StringBuilder response = new StringBuilder();


        getServiceCaller().setSelectedRestaurant(restaurantId);

        response.append("Τα εστιατορία για τις επιλογες σας είναι :" + "\n");

        return response.toString();
    }
}
