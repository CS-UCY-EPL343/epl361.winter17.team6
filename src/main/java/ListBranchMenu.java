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

        StringBuilder response = new StringBuilder();
        getServiceCaller().setSelectedRestaurant(restaurantId);
        BranchMenu branchMenu = getServiceCaller().getSelectedRestaurantMenu();
        List<FoodCategory> branchMenuFoodCategories = branchMenu.getFoodCategories();
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
