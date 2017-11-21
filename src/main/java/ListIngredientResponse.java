import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListIngredientResponse extends AbstractResponse {

    @Override
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


















                for(FoodCategory fc : branchMenuFoodCategories){
                    response.append("<ul class=\""+ FOOD_CATEGORY_CLASS_NAME + "\"" +
                            "id=\"" + FOOD_CATEGORY_CLASS_NAME + "-" + fc.getId() +"\">" + fc.getName());
                    List<MenuItem> fcMenuItems = fc.getMenuItems();
                    for(MenuItem mi : fcMenuItems) {
                        response.append("<li class=\"" + MENU_ITEM_CLASS_NAME + "\" " +
                                "id=\""+ MENU_ITEM_CLASS_NAME + "-" + mi.getId() + "\">" + mi.getName() + "</li>");
                    }
                    response.append("</ul>");

                }


                return response.toString();
            }
        }
