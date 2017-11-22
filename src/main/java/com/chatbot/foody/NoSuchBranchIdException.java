package com.chatbot.foody;

/**
 *
 * Created by tomis on 19/11/2017.
 */
 class NoSuchBranchIdException extends RuntimeException {
     NoSuchBranchIdException(int branchId) {
        super("There is no branch with id " + branchId);
    }
}
