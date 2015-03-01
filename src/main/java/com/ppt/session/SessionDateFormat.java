package com.ppt.session;

import com.ppt.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alshevchuk on 01.03.2015.
 */
public class SessionDateFormat {

    public static boolean isSessionExpired(Date expirationDate) {
        Date currentDate = new Date();
        return currentDate.getTime() > expirationDate.getTime();
    }

    public static Date convertDate(String expirationDateString) {
        Date date = null;
        try {
            DateFormat simpleDate = new SimpleDateFormat(Constants.sessionDatePattern);
            date = simpleDate.parse(expirationDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
