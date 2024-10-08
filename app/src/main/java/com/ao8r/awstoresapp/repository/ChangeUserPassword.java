package com.ao8r.awstoresapp.repository;

import android.content.Context;
import android.widget.Toast;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.dmoral.toasty.Toasty;


public class ChangeUserPassword {
    static Connection connection;

    public static void changeUserPassword(Context context, String currentPassword, String newPassword) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();

//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
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
//                    Toasty.success(context, "تم تغيير كلمة المرور بنجاح", Toast.LENGTH_SHORT, true).show();
                }else{
                    CustomToast.customToast(context,"عفوا, لم يتم تغيير كلمة المرور ❌ ");
                }


            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getMessage();
            CustomToast.customToast(context,"فضلا, برجاء أدخال بيانات صحيحة ❌ ");
//            Toasty.error(context, "فضلا, برجاء أدخال بيانات صحيحة ", Toast.LENGTH_SHORT, true).show();
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
