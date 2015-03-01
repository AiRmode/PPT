package com.ppt.db.sqlite;

import com.ppt.utils.Tuple;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by alshevchuk on 26.02.2015.
 */
public class SQLQueriesExecution {
    private static volatile SQLiteJDBC sqLiteJDBC = SQLiteJDBC.getInstance();

    public static void executeVoidQuery(String query) throws IllegalStateException {
        try {
            Connection connection = sqLiteJDBC.getConnection();
            Statement statement = connection.createStatement();
            if (statement.isClosed()) {
                throw new IllegalStateException();
            }
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Tuple executeResultQuery(String query) throws IllegalStateException {
        ResultSet rs = null;
        Statement statement = null;
        try {
            Connection connection = sqLiteJDBC.getConnection();
            statement = connection.createStatement();
            if (statement.isClosed()) {
                throw new IllegalStateException();
            }
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Tuple(statement,rs);
    }
}
