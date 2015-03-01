package com.ppt.db.sqlite;

import java.sql.*;

/**
 * Created by alshevchuk on 26.02.2015.
 */
public class SQLiteJDBC {
    private static volatile SQLiteJDBC instance = null;
    private static volatile Connection connection = null;
    private final String dbPath = "jdbc:sqlite:C:\\ashevchuk_git\\my_git\\ppt\\DB\\test_sqlite\\test1.db";
    private final String dbClass = "org.sqlite.JDBC";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            initConnection();
        }
        return connection;
    }

    private SQLiteJDBC() throws ClassNotFoundException, SQLException {
        initConnection();
    }

    private void initConnection() throws ClassNotFoundException, SQLException {
        Class.forName(dbClass);
        connection = DriverManager.getConnection(dbPath);
        connection.setAutoCommit(true);
    }

    public static SQLiteJDBC getInstance() {
        SQLiteJDBC localInstance = instance;
        if (localInstance == null) {
            synchronized (SQLiteJDBC.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance = localInstance = new SQLiteJDBC();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return localInstance;
    }
}
