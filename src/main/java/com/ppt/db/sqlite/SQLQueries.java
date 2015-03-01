package com.ppt.db.sqlite;

/**
 * Created by alshevchuk on 26.02.2015.
 */
public class SQLQueries {
    public static final String removeInactiveSessionID = "DELETE FROM UsersSessions WHERE SessionID IN ('{0}')";
    public static final String addNewSessionID = "INSERT INTO UsersSessions (SessionID, ExpirationDate) VALUES ('{0}','{1}')";
    public static final String updateSessionID = "UPDATE UsersSessions SET ExpirationDate='{0}' WHERE SessionID='{1}'";
    public static final String selectSessionIDs = "SELECT * FROM UsersSessions";
    public static final String selectUsersUniqIDs = "SELECT UniqKey FROM Users";
    public static final String addNewUser = "INSERT INTO Users (UniqKey, CreateDate) VALUES ('{0}','{1}')";

}