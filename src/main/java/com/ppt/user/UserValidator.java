package com.ppt.user;

/**
 * Created by alshevchuk on 01.03.2015.
 */
public class UserValidator {

    public static boolean isUserExists(User user) {
        return UserHelper.getUsersFromDB().contains(user.getUniqKey());
    }
}
