package com.ao8r.awstoresapp.repository;

import android.content.Context;
import android.widget.Toast;

import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.models.AzonateModel;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class GetAllMatchItemsByNameWithFilterBySectorNameRepo {
    static Connection connection;

    public static void getAllMatchItemsByNameWithFilterBySectorNameRepo(Context context, String itemName, ArrayList<AzonateModel> azonateList) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toast.LENGTH_SHORT, true).show();
            } else {

                //Query
//                Select Distinct A.item_no,(Select I.[item name] From itemmain I Where I.[item no] = A.item_no) ItemName,
//                Round((SUM(A.in_quen)- SUM(A.out_quen)),2)Qty
//                From azonat A
//                Where A.item_name Like '%مسمار%'
//                AND A.store_num = CASE WHEN @StoreNo = 0 THEN A.store_num ELSE @StoreNo END
//                Group By A.item_no
//                Having Round((SUM(A.in_quen)- SUM(A.out_quen)),2) > 0
//                Order By ItemName Desc,Qty Desc

//                String updateQuery = "SELECT DISTINCT A.item_no,\n" +
//                        "(SELECT I.[item name] FROM itemmain I WHERE I.[item no] = A.item_no) itemName,\n" +
//                        "ROUND((SUM(A.in_quen)- SUM(A.out_quen)),2) storeTotalQuantity\n" +
//                        "FROM azonat A\n" +
//                        "WHERE A.item_name Like ?\n" +
//                        "GROUP BY A.item_no\n"+
//                        "HAVING ROUND((SUM(A.in_quen)- SUM(A.out_quen)),2) > 0\n"+
//                        "ORDER BY ItemName Desc, storeTotalQuantity DESC";
                String updateQuery =
//                        "  Select Distinct A.item_no," +
//                        "  (dbo.ItemName(A.item_no)) ItemName ," +
//                        "  Round((SUM(A.in_quen)- SUM(A.out_quen)),2)storeTotalQuantity\n" +
//                        "  From azonat A\n" +
//                        "  Where A.item_name Like ?\n" +
////                        "  AND A.store_num = CASE WHEN @StoreNo = 0 THEN A.store_num ELSE @StoreNo END\n" +
//                        "  Group By A.item_no\n" +
//                        "  Having Round((SUM(A.in_quen)- SUM(A.out_quen)),2) <> 0\n" +
//                        "  Order By ItemName Desc, storeTotalQuantity Desc";
                "EXEC [dbo].[MatchItemsByName_Sector] @ItemName = ?, @StoreNo = 0, @SectorName = ?";

                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setString(1, "%"+itemName+"%");
                statement.setString(2, StoresConstants.STORE_SECTOR);

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get azonate
                    AzonateModel azonateModel = new AzonateModel();
                    azonateModel.setItemName(resultSet.getString("itemName"));
                    azonateModel.setItemNumber(resultSet.getInt("item_no"));
                    azonateModel.setStoreTotalQuantity(resultSet.getString("storeTotalQuantity"));
//                   add azonate to list
                    azonateList.add(azonateModel);

//
//
                    System.out.println("storeTotalQuantity is:   " + String.valueOf(resultSet.getDouble("storeTotalQuantity")));
                    System.out.println("itemName is:   " + resultSet.getString("itemName"));
                    System.out.println("itemNo is:   " +resultSet.getInt("item_no"));


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
