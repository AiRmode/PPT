package com.ppt.session.id;

import com.ppt.user.User;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;

/**
 * Created by alshevchuk on 24.02.2015.
 */
public class SessionID {
    private final String digestAlgoritm = "MD5";
    private String login = "";
    private String password = "";
    private String sessionID = null;

    public SessionID(String login, String password) {
        this.login = login;
        this.password = password;
        validateParams();
        createSessionID();
    }

    private void validateParams() throws UnsupportedOperationException {
        if (login == null || password == null) {
            throw new UnsupportedOperationException();
        }
    }

    private void createSessionID() {
        StringBuffer sb = new StringBuffer();
        sessionID = User.createUniqID(login, password);

        sb.append(new Date()).append(System.currentTimeMillis()).append((System.currentTimeMillis() * new Date().getTime()));
        sessionID += Base64.encodeBase64String(sb.toString().getBytes());

        sessionID = Base64.encodeBase64String(sessionID.getBytes());
    }

    public String getSessionID() {
        return sessionID;
    }
}
