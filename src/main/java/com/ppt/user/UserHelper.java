package com.ppt.user;

import com.ppt.Constants;
import com.ppt.db.sqlite.SQLQueries;
import com.ppt.db.sqlite.SQLQueriesExecution;
import com.ppt.utils.Tuple;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by alshevchuk on 01.03.2015.
 */
public class UserHelper {
    private static Set<String> usersUniqIDs = new CopyOnWriteArraySet<>();

    public static void createUser(String login, String password) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat(Constants.sessionDatePattern);
        String stringDate = dateFormat.format(date);
        String query = SQLQueries.addNewUser.replace("{0}", User.createUniqID(login, password)).replace("{1}", stringDate);
        //store to DB
        SQLQueriesExecution.executeVoidQuery(query);

    }

    public static Set<String> getUsersFromDB() {
        Tuple<Statement, ResultSet> tuple = SQLQueriesExecution.executeResultQuery(SQLQueries.selectUsersUniqIDs);
        Statement statement = tuple.getK();
        ResultSet rs = tuple.getV();
        populateUsersSet(rs);
        SQLQueriesExecution.closeStatementAndResultSet(statement, rs);
        return usersUniqIDs;
    }

    private static void populateUsersSet(ResultSet rs) {
        try {
            while (rs != null && rs.next()) {
                usersUniqIDs.add(rs.getString("UniqKey"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
