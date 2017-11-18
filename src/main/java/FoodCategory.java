import org.json.JSONObject;

import java.util.List;

/**
 * Created by tomis on 15/11/2017.
 */
public class FoodCategory extends FoodyObject {
    private List<Food> foodList;

    FoodCategory(JSONObject json) {

        super(json);


    }
}
