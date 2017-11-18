import org.json.JSONArray;
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
        sb.append("-- restaurant name: " + restaurantJson.optString("name") + "\n");
        sb.append("***------***------***\n");
        return sb.toString();
    }

    public static void main(String args[]) {
        String jsonString = FileParser.getFileContentAsString("sample-dataset/general/restaurants.json");
        JSONArray jsonArray = new JSONArray(jsonString);
        Branch branch = new Branch(jsonArray.optJSONObject(0));
        System.out.println(branch);
    }
}
