package com.ppt.user;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by alshevchuk on 01.03.2015.
 */
public class User {
    private final String login;
    private final String password;
    private final String uniqKey;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.uniqKey = createUniqID(login, password);
    }

    public String getUniqKey() {
        return uniqKey;
    }

    public static String createUniqID(String login, String password) {
        return DigestUtils.md5Hex(login + password);
    }
}
