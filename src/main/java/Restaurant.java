import org.json.JSONObject;

/**
 * 
 * Created by tomis on 15/11/2017.
 */
public class Restaurant extends FoodyObject {

    Restaurant(JSONObject json) {
        super(json);
    }
    String getName() {
        return getJson().optString("name");
    }


}
