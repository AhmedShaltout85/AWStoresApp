package com.ao8r.awstoresapp.repository;

import android.content.Context;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ChangeUserPassword {
    static Connection connection;

    public static void changeUserPassword(Context context, String currentPassword, String newPassword) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {

                //Query
                //"EXEC	[dbo].[UpdatePass]
                //		@ID = ?,
                //		@OldPass = ?,
                //		@NewPass = ?"


                String updateQuery =
                        "EXEC	[dbo].[UpdatePass] " +
                                "@ID = ?, " +
                                "@OldPass = ?, " +
                                "@NewPass = ?";



                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setLong(1, StoresConstants.CURRENT_USER_ID);
                statement.setString(2, currentPassword);
                statement.setString(3, newPassword);

                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {
                    String message = resultSet.getString("Message");
                    System.out.println(message);
                    CustomToast.customToast(context, "تم تغيير كلمة المرور بنجاح ✅");
                }


            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            CustomToast.customToast(context,"فضلا, برجاء أدخال بيانات صحيحة ❌ ");
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
