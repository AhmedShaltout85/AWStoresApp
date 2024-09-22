package com.ao8r.awstoresapp.repository;

import android.content.Context;
import android.widget.Toast;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.models.StoreReportModel;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class GetAllItemsInCertainStore {
    static Connection connection;

    public static void getAllItemsInCertainStore(Context context, ArrayList<StoreReportModel> storeReportModelArrayList) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();
            } else {

                //Query 'جرد مخزن'
                //EXEC	[dbo].[StoreQty] @StoreName = ?


                String updateQuery =
                        "EXEC	[dbo].[StoreQty] @StoreName = ?";



                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setString(1, StoresConstants.STORE_NAME_REPORT);

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get Store Items
                StoreReportModel storeReportModel = new StoreReportModel();
                storeReportModel.setItemCode(resultSet.getString("item_no"));
                storeReportModel.setItemNameReport(resultSet.getString("item_name"));
                storeReportModel.setTotalQtyNow(resultSet.getString("SBal"));
                storeReportModel.setLastDate(resultSet.getString("lastDate"));
                storeReportModelArrayList.add(storeReportModel);
//
//
                    System.out.println("itemCode is:   " + resultSet.getString("item_no"));
                    System.out.println("itemNameReport is:   " + resultSet.getString("item_name"));
                    System.out.println("totalQtyNow is:   " +resultSet.getString("SBal"));
                    System.out.println("lastDate is:   " +resultSet.getString("lastDate"));


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
    }
}
