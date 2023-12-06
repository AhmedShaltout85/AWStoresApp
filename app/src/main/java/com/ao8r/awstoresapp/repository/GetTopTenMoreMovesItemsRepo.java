package com.ao8r.awstoresapp.repository;

import android.content.Context;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.ao8r.awstoresapp.controller.ConnectionHelper;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetTopTenMoreMovesItemsRepo {
    static Connection connection;

    public static void getTopTenMoreMovesItemsRepo(Context context, AnyChartView anyChartViewV) {

        AnyChartView anyChartView;
        ArrayList<Integer> itmNoArr = new ArrayList<>();
        ArrayList<String> itmNmeArr = new ArrayList<>();
        ArrayList<Integer> countArr = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {

                //Query
//                -- Top 10 Item Moves
//                Select Top 10 COUNT(genral_Serial) C ,item_no,dbo.ItemName(Item_No) Item_Name
//                From azonat
//                Group By item_no
//                Order By C Desc
                ///
//                Select Top 10 COUNT(genral_Serial) C ,item_no,dbo.ItemName(Item_No) Item_Name
//                From azonat
//                Where convert(date, azn_date) between convert(date, GETDATE()-30) and convert(date, GETDATE())
//                Group By item_no,item_name
//                Order By C Desc

                String updateQuery =
//                        "SELECT TOP 10 COUNT(genral_Serial) C ," +
//                        "item_no," +
//                        "dbo.ItemName(Item_No) Item_Name\n" +
//                        "FROM azonat\n" +
//                        "Where convert(date, azn_date) between convert(date, GETDATE()-30) and convert(date, GETDATE())\n"+
//                        "GROUP BY item_no,item_name\n" +
//                        "ORDER BY C DESC";
                "EXEC [dbo].[TopTenMoves]";

                PreparedStatement statement = connection.prepareStatement(updateQuery);


                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get TOP 10 MOVES
                   int countNo = resultSet.getInt("C");
                    String item_name = resultSet.getString("Item_Name");
                    int itmNo = resultSet.getInt("item_no");

                    countArr.add(countNo);

                    itmNoArr.add(itmNo);
                    itmNmeArr.add(item_name);




                    System.out.println("Count is:   " + resultSet.getInt("C"));
                    System.out.println("ItemsNo is:   " + resultSet.getInt("item_no"));

//                    System.out.println("ItemName is:   " + resultSet.getString("Item_Name"));


                }
                anyChartView = anyChartViewV;
                Pie pie = AnyChart.pie();
                List<DataEntry> dataEntries = new ArrayList<>();
                for (int i= 0; i<countArr.size();i++){
                    dataEntries.add(new ValueDataEntry(itmNmeArr.get(i),countArr.get(i)));
                }
                pie.data(dataEntries);
                pie.title("الأكثر حركة فى المخازن");
                anyChartView.setChart(pie);


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

