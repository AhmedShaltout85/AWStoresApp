package com.ao8r.awstoresapp.repository;

import android.content.Context;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.models.FavoritesModel;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAllFavsRepo {
    static Connection connection;

    public static void getAllFavsRepo(Context context, ArrayList<FavoritesModel> favoritesModelArrayList) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {

                //Query
//                Select MF.Item_No,dbo.ItemName(MF.Item_No) Item_Name,dbo.ItemQty(MF.Item_No) Qty
//                From Mob_Favourite MF
//                Where MF.UserID = '1'

                String updateQuery =
//                        "SELECT MF.Item_No," +
//                        "dbo.ItemName(MF.Item_No) Item_Name,ROUND(dbo.ItemQty(MF.Item_No),2) Qty\n" +
//                        "FROM Mob_Favourite MF\n" +
//                        "WHERE MF.UserID = ?";
                        "EXEC [dbo].[GetFavQty] @UserID = ? , @StoreNo = 0";

                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setString(1, String.valueOf(StoresConstants.CURRENT_USER_ID));

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get fav
                    FavoritesModel favoritesModel = new FavoritesModel();
                    favoritesModel.setFavItemName(resultSet.getString("Item_Name"));
                    favoritesModel.setFavItemNumber(resultSet.getString("Item_No"));
                    favoritesModel.setFavTotalAllStoreQuantity(String.valueOf(resultSet.getDouble("Qty")));
//                   add Favorites to list
                    favoritesModelArrayList.add(favoritesModel);


                    System.out.println("avTotalAllStoreQuantity is:   " + resultSet.getDouble("Qty"));
                    System.out.println("FavItemNumber is:   " + resultSet.getString("Item_No"));
                    System.out.println("FavItemName is:   " + resultSet.getString("Item_Name"));
//                    CustomToast.customToast(context,  StoresConstants.STORE_NAME + "===>"+StoresConstants.STORE_TOTAL_QUANTITY);


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

