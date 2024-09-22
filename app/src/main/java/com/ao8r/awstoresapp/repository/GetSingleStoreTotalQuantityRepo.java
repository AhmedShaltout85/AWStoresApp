package com.ao8r.awstoresapp.repository;

import android.content.Context;
import android.widget.Toast;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.models.AzonateModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class GetSingleStoreTotalQuantityRepo {
    static Connection connection;

    public static void getSingleStoreTotalQuantity(Context context,int itemNo, ArrayList<AzonateModel> azonateList) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();
            } else {

                //Query
//                SELECT DISTINCT A.item_no,
//                (SELECT I.[item name] FROM itemmain I WHERE I.[item no] = A.item_no) itemName,
//                (SUM(A.in_quen)- SUM(A.out_quen))storeTotalQuantity,
//                A.store_num,
//                (SELECT S.store_name FROM stores_data S WHERE S.store_num = A.store_num) storeName,
//                MAX(A.t_date) LastDate
//                FROM azonat A
//                WHERE A.item_no = ?
//                GROUP BY A.item_no,A.store_num
//
//                String updateQuery = "SELECT DISTINCT A.item_no,\n" +
//                        "(SELECT I.[item name] FROM itemmain I WHERE I.[item no] = A.item_no) itemName,\n" +
//                        "ROUND((SUM(A.in_quen)- SUM(A.out_quen)),2) storeTotalQuantity,\n" +
//                        "A.store_num,\n" +
//                        "(SELECT S.store_name FROM stores_data S WHERE S.store_num = A.store_num) storeName,\n" +
//                        "MAX(A.t_date) LastDate\n"+>>OLD
//                        "(SELECT MAX(t_date) FROM azonat WHERE store_num = A.store_num) LastDate\n"+
//                        "FROM azonat A\n" +
//                        "WHERE A.item_no = ?\n" +
//                        "GROUP BY A.item_no,A.store_num\n"+
//                        "HAVING ROUND((SUM(A.in_quen)- SUM(A.out_quen)),2) > 0\n"+
//                        "ORDER BY storeTotalQuantity DESC";
                String updateQuery =
//                        " Select Distinct A.item_no, " +
//                        " (dbo.ItemName(A.item_no)) ItemName," +
//                        " Round((SUM(A.in_quen)- SUM(A.out_quen)),2)storeTotalQuantity, " +
//                        "  A.store_num, " +
//                        "  (Select S.store_name From stores_data S Where S.store_num = A.store_num) StoreName, " +
////                        "  MAX(A.t_date) LastDate\n" +
//                        " (SELECT MAX(t_date) FROM azonat WHERE store_num = A.store_num) LastDate\n"+
//                        "  From azonat A\n" +
//                        "  Where A.item_no = ?\n" +
////                        "  AND A.store_num = CASE WHEN @StoreNo = 0 THEN A.store_num ELSE @StoreNo END\n" +
//                        "  Group By A.item_no,A.store_num\n" +
//                        "  Having Round((SUM(A.in_quen)- SUM(A.out_quen)),2) <> 0\n" +
//                        "  Order By storeTotalQuantity Desc";
                  "EXEC [dbo].[ItemStoreQty] @ItemNo = ?, @StoreNo = 0";

                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setInt(1, itemNo);

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get azonate
                    AzonateModel azonateModel = new AzonateModel();
                    azonateModel.setItemName(resultSet.getString("itemName"));
                    azonateModel.setStoreName(resultSet.getString("storeName"));
                    azonateModel.setLastDateSend(resultSet.getString("LastDate"));
                    azonateModel.setStoreTotalQuantity(String.valueOf(resultSet.getDouble("Qty")));
//                   add azonate to list
                    azonateList.add(azonateModel);


//
//                    StoresConstants.STORE_TOTAL_QUANTITY = resultSet.getInt("Qty");
//                    StoresConstants.STORE_NUMBER = resultSet.getInt("storeNumber");
//                    StoresConstants.ITEM_NAME = resultSet.getString("itemName");
//                    StoresConstants.STORE_NAME = resultSet.getString("storeName");


//
//
                    System.out.println("storeTotalQuantity is:   " + String.valueOf(resultSet.getDouble("Qty")));
                    System.out.println("itemName is:   " + resultSet.getString("itemName"));
                    System.out.println("storeName is:   " + resultSet.getString("storeName"));
                    System.out.println("lastDateSend is:   " + resultSet.getString("LastDate"));
//                    CustomToast.customToast(context,  StoresConstants.STORE_NAME + "===>"+StoresConstants.STORE_TOTAL_QUANTITY);


                }
;

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
