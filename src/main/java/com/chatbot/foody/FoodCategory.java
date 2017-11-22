package com.chatbot.foody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomis on 15/11/2017.
 */
public class FoodCategory extends FoodyObject {
    private List<MenuItem> menuItems;

    FoodCategory(JSONObject json) {
        super(json);
        menuItems = new ArrayList<>();
        JSONArray menuItemsJSONArray = getJson().optJSONArray("menuitems");
        for(int i = 0; i < menuItemsJSONArray.length(); i++) {
            MenuItem curMenuItem = new MenuItem(menuItemsJSONArray.optJSONObject(i));
            menuItems.add(curMenuItem);
        }
    }

    List<MenuItem> getMenuItems() {
        return menuItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        int i = 0;
        for(MenuItem menuItem: menuItems)
            sb.append("Menu item " + i++ + " : " + menuItem.getName());
        sb.append("***------***------***\n");
        return sb.toString();

    }
}
