package com.ao8r.awstoresapp.repository;

import android.content.Context;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.models.StoresNamesModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class GetAllStoresNamesDropdown {
    static Connection connection;

    public static ArrayList<StoresNamesModel> getAllStoresNamesDropdown(Context context) {
        ArrayList<StoresNamesModel> storesNamesModelArrayList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {
                CustomToast.customToast(context, "جارى الأتصال بقاعدة البيانات");
                //Query
//              "SELECT * FROM Branch";

                String updateQuery =
                        "SELECT [store_name] FROM [stores_data] Order By Store_Num";


                PreparedStatement statement = connection.prepareStatement(updateQuery);


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








