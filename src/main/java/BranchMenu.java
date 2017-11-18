import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomis on 18/11/2017.
 */
public class BranchMenu extends FoodyObject {
    private List<Food> menuItems;
    private List<FoodCategory> foodCategories;

    BranchMenu(JSONObject json) {
        super(json);
        menuItems = new ArrayList<>();
        foodCategories = new ArrayList<>();
        JSONArray menuItemsJsonArray = getJson().optJSONArray("menuitems");
        for(int i = 0; i < menuItemsJsonArray.length(); i++) {

            Food curMenuItem = new Food(menuItemsJsonArray.optJSONObject(i));
            menuItems.add(curMenuItem);
        }
        JSONArray foodCategoriesJsonArray = getJson().optJSONArray("categories");
        for(int i = 0; i < foodCategoriesJsonArray.length(); i++) {
            FoodCategory fc = new FoodCategory(foodCategoriesJsonArray.optJSONObject(i));
            foodCategories.add(fc);
        }

    }

    List<Food> getMenuItems() {
        return menuItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        int i = 0;
        sb.append( "-- Items Served: \n");
        for(Food menuItem : menuItems ) {
            sb.append("Menu item " + i + ": " + menuItem.getName() + "\tPrice: "+menuItem.getPrice()+ "\n");
            i++;
        }
        i = 0;
        sb.append("\n");
        sb.append( "-- FoodCategories : \n");
        for(FoodCategory fc : foodCategories) {
            sb.append("food category " + i + ": " + fc.getName() + "\n" );
            i++;
        }
        sb.append("\n");
        sb.append("***------***------***\n");
        return sb.toString();
    }

    public static void main(String args[]) {

        String jsonString = FileParser.getFileContentAsString("sample-dataset/restaurant_menu/coffeebrands_menu.json");
        JSONObject BranchMenuJsonObject = new JSONObject(jsonString);
        BranchMenu branchMenu = new BranchMenu(BranchMenuJsonObject);
        System.out.println(branchMenu);
        Food secondMenuItem = branchMenu.getMenuItems().get(1);
        System.out.println("The 2nd menu item is :\n" + secondMenuItem );
        System.out.println("The menu item has these Ingredient categories : " + secondMenuItem.getIngredientCategory());


    }
}
