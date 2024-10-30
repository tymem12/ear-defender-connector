package com.eardefender.exception;

import com.eardefender.model.User;

public class UserNotOwnerException extends RuntimeException {
    public UserNotOwnerException(User user) {
        super("User " + user.getEmail() + " is not owner of the resource.");
    }
}
