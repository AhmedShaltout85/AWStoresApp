package com.ao8r.awstoresapp.repository;

import android.content.Context;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.models.StoresNamesModel;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class GetAllStoresNamesBySectorNameDropdown {
    static Connection connection;

    public static ArrayList<StoresNamesModel> getAllStoresNamesBySectorNameDropdown(Context context) {
        ArrayList<StoresNamesModel> storesNamesModelArrayList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {
                CustomToast.customToast(context, "جارى الأتصال بقاعدة البيانات");
                //Query
//              --SELECT STORE NAME FROM STORES TABLE BY STORE SECTOR
                //
                //SELECT
                //    store_name
                //FROM
                //    stores_data
                //WHERE store_sector = 'غرب وجنوب'
                //ORDER BY store_num ASC;

                String updateQuery =
//                        "--SELECT STORE NAME FROM STORES TABLE BY STORE SECTOR\n" +
                        "SELECT store_name FROM stores_data WHERE store_sector = ? ORDER BY store_num ASC";



                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setString(1, StoresConstants.STORE_SECTOR);
//                statement.setString(1, "شرق");

                ResultSet resultSet = statement.executeQuery();



                while (resultSet.next()) {
// get ALL DATA
                    StoresNamesModel storesNamesModel = new StoresNamesModel();
                    storesNamesModel.setStoreNameReport(resultSet.getString("store_name"));
                    storesNamesModelArrayList.add(storesNamesModel);

//                    CustomToast.customToast(context, "تم جلب البيانات");
                    System.out.println(resultSet.getString("store_name"));


                }


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
        return storesNamesModelArrayList;
    }
}








