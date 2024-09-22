package com.ao8r.awstoresapp.repository;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.utils.StoresConstants;
import com.ao8r.awstoresapp.views.MenuScreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.dmoral.toasty.Toasty;

public class LoginRepo {

    static Connection connection;

    public static void login(final Context context, final String loginPassword) {

        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();

            } else {

                String query = "SELECT * FROM Mob_Users WHERE Name= ? AND Pass = ?";
//                    Statement statement = connection.createStatement();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, StoresConstants.LOGIN_USER);
                statement.setString(2, loginPassword);


                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {
//                    CustomToast.customToast(context, "تم تسجيل الدخول بنجاح ✅");
                    Toasty.success(context, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT, true).show();
                    StoresConstants.CURRENT_USER_ID = resultSet.getInt("ID");
                    StoresConstants.USER_CONTROL = resultSet.getInt("Flag");
                    StoresConstants.STORE_NUMBER = resultSet.getInt("Store_num");
                    StoresConstants.STORE_SECTOR = resultSet.getString("Sector");

                    System.out.println(
                            "User Credentials: \n ID \n Flag \n Store_num \n Sector\n" +
                                    resultSet.getInt("ID") + "\n" +
                                    resultSet.getInt("Flag") + "\n" +
                                    resultSet.getString("Store_num") + "\n" +
                                    resultSet.getString("Sector"));

//            used with security levels

//                    if(StoresConstants.USER_CONTROL != 3){
//                        StoresConstants.STORE_NUMBER = "0";
//                    }else{
//                        StoresConstants.STORE_NUMBER = resultSet.getString("Sector");
//                    }

//
                    Intent intent = new Intent(context, MenuScreen.class);
                    context.startActivity(intent);

                } else {
//                    CustomToast.customToast(context, "بيانات خاطئة ❌");
                    Toasty.error(context, "بيانات خاطئة ❌", Toast.LENGTH_SHORT, true).show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


