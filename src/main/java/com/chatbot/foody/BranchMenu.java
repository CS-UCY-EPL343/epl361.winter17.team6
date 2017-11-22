package com.chatbot.foody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomis on 18/11/2017.
 */
public class BranchMenu extends FoodyObject {
    private List<MenuItem> menuItems;
    private List<FoodCategory> foodCategories;

    BranchMenu(JSONObject json) {
        super(json);
        menuItems = new ArrayList<>();
        foodCategories = new ArrayList<>();
        JSONArray menuItemsJsonArray = getJson().optJSONArray("menuitems");
        for(int i = 0; i < menuItemsJsonArray.length(); i++) {

            MenuItem curMenuItem = new MenuItem(menuItemsJsonArray.optJSONObject(i));
            menuItems.add(curMenuItem);
        }
        JSONArray foodCategoriesJsonArray = getJson().optJSONArray("categories");
        for(int i = 0; i < foodCategoriesJsonArray.length(); i++) {
            FoodCategory fc = new FoodCategory(foodCategoriesJsonArray.optJSONObject(i));
            foodCategories.add(fc);
        }

    }

    //getters
    List<MenuItem> getMenuItems() {
        return menuItems;
    }
    List<FoodCategory> getFoodCategories() { return foodCategories; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        int i = 0;
        sb.append( "-- Items Served: \n");
        for(MenuItem menuItem : menuItems )
            sb.append("Menu item " + i++ + ": " + menuItem.getName() + "\tPrice: " + menuItem.getPrice() + "\n");
        i = 0;
        sb.append("\n");
        sb.append( "-- FoodCategories : \n");
        for(FoodCategory fc : foodCategories)
            sb.append("food category " + i++ + ": " + fc.getName() + "\n" );
        sb.append("\n");
        sb.append("***------***------***\n");
        return sb.toString();
    }

    public static void main(String args[]) {

        String jsonString = FileParser.getFileContentAsString("sample-dataset/restaurant_menu/coffeebrands_menu.json");
        JSONObject BranchMenuJsonObject = new JSONObject(jsonString);
        BranchMenu branchMenu = new BranchMenu(BranchMenuJsonObject);
        System.out.println(branchMenu);
        MenuItem secondMenuItem = branchMenu.getMenuItems().get(1);
        System.out.println("The 2nd menu item is :\n" + secondMenuItem );
        System.out.println("The 2nd menu item has these com.chatbot.foody.Ingredient categories :\n" + secondMenuItem.getIngredientCategory()
                + "\n does the 2nd menu item have Modifiers? : " + secondMenuItem.hasModifiers());
        MenuItem thirdMenuItem = branchMenu.getMenuItems().get(2);
        System.out.println("The thirdMenuItem is :\n" + thirdMenuItem );
        System.out.println("The thirdMenuItem has these com.chatbot.foody.Ingredient categories : \n" + thirdMenuItem.getIngredientCategory()
                + "\n does the thirdMenuItem item have Modifiers? : " + thirdMenuItem.hasModifiers());
        System.out.println("The 2nd food category is " + branchMenu.getFoodCategories().get(1).getName()
                + " and has those MenuItems in it :\n" + branchMenu.getFoodCategories().get(1).getMenuItems() );


    }
}
