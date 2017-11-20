import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListBranchMenu extends AbstractResponse{
    private final static String MENU_ITEM_CLASS_NAME = "mi-class";
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
            response.append("<ul>" + fc.getName() + "</ul>");
            List<MenuItem> fcMenuItems = fc.getMenuItems();
            for(MenuItem mi : fcMenuItems) {
               response.append("<li class=\"\">" + mi.getName() + "</li>");
           }

       }


        return response.toString();
    }
}
