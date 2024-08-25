package com.ao8r.awstoresapp.repository;


import android.os.StrictMode;

import com.ao8r.awstoresapp.utils.EncryptionUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public  class RetrieveEncryptedString {

    private static Connection connection;
    private static final String HOST = "41.33.226.212";
    private static final int PORT = 5010;
    private static final String DB_NAME = "awco";
    private static final String USERNAME = "awco";
    private static final String PASSWORD = "awco";
    private static final String SRC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    public static void retrieveEncryptedString(String empId) {

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //Import mssql driver
            Class.forName(SRC_DRIVER);

            //ceate connection
            connection = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://"+HOST+":"+PORT+"/"+DB_NAME
                    , USERNAME, PASSWORD);

            if (connection == null) {
            } else {
                String query = "SELECT * FROM Mob_User_Request WHERE Emp_ID = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, empId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String Upass = resultSet.getString("UPass");
                    System.out.println("encrypted password = " + Upass);
                    String EncryptedPass = EncryptionUtil.decrypt(Upass);
                    System.out.println("decrypted password = " + EncryptedPass);

                    // Do something with the encrypted string
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
