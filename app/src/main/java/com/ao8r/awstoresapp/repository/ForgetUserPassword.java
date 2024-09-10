package com.ao8r.awstoresapp.repository;


import static com.ao8r.awstoresapp.utils.StoresConstants.DB_NAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.HOST;
import static com.ao8r.awstoresapp.utils.StoresConstants.PASSWORD;
import static com.ao8r.awstoresapp.utils.StoresConstants.PORT;
import static com.ao8r.awstoresapp.utils.StoresConstants.SRC_DRIVER;
import static com.ao8r.awstoresapp.utils.StoresConstants.USERNAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.connection;

import android.content.Context;
import android.os.StrictMode;

import com.ao8r.awstoresapp.customiz_widgets.CustomToast;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ForgetUserPassword {


    public static void forgetUserPassword(Context context,
                                          String empId,
                                          String empMobile,
                                          String uName,
                                          String uPass) throws SQLException, ClassNotFoundException { //throws SQLException
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //Import mssql driver
            Class.forName(SRC_DRIVER);

            //create connection
            connection = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://" + HOST + ":" + PORT + "/" + DB_NAME
                    , USERNAME, PASSWORD);

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم ❌");
            } else {

                System.out.println("before insert procedure");
                //Query for forget password

//                String updateQuery ="EXEC [dbo].[Forget_Password] " +
//                        "@UPass = ?," +
//                        "@UName = ?, " +
//                        "@Emp_Mobile = ?, " +
//                        "@Emp_ID = ?";

                String updateQuery = "{CALL [dbo].[Forget_Password](?,?,?,?)}";

//
                CallableStatement statement = connection.prepareCall(updateQuery);

                statement.setString(1, uPass);
                statement.setString(2, empId);
                statement.setString(3, uName);
                statement.setString(4, empMobile);


                boolean result = statement.execute();

                System.out.println("after insert procedure execution");

                if (!result) {
                    System.out.println("stored procedure executed successfully");
                } else {
                    System.out.println("stored procedure executed failed");
                }

            }
        } catch (Exception ex) {
            throw ex; //pass the exception to the caller
//            ex.getMessage();
//            System.out.println(ex.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.getMessage();
                }
            }

        }
    }
}


