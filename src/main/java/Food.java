import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 *
 * Created by tomis on 15/11/2017.
 */
public class Food extends FoodyObject {
    private String name;
    private double price;
    private boolean hasModifiers;
    private List<IngredientCategory> ingredientCategory;

    Food(JSONObject json ) {
        super(json);

        this.price = getJson().optDouble("price");
        this.hasModifiers = getJson().optBoolean("hasmodifier");

        JSONObject modifierJSONObject = getJson().optJSONObject("modifier");

        if(hasModifiers) {
            JSONArray modifierCategories = modifierJSONObject.optJSONArray("categories");
            for(int i = 0; i < modifierCategories.length(); i++) {
                IngredientCategory curIngredientCategory = new IngredientCategory(modifierCategories.optJSONObject(i));
                ingredientCategory.add(curIngredientCategory);
            }
        }
    }

    List<IngredientCategory> getIngredientCategory() {
        return ingredientCategory;
    }

    boolean hasModifiers() {
        return hasModifiers;
    }
    double getPrice () {
        return price;
    }

    public static void main(String args) {

    }

}
