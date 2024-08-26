package com.ao8r.awstoresapp.repository;

import static com.ao8r.awstoresapp.utils.StoresConstants.DB_NAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.HOST;
import static com.ao8r.awstoresapp.utils.StoresConstants.PASSWORD;
import static com.ao8r.awstoresapp.utils.StoresConstants.PORT;
import static com.ao8r.awstoresapp.utils.StoresConstants.SRC_DRIVER;
import static com.ao8r.awstoresapp.utils.StoresConstants.USERNAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.connection;

import android.os.StrictMode;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public  class ForgetUserPassword {


    public static void forgetUserPassword(String uPass,
                                          String uName,
                                          String empId,
                                          String empMobile) {

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
                //query UPass update
                //"UPDATE Mob_User_Request" +
                //                        "SET UPass = ?" +
                //                        "WHERE Emp_ID = ? " +
                //                        "AND " +
                //                        "UName = ? " +
                //                        "AND " +
                //                        "Emp_Mobile = ?;"
                String query = "UPDATE Mob_User_Request" +
                        "SET UPass = ?" +
                        "WHERE Emp_ID = ? " +
                        "AND " +
                        "UName = ? " +
                        "AND " +
                        "Emp_Mobile = ?;";

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, uPass);
                preparedStatement.setString(2, empId);
                preparedStatement.setString(3, uName);
                preparedStatement.setString(4, empMobile);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("Forget Password change successfully");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
