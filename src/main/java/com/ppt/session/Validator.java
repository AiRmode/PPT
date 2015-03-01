package com.ppt.session;

/**
 * Created by alshevchuk on 01.03.2015.
 */
public class Validator {

    public static boolean isSessionIDValid (String sessionID) {
        return SessionsHandler.getSessionsMap().containsKey(sessionID);
    }
}
