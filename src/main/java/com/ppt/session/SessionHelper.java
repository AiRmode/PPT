package com.ppt.session;

import com.ppt.Constants;
import com.ppt.db.sqlite.SQLQueries;
import com.ppt.db.sqlite.SQLQueriesExecution;
import com.ppt.session.id.SessionID;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by alshevchuk on 01.03.2015.
 */
public class SessionHelper {

    public static void createAndStoreSessionToDB(String login, String password) {
        SessionID sessionID = new SessionID(login, password);
        String expDate = calcExpirationDate();
        String sessionIDString = sessionID.getSessionID();

        //store session id to DB
        String updateQuery = SQLQueries.addNewSessionID.replace("{0}", sessionIDString)
                .replace("{1}", expDate);
        SQLQueriesExecution.executeVoidQuery(updateQuery);
    }

    private static String calcExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, Constants.sessionTimeout);
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat(Constants.sessionDatePattern);
        String stringDate = dateFormat.format(date);
        return stringDate;
    }

    public static void removeSessionByRequest(String sessionID) {
        Map<String, String> sessionsMap = SessionsHandler.getSessionsMap();
        sessionsMap.remove(sessionID);
        Map<String, String> expiredSessionsMap = SessionsHandler.getExpiredSessionsMap();
        expiredSessionsMap.put(sessionID, "");
    }
}
