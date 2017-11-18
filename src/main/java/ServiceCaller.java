
import org.json.JSONArray;
import org.json.JSONObject;

import java.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ServiceCaller {
    private static boolean DEBUG = false;
    private static final int BURGERS = 12, SOUVLAKIA = 2, SANDWICH = 17;

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

    private Set<Integer> selectedCategoriesIds;
    private List<Branch> matchedBranches;
    private List<Restaurant> matchedRestaurants;
    //private List<Category> selectedCategories;
    private District userDistrict;

    public String getPostCode(){
        return postCode;
    }

    /**
     *
     * @return a list with the names of the categories that are currently selected.
     */
    public List<String> getCategoriesAsString(){
        ArrayList<String> categNames = new ArrayList<>();
        for(Integer c : selectedCategoriesIds ) {
            switch( c ) {
                case SANDWICH :
                    categNames.add("Sandwich");
                case SOUVLAKIA :
                    categNames.add("Souvlakia");
                case BURGERS :
                    categNames.add("Burgers");
            }
        }
        return categNames;
    }

    public void addCategory(int categoryId) {
        selectedCategoriesIds.add(categoryId);
    }

    public void removeCategory(int categoryId) {
        selectedCategoriesIds.remove(categoryId);
    }

    /**
     *
     * @return a list the the category ids of the currently selected categories.
     */
    public List<Integer> getCatorgoriesIds(){
        ArrayList<Integer> categIds = new ArrayList<>();
        categIds.addAll(categIds);
        return categIds;
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
        String jsonString = FileParser.getFileContentAsString("sample-dataset/restaurants_by_category/burgers.json");
        JSONArray jsonArray = new JSONArray(jsonString);

        //create Branch for each json object in jsonArray
        if(DEBUG)
            System.out.println("The json of the first json object in the array is \n" + jsonArray.optJSONObject(0).toString(4));
        for(int i = 0; i < jsonArray.length(); i++) {
            Branch curBranch = new Branch(jsonArray.optJSONObject(i));
            matchedBranches.add(curBranch);
            if(DEBUG)
                System.out.println("The first Branch object is \n" + curBranch);
            Restaurant restaurant = new Restaurant(curBranch.getJson().optJSONObject("restaurant"));

            int restaurantId = restaurant.getId();
            boolean isRestaurantAlreadyInList = restaurantIds.contains(restaurantId);
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
