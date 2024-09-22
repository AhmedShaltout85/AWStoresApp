package com.ao8r.awstoresapp.repository;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.customiz_widgets.CustomSnackBar;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import es.dmoral.toasty.Toasty;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AddItemsToFavoritesRepo {

    static Connection connection;

    public static void insertItemsToFavorites(Context context, View view) {


            try {
                connection = ConnectionHelper.getConnection();

                if (connection == null) {
                    Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();
//                    CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
                } else {
//                    -- Insert Mob_Favourite
//                    Insert Into Mob_Favourite (UserID,Item_No) Values ('2','3611150')
                    String updateQuery =
//                            "INSERT INTO Mob_Favourite (UserID,Item_No) VALUES (?,?)";
                    "EXEC [dbo].[AddFavourite] @UserID = ? , @ItemNo = ?";

                    PreparedStatement statement = connection.prepareStatement(updateQuery);

                    ////////////////
                    statement.setString(1, String.valueOf(StoresConstants.CURRENT_USER_ID));
                    statement.setString(2, String.valueOf(StoresConstants.ITEM_NUMBER));

                    statement.executeUpdate();
                    CustomSnackBar.customSnackBar(view, "تم الإضافة الى المفضلة", context);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                CustomToast.customToast(context, "لم يتم الاضافة الى المفضلة");
//                Toasty.error(context, "لم يتم الاضافة الى المفضلة", Toast.LENGTH_SHORT, true).show();

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

//    }
}





