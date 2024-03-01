package com.example.skylink.application;

import android.app.Activity;
import android.content.Context;

import java.io.File;

import com.example.skylink.persistence.Implementations.hsqldb.BookingHSQLDB;
import com.example.skylink.persistence.Implementations.hsqldb.FlightHSQLDB;
import com.example.skylink.persistence.Implementations.hsqldb.UserHSQLDB;
import com.example.skylink.persistence.Interfaces.IFlightDB;
import com.example.skylink.persistence.Interfaces.IUserDB;
import com.example.skylink.persistence.Interfaces.iBookingDB;

public class Services {

    private static IFlightDB flightDatabase = null;
    private static IUserDB userDatabase = null;
    private static iBookingDB bookDatabase = null;

    public static void setup(Activity activity) {
        final String DB_PATH = "db";
        Context context = activity.getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        Main.setDBPathName(dataDirectory.getAbsolutePath());
    }

    public static synchronized IFlightDB getFlightDatabase() {
        if (flightDatabase == null) {
            flightDatabase = new FlightHSQLDB(Main.getDBPathName()).initialize();
        }
        return flightDatabase;
    }

    public static synchronized IUserDB getUserDatabase() {
        if (userDatabase == null) {
            userDatabase = new UserHSQLDB(Main.getDBPathName()).initialize();
        }
        return userDatabase;
    }

    public static synchronized iBookingDB getBookDatabase() {
        if (bookDatabase == null) {
            bookDatabase = new BookingHSQLDB(Main.getDBPathName()).initialize();
        }
        return bookDatabase;
    }
}
