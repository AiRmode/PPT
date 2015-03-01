package com.ppt.user;

import com.ppt.db.sqlite.SQLQueries;
import com.ppt.db.sqlite.SQLQueriesExecution;
import com.ppt.utils.Tuple;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by alshevchuk on 01.03.2015.
 */
public class UserHelper {
    private static Set<String> usersUniqIDs = new CopyOnWriteArraySet<>();

    public static void createUser(String login, String password) {
        String query = SQLQueries.addNewUser.replace("{0}", User.createUniqID(login, password));
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
