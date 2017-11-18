/**
 * Created by tomis on 18/11/2017.
 */
public class FoodCategoryIsNotSetException extends Exception {
    FoodCategoryIsNotSetException( ) {
        super("Food Category is not set yet");
    }
}
