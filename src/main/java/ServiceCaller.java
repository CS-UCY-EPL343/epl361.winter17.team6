
import org.json.JSONArray;
import org.json.JSONObject;

import java.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ServiceCaller {

    String postCode;
    String userId;
    int districtId;
    List<Integer> categoryIds;
    boolean payByCredit ;
    int selectedrestaurant;
    int branchId;
    int curFoodCategoryId;
    int currentFoodId;
    float curPrice;
    int curIngredientCategoryId;
    List<Integer> selectedFoodsIds;
    List<Integer> selectedIngredientsId;

    List<Branch> matchedBranches;
    List<Restaurant> matchedRestaurants;
    List<Category> selectedCategories;
    District userDistrict;

    private String getStringFromFile(String path) {
        StringBuilder jsonString = new StringBuilder("");
        // The name of the file to open.
        String fileName = path;
        // This will reference one line at a time
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                jsonString.append(line);
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.err.println("Unable to open file '" + fileName + "'");
            ex.printStackTrace();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        return jsonString.toString();
    }

    public String getPostCode(){
        return postCode;
    }

    public List<String> getCategoriesAsString(){
        ArrayList<String> categNames = new ArrayList<>();
        for(Category c : selectedCategories ) {
            categNames.add(c.getSlug());
        }
        return categNames;
    }

    public List<Integer> getCatorgoriesIds(){
        ArrayList<Integer> categIds = new ArrayList<>();
        for(Category c : selectedCategories ) {
            categIds.add(c.getId());
        }
        return categIds;
    }

    public  List<Category> getCategories() {

        return selectedCategories;
    }

    public  String getDistrictAsString(){

        return userDistrict.getSlug() ;

    }

    public  int getDistrictId() {

        return userDistrict.getId();

    }

    public  District getUserDistrict(){

        return userDistrict;

    }

    public  void setPayByCredit(boolean payByCredit){
        this.payByCredit = payByCredit;
    }

    /**
     *
     * @return
     */
    public  List<Restaurant> getMatchingRestaurants() {
        ArrayList<Branch> matchedBranches = new ArrayList<>();
        //RestaurantId  is saved 
        List<Integer> restaurantIds = new ArrayList<>();
        List<Restaurant> matchedRestaurants = new ArrayList<>();

        //The json Array has json represantation of branches
        String jsonString = getStringFromFile("sample-dataset/restaurants_by_category/burgers.json");
        JSONArray jsonArray = new JSONArray(jsonString);

        //create Branch for each json object in jsonArray
        System.out.println("The json of the first json object in the array is \n" + jsonArray.optJSONObject(0).toString(4));
        for(int i = 0; i < jsonArray.length(); i++) {
            Branch curBranch = new Branch(jsonArray.optJSONObject(i));
            matchedBranches.add(curBranch);
            System.out.println("The first Branch object is \n" + curBranch);
            Restaurant restaurant = new Restaurant(curBranch.getJson().optJSONObject("restaurant"));

            int restaurantId = restaurant.getId();
            boolean isRestaurantAlreadyInList = restaurantIds.contains(restaurantId);
            System.out.println(restaurant);
            if(!isRestaurantAlreadyInList) {
                matchedRestaurants.add(restaurant);
                restaurantIds.add(restaurantId);
            }
        }

        this.matchedBranches = matchedBranches;
        this.matchedRestaurants = matchedRestaurants;
        return matchedRestaurants;
    }

    //TODO implement logic
    public  List<Food> getFoodFromFoodCategory(){

        return null;
    }

    //TODO implement logic
    public  List<FoodCategory> getBranchFoodCategories(){
        return null;
    }

    //TODO implement logic
    public  Branch getBranchDeliveryInfo(){

        return null;
    }

    //TODO implement logic
    public  List<FoodCategory> getFoodCategoriesForBranch(){
        return null;
    }

    //TODO implement logic
    public List<IngredientCategory> getIngredientCategoriesForFood(){
        return null;
    }

    //TODO implement logic
    public List<Ingredient> getIngredientsForIngredientCategory(){
        return null;
    }

    //TODO implement logic
    private int calculateBranchId(){
        return 0;
    }

    public static void main(String args[]) throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        ServiceCaller sc = new ServiceCaller();
        List<Restaurant> restaurantList = sc.getMatchingRestaurants();
        System.out.println("Restaurants found:");
        for(Restaurant r : restaurantList) {
            System.out.println(r);
        }
    }
}
