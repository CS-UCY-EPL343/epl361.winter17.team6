import org.eclipse.jetty.http.BadMessageException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ListIngredientResponse extends AbstractResponse {

    private final static String MENU_ITEM_CLASS_NAME = "clickable-mi";
    private List<Integer> MenuIDs;
    private List<MenuItem> menuItemList;
    private List<String> menuItems;

    public void set_menuIds_Int(List<Integer> MenuIDs) {
        this.MenuIDs = MenuIDs;
    }


    public String getResponse() {
        /* GET Menu Items from Json File */
        StringBuilder response = new StringBuilder();
        BranchMenu menu = getServiceCaller().getSelectedRestaurantMenu();
        List<MenuItem> menuList;

        /*Get the id from the restaurant called in the list Restarant*/

        menuList = menu.getMenuItems();

        response.append("<p>Αυτα που παραγγειλατε είναι: </p>");
        double allPrice = 0;
        /*For each Menu Item in menuList print */
        for (MenuItem mi : menuList) {
            try {
                if (MenuIDs.contains(mi.getId())) {
                    double price = mi.getPrice();
                    allPrice += price;
                    JSONObject curMiJson = mi.getJson();
                    response.append("<li class=\"" + MENU_ITEM_CLASS_NAME + "\" " + "id=\"" + MENU_ITEM_CLASS_NAME + "-" + mi.getId() + "\" onclick=\"sendMenuItemId(" + mi.getId() + ")\">" + "\t" + mi.getName() + "\t" + "Price: " + price + "\t" + "</li>");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        response.append("<li>Total-Price is: " + allPrice + "</li></ul>");

        return response.toString();

    }

}







