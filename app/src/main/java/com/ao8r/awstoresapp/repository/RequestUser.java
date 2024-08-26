package com.ao8r.awstoresapp.repository;


import static com.ao8r.awstoresapp.utils.StoresConstants.DB_NAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.HOST;
import static com.ao8r.awstoresapp.utils.StoresConstants.PASSWORD;
import static com.ao8r.awstoresapp.utils.StoresConstants.PORT;
import static com.ao8r.awstoresapp.utils.StoresConstants.SRC_DRIVER;
import static com.ao8r.awstoresapp.utils.StoresConstants.USERNAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.connection;

import android.content.Context;
import android.os.StrictMode;

import com.ao8r.awstoresapp.customiz_widgets.CustomToast;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RequestUser {
//    static Connection connection;
//    private static final String HOST = "41.33.226.212";
//    private static final int PORT = 5010;
//    private static final String DB_NAME = "awco";
//    private static final String USERNAME = "awco";
//    private static final String PASSWORD = "awco";
//    private static final String SRC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";

    public static void requestUser(Context context,
                                   String empId,
                                   String empName,
                                   String empLocation,
                                   String empJob,
                                   String empMobile,
                                   String uName,
                                   String uPass,
                                   String notes) {
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //Import mssql driver
            Class.forName(SRC_DRIVER);

            //ceate connection
            connection = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://"+HOST+":"+PORT+"/"+DB_NAME
                    , USERNAME, PASSWORD);

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {

                System.out.println("before insert procedure");
                //Query
                //  -- Insert Mob_User_Request

                //EXEC	[dbo].[Add_Request]
                //		@Emp_ID = N'55052',
                //		@Emp_Name = N'عمر محمد فرج محمود',
                //		@Emp_Location = N'كوم الدكة',
                //		@Emp_Job = N'مهندس تكنولوجيا معلومات',
                //		@Emp_Mobile = N'01280772550',
                //		@UName = N'Omar',
                //		@UPass = N'123654',
                //		@Notes = N'كل الصلاحيات';


//
//                String updateQuery ="EXEC [dbo].[Add_Request] " +
//                        "@Emp_ID = ?, " +
//                        "@Emp_Name = ?, " +
//                        "@Emp_Location = ?, " +
//                        "@Emp_Job = ?, " +
//                        "@Emp_Mobile = ?, " +
//                        "@UName = ?, " +
//                        "@UPass = ?, " +
//                        "@Notes = ?";

                String updateQuery = "{CALL [dbo].[Add_Request](?,?,?,?,?,?,?,?)}";

//
                CallableStatement statement = connection.prepareCall(updateQuery);

                statement.setString(1, empId);
                statement.setString(2, empName);
                statement.setString(3, empLocation);
                statement.setString(4, empJob);
                statement.setString(5, empMobile);
                statement.setString(6, uName);
                statement.setString(7, uPass);
                statement.setString(8, notes);

               Boolean result = statement.execute();

                System.out.println("after insert procedure execution");

                if(!result){
                    System.out.println("stored procedure executed successfully");
                }else {
                    System.out.println("stored procedure executed failed");
                }



            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            CustomToast.customToast(context, "فضلا, برجاء أدخال بيانات صحيحة  ");
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




