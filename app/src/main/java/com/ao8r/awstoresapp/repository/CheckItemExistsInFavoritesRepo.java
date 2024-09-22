package com.ao8r.awstoresapp.repository;

import android.content.Context;
import android.widget.Toast;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.dmoral.toasty.Toasty;

public class CheckItemExistsInFavoritesRepo {
    static Connection connection;

    public static void getItemNoFromFavoritesRepo(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();
            } else {

                //Query
//              "SELECT ItemName FROM Mob_Favourite WHERE ItemName =? AND UserID = ?"

                String updateQuery =
//                        "SELECT Item_No FROM Mob_Favourite WHERE Item_No =? AND UserID = ?";
                "EXEC [dbo].[CheckFavourite]  @ItemNo = ?, @UserID = ?";

                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setString(1, String.valueOf(StoresConstants.ITEM_NUMBER));
                statement.setString(2, String.valueOf(StoresConstants.CURRENT_USER_ID));

                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {
// get fav ITEM-NUMBER

                    StoresConstants.FAV_ITEM_NUMBER = resultSet.getInt("Item_No");


//                    System.out.println("avTotalAllStoreQuantity is:   " + resultSet.getDouble("Qty"));

//                    CustomToast.customToast(context,  "هذا العنصر فى المفضلة");
                    Toasty.warning(context, "هذا العنصر فى المفضلة", Toast.LENGTH_SHORT, true).show();
//                    System.out.println("FavItemName is:   " + resultSet.getString("Item_Name"));
//                    CustomToast.customToast(context,  StoresConstants.STORE_NAME + "===>"+StoresConstants.STORE_TOTAL_QUANTITY);



                } else {
//                    CustomToast.customToast(context,  "هذا العنصر لايوجد فى المفضلة");
                    Toasty.info(context, "هذا العنصر لايوجد فى المفضلة", Toast.LENGTH_SHORT, true).show();

                }
//

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
