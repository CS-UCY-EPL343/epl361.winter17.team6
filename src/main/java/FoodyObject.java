import org.json.JSONObject;

/**
 * Created by tomis on 15/11/2017.
 */
public class FoodyObject implements JsonObject {
    private JSONObject jo;

    private String slug;
    private int id;

    public FoodyObject(JSONObject json) {

        this.jo = json;
        this.id = jo.getInt("id");
        this.slug = jo.getString("slug");

    }

    //getters
    public String getSlug() { return slug; }
    public int getId() { return id; }
    public JSONObject getJson() { return jo; }
    //** end getters

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Foody Object -- \n");
        sb.append("-- slug : " + slug + "\n");
        sb.append("-- id   : " + id + "\n");
        sb.append("------------------");
        return sb.toString();
    }

    public static void main(String args[]) {

    }
}
