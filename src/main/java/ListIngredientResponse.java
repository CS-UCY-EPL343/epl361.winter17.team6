import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListIngredientResponse extends AbstractResponse {


    private List<Integer> MenuIDs;
    private List<MenuItem> menuItemList;

    public void set_menuIds(List<Integer> MenuIDs)
    {
        this.MenuIDs= MenuIDs;
    }




    public String getResponse() {
        /* GET Menu Items from Json File */


        getServiceCaller().setSelectedRestaurant(ServiceCaller.TOANAMMA_MENU);
        ArrayList<MenuItem> menuItems = (ArrayList<MenuItem>) getServiceCaller().getSelectedRestaurantMenu().getMenuItems();
        StringBuilder response = new StringBuilder();
        for(MenuItem mi : menuItems ) {
            JSONObject curMiJson = mi.getJson();
            double price = mi.getPrice();
            //double price  =  curMiJson.optDouble("price");
    }




















                return response.toString();
            }
        }
