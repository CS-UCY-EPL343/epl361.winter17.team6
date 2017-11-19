import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tomis on 15/11/2017.
 */
public class FoodyObject implements JsonObject {
    private JSONObject jo;

    private String slug;
    private int id;
    private String name;

    public FoodyObject(JSONObject json) {

        this.jo = json;
        this.id = jo.getInt("id");
        try {
            this.slug = jo.getString("slug");
        }catch (JSONException e) {
            this.slug = "There is no slug key for this foody object. ("+getClass()+")";
        }
        try {
            this.name = jo.getString("name");
        }catch (JSONException e) {
            this.name = "There is no name key for this foody object. ("+getClass()+")";
        }


    }


    //getters
    public String getSlug() { return slug; }
    public String getName() { return  name;}
    public int getId() { return id; }
    public JSONObject getJson() { return jo; }
    //** end getters

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Foody Object -- \n");
        sb.append("-- Type of Foody Object is: " + getClass() +"\n");
        sb.append("-- slug : " + getSlug() + "\n");
        sb.append("-- name : " + getName() + "\n");
        sb.append("-- id   : " + getId() + "\n");
        sb.append("------------------\n");
        return sb.toString();
    }

    public static void main(String args[]) {

    }
}
