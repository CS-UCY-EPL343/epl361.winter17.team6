
import java.*;
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


    public String getPostCode(){
        return null;
    }

    public String[] getCategoriesAsString(){
        return null;
    }

    public int[] getCatorgoriesIds(){
        return null;
    }

    public  List<Category> getCategories() {

        return null;
    }
    public  String[] getDistrictAsString(){

        return null;

    }
    public  int getDistrictId(){
        return 0;

    }
    public  District getDistrict(){
        return null;

    }

    public  void setPayByCredit(boolean payByCredit){
        return;
    }

    public  List<Restaurant> getMatchingRestaurants() {
        return null;

    }


    public  List<Food> getFoodFromFoodCategory(){

        return null;
    }
    public  List<FoodCategories> getBranchFoodCategories(){
        return null;
    }

    public  Branch getBranchDeliveryInfo(){

        return null;
    }

    public  List<FoodCategory> getFoodCategoriesForBranch(){
        return null;
    }

    public List<IngredientCategory> getIngredientCategoriesForFood(){
        return null;
    }

    public List<Ingredient> getIngredientsForIngredientCategory(){
        return null;
    }

    private int calculateBranchId(){
    return 0;
    }

}
