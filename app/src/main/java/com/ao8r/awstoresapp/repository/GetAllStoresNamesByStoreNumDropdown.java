package com.ao8r.awstoresapp.repository;

import android.content.Context;
import android.widget.Toast;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.models.StoresNamesModel;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class GetAllStoresNamesByStoreNumDropdown {
    static Connection connection;

    public static ArrayList<StoresNamesModel> getAllStoresNamesByStoreNumDropdown(Context context) {
        ArrayList<StoresNamesModel> storesNamesModelArrayList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();
            } else {
//                CustomToast.customToast(context, "جارى الأتصال بقاعدة البيانات");
                Toasty.info(context, "جارى الأتصال بقاعدة البيانات", Toast.LENGTH_SHORT, true).show();
                //Query
//              --SELECT STORE NAME FROM STORES TABLE BY STORE NUM
                //SELECT
                //store_name
                //FROM
                //stores_data
                //WHERE store_num = 3;

                String updateQuery =
                        "SELECT store_name FROM stores_data WHERE store_num = ?";

                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setInt(1, StoresConstants.STORE_NUMBER);
//                statement.setInt(1, 3);

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








