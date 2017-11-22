package com.chatbot.foody;

/**
 * Created by tomis on 18/11/2017.
 */
public class FoodCategoryIsNotSetException extends Exception {
    FoodCategoryIsNotSetException( ) {
        super("com.chatbot.foody.MenuItem com.chatbot.foody.Category is not set yet");
    }
}
