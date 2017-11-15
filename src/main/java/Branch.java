import org.json.JSONObject;

/**
 * Created by tomis on 15/11/2017.
 */
public class Branch extends FoodyObject {


    JSONObject restaurantJson;

    Branch(JSONObject json) {
        super(json);
        restaurantJson = json.optJSONObject("restaurant");
    }

    //getters
    public JSONObject getRestaurantJson() {return restaurantJson; }
    //** end getters

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("-- restaurant : " + restaurantJson.optString("name") );
        sb.append(" ---------------**");
        return sb.toString();
    }
}
