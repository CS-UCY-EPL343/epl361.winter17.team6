
import java.*;
import java.util.ArrayList;
import java.util.List;


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

    List<Category> selectedCategories;
    District userDistrict;


    public String getPostCode(){
        return postCode;
    }

    public List<String> getCategoriesAsString(){
        ArrayList<String> categNames = new ArrayList<>();
        for(Category c : selectedCategories ) {
            categNames.add(c.getName());
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

        return userDistrict.getName() ;

    }

    public  int getDistrictId() {

        return userDistrict.id;

    }

    public  District getUserDistrict(){

        return userDistrict;

    }

    public  void setPayByCredit(boolean payByCredit){
        this.payByCredit = payByCredit;
    }

    //TODO implement logic
    public  List<Restaurant> getMatchingRestaurants() {

        return null;

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

}
