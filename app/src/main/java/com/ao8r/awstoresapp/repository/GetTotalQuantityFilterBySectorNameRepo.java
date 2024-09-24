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

public class GetTotalQuantityFilterBySectorNameRepo {

    static Connection connection;

    public static double getSumTotalQuantityFilterBySectorName(Context context) {
        double tQty = 0.0;
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();
            } else {

                //Query
//                Select Distinct A.item_no,(Select I.[item name] From itemmain I Where I.[item no] = A.item_no) ItemName,(SUM(A.in_quen)- SUM(A.out_quen))Qty
//                From azonat A
//                Where A.item_no = '3792202'
//                Group By A.item_no

//
//                String updateQuery = "SELECT DISTINCT A.item_no," +
//                        "(SELECT I.[item name] FROM itemmain I WHERE I.[item no] = A.item_no) itemName," +
//                        "ROUND((SUM(A.in_quen)- SUM(A.out_quen)),2) totalQuantity\n" +
//                        "FROM azonat A\n" +
//                        "WHERE A.item_no = ?\n" +
//                        "GROUP BY A.item_no ;";
                String updateQuery =
//                        "Select Distinct A.item_no," +
//                        "  (dbo.ItemName(A.item_no)) ItemName," +
//                        "  Round((SUM(A.in_quen)- SUM(A.out_quen)),2)totalQuantity\n" +
//                        "  From azonat A\n" +
//                        "  Where A.item_no = ?\n" +
////                        "  AND A.store_num = CASE WHEN @StoreNo = 0 THEN A.store_num ELSE @StoreNo END\n" +
//                        "  Group By A.item_no";
                        "EXEC [dbo].[ItemAllQty_Sector] @ItemNo = ?, @StoreNo = 0, @SectorName = ?";

                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setString(1, String.valueOf(StoresConstants.ITEM_NUMBER));
                statement.setString(2, StoresConstants.STORE_SECTOR);

                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {

                    tQty = resultSet.getDouble("Qty");
//                    StoresConstants.ITEM_NAME = resultSet.getString("itemName");


//

                    System.out.println("totalQuantity is:   " + tQty);
                    CustomToast.customToast(context, String.valueOf(tQty));


                } else {
                    CustomToast.customToast(context, "Can not get Total Value");
//                    Toasty.error(context, "Can not get Total Value", Toast.LENGTH_SHORT, true).show();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return tQty;
    }
}


