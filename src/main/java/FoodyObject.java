import org.json.JSONObject;

/**
 * Created by tomis on 15/11/2017.
 */
public class FoodyObject implements JsonObject {
    private JSONObject jo;

    int id;
    private String name;
    private String slug;

    public FoodyObject(JSONObject json) {

        this.jo = json;

    }

    public JSONObject getJson() {
        return jo;
    }

    String getName() {
        return name;
    }


    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

}
