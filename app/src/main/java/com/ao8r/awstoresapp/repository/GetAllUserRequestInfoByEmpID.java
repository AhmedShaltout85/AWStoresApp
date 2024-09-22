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

import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import es.dmoral.toasty.Toasty;

public  class GetAllUserRequestInfoByEmpID {

    public static void getAllUserRequestInfoByEmpID(Context context, String empId) {
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //Import mssql driver
            Class.forName(SRC_DRIVER);

            //create connection
            connection = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://"+HOST+":"+PORT+"/"+DB_NAME
                    , USERNAME, PASSWORD);

            if (connection == null) {
                System.out.println("connection is null");
                Toasty.error(context, "عفو لايمكن الأتصال بالخادم", Toasty.LENGTH_SHORT, true).show();
            } else {
                String query = "SELECT * FROM Mob_User_Request WHERE Emp_ID = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, empId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Do something with the encrypted string
//                    String Upass = resultSet.getString("UPass");
//                    System.out.println("encrypted password = " + Upass);
//                    String EncryptedPass = EncryptionUtil.decrypt(Upass);
//                    System.out.println("decrypted password = " + EncryptedPass);
                    //
                    //get emp info from mob_user_request table by emp id
                    StoresConstants.EMP_NAME = resultSet.getString("Emp_Name");
                    StoresConstants.EMP_MOBILE = resultSet.getString("Emp_Mobile");
                    StoresConstants.EMP_JOB = resultSet.getString("Emp_Job");
                    StoresConstants.EMP_LOCATION = resultSet.getString("Emp_Location");
                    StoresConstants.EMP_USERNAME = resultSet.getString("UName");
                    StoresConstants.EMP_ID = resultSet.getString("Emp_ID");

                    System.out.println(StoresConstants.EMP_ID);
                    System.out.println(StoresConstants.EMP_USERNAME);
                    System.out.println(StoresConstants.EMP_MOBILE);
                    System.out.println(StoresConstants.EMP_JOB);
                    System.out.println(StoresConstants.EMP_LOCATION);
                    System.out.println(StoresConstants.EMP_NAME);

//                    CustomToast.customToast(context, "تم جلب بيانات الموظف بنجاح ✅");
                    Toasty.info(context, "تم جلب بيانات الموظف بنجاح ", Toasty.LENGTH_SHORT, true).show();
                }else {
                    StoresConstants.EMP_USERNAME = "";
                    StoresConstants.EMP_MOBILE = "";
                    StoresConstants.EMP_JOB = "";
                    StoresConstants.EMP_LOCATION = "";
                    StoresConstants.EMP_NAME = "";
//                    CustomToast.customToast(context, "لايوجد بيانات لهذا الموظف ❌");
                    Toasty.warning(context, "لايوجد بيانات لهذا الموظف ", Toasty.LENGTH_SHORT, true).show();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
//            CustomToast.customToast(context, "فضلا أدخل بيانات الموظف بشكل صحيح ❌");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
