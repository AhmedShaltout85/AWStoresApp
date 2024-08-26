package com.ao8r.awstoresapp.repository;

import static com.ao8r.awstoresapp.utils.StoresConstants.DB_NAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.HOST;
import static com.ao8r.awstoresapp.utils.StoresConstants.PASSWORD;
import static com.ao8r.awstoresapp.utils.StoresConstants.PORT;
import static com.ao8r.awstoresapp.utils.StoresConstants.SRC_DRIVER;
import static com.ao8r.awstoresapp.utils.StoresConstants.USERNAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.connection;

import android.os.StrictMode;

import com.ao8r.awstoresapp.utils.StoresConstants;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public  class GetAllUserRequestInfoByEmpID {

    public static void getAllUserRequestInfoByEmpID(String empId) {

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
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
