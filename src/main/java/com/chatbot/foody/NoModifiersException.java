package com.chatbot.foody;

/**
 *
 * Created by tomis on 18/11/2017.
 */
public class NoModifiersException extends RuntimeException {
    NoModifiersException() {
        super("This object has no modifiers.");
    }
}
