package com.ppt.session;

import com.ppt.Constants;
import com.ppt.db.sqlite.SQLQueries;
import com.ppt.db.sqlite.SQLQueriesExecution;
import com.ppt.utils.Tuple;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by alshevchuk on 26.02.2015.
 */
public class SessionsHandler implements Runnable {
    private volatile ConcurrentHashMap<String, String> sessionsMap = new ConcurrentHashMap();
    private volatile ConcurrentHashMap<String, String> expiredSessionsMap = new ConcurrentHashMap();

    @Override
    public void run() {
        boolean isInterrupted = Thread.currentThread().isInterrupted();
        while (!isInterrupted) {
            try {
                populateSessionsMap();
                populateExpiredSessionsMap();
                removeExpiredSessionsFromDB();
                Thread.sleep(Constants.sessionHandlerDelay);
            } catch (Exception e) {
                //suppress
            }
        }
    }

    private void populateSessionsMap() {
        Tuple tuple = SQLQueriesExecution.executeResultQuery(SQLQueries.selectSessionIDs);
        Statement statement = (Statement) tuple.getK();
        ResultSet rs = (ResultSet) tuple.getV();
        try {
            if (rs == null) {
                return;
            }
            while (rs.next()) {
                sessionsMap.put(rs.getString("SessionID"), rs.getString("ExpirationDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeStatement(statement);
            closeResultSet(rs);
        }
    }

    private void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeStatement(Statement st) {
        try {
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeExpiredSessionsFromDB() {
        Iterator<String> expiredSessionsIter = expiredSessionsMap.keySet().iterator();
        while (expiredSessionsIter.hasNext()) {
            String expiredSession = expiredSessionsIter.next();
            String query = SQLQueries.removeInactiveSessionID.replace("{0}", expiredSession);
            SQLQueriesExecution.executeVoidQuery(query);
        }
    }

    private void populateExpiredSessionsMap() {
        Iterator<String> sessionsIterator = sessionsMap.keySet().iterator();
        while (sessionsIterator.hasNext()) {
            String session = sessionsIterator.next();
            String expirationDateString = sessionsMap.get(session);
            Date expirationDate = SessionDateFormat.convertDate(expirationDateString);

            if (SessionDateFormat.isSessionExpired(expirationDate)) {
                expiredSessionsMap.put(session, expirationDate.toString());
                sessionsIterator.remove();
            }

        }
    }
}
