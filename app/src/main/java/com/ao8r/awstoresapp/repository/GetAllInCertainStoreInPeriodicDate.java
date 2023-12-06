package com.ao8r.awstoresapp.repository;

import android.content.Context;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.models.StoreReportModel;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAllInCertainStoreInPeriodicDate {

    static Connection connection;

    public static void getAllInCertainStoreInPeriodicDate(Context context, ArrayList<StoreReportModel> storeReportPeriodicModelArrayList) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {

                //Query
                //EXEC	[dbo].[StoreQty] @StoreName = ?


                String updateQuery =
                        "EXEC [dbo].[StoreQtyPeriod] @StoreName = ?,@FromDtae = ?,@ToDate = ?";


                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setString(1, StoresConstants.STORE_NAME_REPORT);
                statement.setString(2, StoresConstants.START_DATE);
                statement.setString(3, StoresConstants.END_DATE);

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get Store Items
                    StoreReportModel storeReportPeriodicModel = new StoreReportModel();
                    storeReportPeriodicModel.setItemCode(resultSet.getString("item_no"));
                    storeReportPeriodicModel.setItemNameReport(resultSet.getString("item_name"));
                    storeReportPeriodicModel.setTotalQtyInStartedPoint(resultSet.getString("SBal"));
                    storeReportPeriodicModel.setTotalQtyIncome(resultSet.getString("QtyIN"));
                    storeReportPeriodicModel.setTotalQtyOutcome(resultSet.getString("QtyOUT"));
                    storeReportPeriodicModel.setTotalQtyEndPoint(resultSet.getString("FQty"));
                    storeReportPeriodicModelArrayList.add(storeReportPeriodicModel);
//
//
                    System.out.println("itemCode is:   " + resultSet.getString("item_no"));
                    System.out.println("itemNameReport is:   " + resultSet.getString("item_name"));
                    System.out.println("QtyInStartedPoint is:   " + resultSet.getString("SBal"));
                    System.out.println("tTotalQtyIncome is:   " + resultSet.getString("QtyIN"));
                    System.out.println("talQtyOutcome is:   " + resultSet.getString("QtyOUT"));
                    System.out.println("TotalQtyEndPoint is:   " + resultSet.getString("FQty"));


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
