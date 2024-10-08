package com.ao8r.awstoresapp.controller;

import static com.ao8r.awstoresapp.utils.StoresConstants.URL_CONNECT_LINK;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    private static final String HOST = "41.33.226.212";
    private static final int PORT = 5010;
    private static final String DB_NAME = "awco";
    private static final String USERNAME = "awco";
    private static final String PASSWORD = "awco";
    private static final String SRC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
//	private static String URL = "jdbc:jtds:sqlserver://"+HOST+":"+PORT+"/"+DB_NAME;
//    jdbc:jtds:sqlserver://41.33.226.211:5011/test_db




    private static Connection connection;

    @SuppressLint("NewApi")

    public static Connection getConnection() {
        try {
            //	ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},
            //	PackageManager.PERMISSION_GRANTED);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //Import mssql driver
            Class.forName(SRC_DRIVER);

            //create connection
            connection = DriverManager.getConnection(
//                    String.format("jdbc:jtds:sqlserver://%s:%d/%s", HOST, PORT, DB_NAME)
                    "jdbc:jtds:sqlserver://"+URL_CONNECT_LINK+":"+PORT+"/"+DB_NAME
                    , USERNAME, PASSWORD);
        } catch(SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        }
        return connection;
    }
}
