package com.ao8r.awstoresapp.repository;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.customiz_widgets.CustomSnackBar;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import es.dmoral.toasty.Toasty;

@RequiresApi(api = Build.VERSION_CODES.O)

public class RemoveItemsFromFavRepo {
    static Connection connection;

    public static void removeItemsFromFavRepo(Context context, View view,String itmNo) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();
            } else {
//                -- Delete Mob_Favourite
//                Delete From Mob_Favourite Where UserID = '2' and Item_No = '3611150'


                String updateQuery =
//                        "DELETE FROM Mob_Favourite WHERE UserID = ? and Item_No = ?";
                "EXEC [dbo].[DeleteFavourite]  @UserID = ? , @ItemNo = ?";

                PreparedStatement statement = connection.prepareStatement(updateQuery);

                ////////////////
                statement.setString(1, String.valueOf(StoresConstants.CURRENT_USER_ID));
                statement.setString(2, itmNo);

                statement.executeUpdate();

                CustomSnackBar.customSnackBar(view,"تم حذف العنصر من المفضله",context);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
//            CustomToast.customToast(context, "لم يتم الحذف");
            Toasty.error(context, "لم يتم الحذف", Toast.LENGTH_SHORT, true).show();
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
