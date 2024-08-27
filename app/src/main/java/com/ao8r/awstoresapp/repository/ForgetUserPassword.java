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

//
//public class ForgetUserPassword {
//static Connection connection;
//
//    public static void forgetUserPassword(Context context, String uPass, String uName, String empId, String empMobile) {
//        try {
////            connection = ConnectionHelper.getConnection();
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//            //Import mssql driver
//            Class.forName(SRC_DRIVER);
//
//            //create connection
//            connection = DriverManager.getConnection(
//                    "jdbc:jtds:sqlserver://" + HOST + ":" + PORT + "/" + DB_NAME
//                    , USERNAME, PASSWORD);
//
//            if (connection == null) {
//                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
//            } else {
//
//                //Query
//                //"UPDATE Mob_User_Request" +
//                //                        "SET UPass = ?" +
//                //                        "WHERE " +
//                //                        "Emp_ID = ? " +
//                //                        "AND " +
//                //                        "UName = ? " +
//                //                        "AND " +
//                //                        "Emp_Mobile = ?";
//
//                System.out.println("before update password query ----from repository");
//                String updateQuery =
//                        "UPDATE Mob_User_Request" +
//                                "SET UPass = ?" +
//                                "WHERE " +
//                                "Emp_ID = ? " +
//                                "AND " +
//                                "UName = ? " +
//                                "AND " +
//                                "Emp_Mobile = ?";
//
//
//                PreparedStatement statement = connection.prepareStatement(updateQuery);
//
//                System.out.println("after update password query----from repository");
//
//                statement.setString(1, uPass);
//                statement.setString(2, empId);
//                statement.setString(3, uName);
//                statement.setString(4, empMobile);
//
//                ResultSet resultSet = statement.executeQuery();
//                System.out.println("after update password query-from repository");
//
//                if (resultSet.next()) {
//                    System.out.println("success update password query");
//                    String message = resultSet.getString("Message");
//                    System.out.println(message);
//                    CustomToast.customToast(context, "تم تغيير كلمة المرور بنجاح");
//                }
//
//
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            CustomToast.customToast(context, "فضلا, برجاء أدخال بيانات صحيحة  ");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }
//}
public class ForgetUserPassword {
//    static Connection connection;
//    private static final String HOST = "41.33.226.212";
//    private static final int PORT = 5010;
//    private static final String DB_NAME = "awco";
//    private static final String USERNAME = "awco";
//    private static final String PASSWORD = "awco";
//    private static final String SRC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";

    public static void forgetUserPassword(Context context,
                                   String empId,
                                   String empMobile,
                                   String uName,
                                   String uPass) {
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
                //Query for forget password

//                String updateQuery ="EXEC [dbo].[Forget_Password] " +
//                        "@UPass = ?," +
//                        "@UName = ?, " +
//                        "@Emp_Mobile = ?, " +
//                        "@Emp_ID = ?";

                String updateQuery = "{CALL [dbo].[Forget_Password](?,?,?,?)}";

//
                CallableStatement statement = connection.prepareCall(updateQuery);

                statement.setString(1, uPass);
                statement.setString(2, empId);
                statement.setString(3, uName);
                statement.setString(4, empMobile);


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


