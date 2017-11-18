
import org.json.JSONArray;
import org.json.JSONObject;

import java.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ServiceCaller {
    private static boolean DEBUG = false;
    public static final int BURGERS = 12, SOUVLAKIA = 2, SANDWICH = 17;
    public static final int COFFEBRAND = 1, TOANAMMA = 2;
    private String postCode;
    private String userId;
    private int districtId;
    private List<Integer> categoryIds;
    private boolean payByCredit ;
    private int selectedRestaurant;
    private int branchId;
    private int curFoodCategoryId;
    private int currentFoodId;
    private float curPrice;
    private int curIngredientCategoryId;
    private List<Integer> selectedFoodsIds;
    private List<Integer> selectedIngredientsId;

    private Set<Integer> selectedCategoriesIds = new HashSet<>();
    private List<Branch> matchedBranches;
    private List<Restaurant> matchedRestaurants;
    private BranchMenu selectedRestaurantMenu;
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

    /**
     * Used to add category to filter restaurants and branches from.
     * If no category is chosen then all restaurants and branches are selected (based on other criteria).
     * @param categoryId the category id.
     */
    void addCategory(int categoryId) {
        selectedCategoriesIds.add(categoryId);
    }

    /**
     * Used to remove category to filter restaurants and branches from.
     * If no category is chosen then all restaurants and branches are selected (based on other criteria).
     * @param categoryId the category id.
     */
    void removeCategory(int categoryId) {
        selectedCategoriesIds.remove(categoryId);
    }

    /**
     *
     * @param branchId the id of the branch
     */
    void setSelectedRestaurant(int branchId) {
        this.selectedRestaurant = branchId;
        String jsonString = "";
        switch ( selectedRestaurant ) {
            case TOANAMMA :
                    jsonString = FileParser.getFileContentAsString("sample-dataset/general/restaurants.json");
                    break;
            case COFFEBRAND :
                    jsonString = FileParser.getFileContentAsString("sample-dataset/general/restaurants.json");
        }
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

    /**
     * Set the pay by credit.
     * @param payByCredit whether the user will pay by credit or not.
     */
    public  void setPayByCredit(boolean payByCredit){
        this.payByCredit = payByCredit;
    }

    //TODO make it so when no categories are selected show all restaurants.
    /**
     * Returns matching restaurants based on district, postal code and selected categories.
     * @return a list of Restaurants
     */
     List<Restaurant> getMatchingRestaurants() {
        ArrayList<Branch> matchedBranches = new ArrayList<>();
        //RestaurantId  is saved
        List<Integer> restaurantIds = new ArrayList<>();
        List<Restaurant> matchedRestaurants = new ArrayList<>();

        String jsonString = "";
        //If no category is set then return all restaurants.
        if(selectedCategoriesIds.isEmpty()) {
            jsonString = FileParser.getFileContentAsString("sample-dataset/general/restaurants.json");
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
        }

        //The json Array has json representation of branches
        for(Integer categoryId : selectedCategoriesIds) {
            switch (categoryId) {
                case SANDWICH:
                    jsonString = FileParser.getFileContentAsString("sample-dataset/restaurants_by_category/sandwich.json");
                    break;
                case SOUVLAKIA:
                    jsonString = FileParser.getFileContentAsString("sample-dataset/restaurants_by_category/souvlakia.json");
                    break;
                case BURGERS:
                    jsonString = FileParser.getFileContentAsString("sample-dataset/restaurants_by_category/burgers.json");
                    break;
            }

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

    /**
     *
     * @return the BranchMenu of the selected Restaurant.
     */
    BranchMenu getSelectedRestaurantMenu() {
         return selectedRestaurantMenu;
    }
    public static void main(String args[]) throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream("log-output/output.txt"));
        System.setOut(out);
        ServiceCaller sc = new ServiceCaller();

        sc.addCategory(ServiceCaller.BURGERS);
        List<Restaurant> restaurantList = sc.getMatchingRestaurants();
        System.out.println("Burger Restaurants found:");
        for(Restaurant r : restaurantList) {
            System.out.println(r);
        }

        System.out.println("Number of restaurants found after adding burger category " + sc.getMatchingRestaurants().size() );
        sc.addCategory(ServiceCaller.SANDWICH);
        System.out.println("Number of restaurants found after adding sandwich category " +   sc.getMatchingRestaurants().size() );
        sc.removeCategory(ServiceCaller.SANDWICH);
        System.out.println("Number of restaurants found after removing sandwich category " +  sc.getMatchingRestaurants().size()  );
    }
}
