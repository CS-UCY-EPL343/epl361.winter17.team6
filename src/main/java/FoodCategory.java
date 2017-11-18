import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomis on 15/11/2017.
 */
public class FoodCategory extends FoodyObject {
    private List<Food> menuItems;

    FoodCategory(JSONObject json) {
        super(json);
        menuItems = new ArrayList<>();
        JSONArray menuItemsJSONArray = getJson().optJSONArray("menuitems");
        for(int i = 0; i < menuItemsJSONArray.length(); i++) {
            Food curFood = new Food(menuItemsJSONArray.optJSONObject(i));
            menuItems.add(curFood);
        }
    }

    List<Food> getMenuItems() {
        return menuItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        int i = 0;
        for(Food menuItem: menuItems)
            sb.append("Menu item " + i++ + " : " + menuItem.getName());
        sb.append("***------***------***\n");
        return sb.toString();

    }
}
