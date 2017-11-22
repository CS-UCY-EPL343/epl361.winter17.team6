import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListBranchMenu extends AbstractResponse{
    private final static String MENU_ITEM_CLASS_NAME = "clickable-mi";
    private final static String FOOD_CATEGORY_CLASS_NAME = "food-categ";
    private int restaurantId;


    public void setRestaurantId(int resId){
        this.restaurantId = resId;
    }

    public String getResponse() {
        /*Create StringBuilder for response*/
        StringBuilder response = new StringBuilder();

        /*Set restaraunt IDs*/
        getServiceCaller().setSelectedRestaurant(restaurantId);

        /*Get Menu from Service Caller*/
        BranchMenu branchMenu = getServiceCaller().getSelectedRestaurantMenu();

        /*Get Food Categories list from Menu*/
        List<FoodCategory> branchMenuFoodCategories = branchMenu.getFoodCategories();


        for(FoodCategory fc : branchMenuFoodCategories){
            response.append("<ul class=\""+ FOOD_CATEGORY_CLASS_NAME + "\"" + "id=\"" + FOOD_CATEGORY_CLASS_NAME + "-" + fc.getId() +"\">" + fc.getName());

            /*Get Menu Items from each Food Category*/
            List<MenuItem> fcMenuItems = fc.getMenuItems();

                for(MenuItem mi : fcMenuItems)
                    response.append("<li class=\"" + MENU_ITEM_CLASS_NAME + "\" " + "id=\""+ MENU_ITEM_CLASS_NAME + "-" + mi.getId() + "\" onclick=\"sendMenuItemId("+ mi.getId() + ")\">" + "\t" + mi.getName() + "</li>");

            response.append("</ul>");

       }

        return response.toString();
    }
}
