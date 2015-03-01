package com.ppt;

import com.ppt.db.sqlite.SQLiteJDBC;
import com.ppt.session.SessionsHandler;

/**
 * Created by alshevchuk on 26.02.2015.
 */
public class Configurator {
    private SQLiteJDBC sqLiteJDBC;

    public Configurator() {
        initDBConnection();
        initSessionsHandler();
    }

    public void initDBConnection() {
        try {
            sqLiteJDBC = SQLiteJDBC.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initSessionsHandler() {
        Thread sessionHandler = new Thread(new SessionsHandler());
        sessionHandler.start();
    }
}
