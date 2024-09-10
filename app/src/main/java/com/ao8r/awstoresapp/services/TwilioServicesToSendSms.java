package com.ao8r.awstoresapp.services;

import static com.ao8r.awstoresapp.utils.StoresConstants.ACCOUNT_SID;
import static com.ao8r.awstoresapp.utils.StoresConstants.AUTH_TOKEN;
import static com.ao8r.awstoresapp.utils.StoresConstants.DB_NAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.HOST;
import static com.ao8r.awstoresapp.utils.StoresConstants.PASSWORD;
import static com.ao8r.awstoresapp.utils.StoresConstants.PORT;
import static com.ao8r.awstoresapp.utils.StoresConstants.SENDER_PHONE_NUMBER;
import static com.ao8r.awstoresapp.utils.StoresConstants.SRC_DRIVER;
import static com.ao8r.awstoresapp.utils.StoresConstants.USERNAME;

import android.content.Context;
import android.os.StrictMode;

import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TwilioServicesToSendSms {
// Find your Account Sid and Token at console.twilio.com

    static Connection connection;

    //Initialize the Twilio client and send a message
    public static void sendSmsUsingTwilio(
            Context context,
            String receiverPhoneNumb,
            String smsBodyText) {


        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //Import mssql driver
            Class.forName(SRC_DRIVER);

            //create connection
            connection = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://" + HOST + ":" + PORT + "/" + DB_NAME
                    , USERNAME, PASSWORD);

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {

                System.out.println("before sending sms");

                //Create the message
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                System.out.println(ACCOUNT_SID + "\n" + AUTH_TOKEN);


                System.out.println(receiverPhoneNumb + "\n" + SENDER_PHONE_NUMBER);

                Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(receiverPhoneNumb), // To number
                        new PhoneNumber(SENDER_PHONE_NUMBER),   // From number
                        smsBodyText          // SMS body
                ).create();

                System.out.println(receiverPhoneNumb + "\n" + SENDER_PHONE_NUMBER + "\n" + smsBodyText);

                System.out.println("after sending sms");

                System.out.println(message.getSid());
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            e.getMessage();
            System.out.println(e.getMessage());
        }
    }
    ///



}




//https://www.twilio.com/docs/sms/send-messages
//https://www.twilio.com/console/sms/settings
//must have twilio account
//https://login.twilio.com/u/signup?state=hKFo2SBZLVBjUEZyVHRyQ1lyeG9VbFQyOW5zRkNPMkQ4c1NQMaFur3VuaXZlcnNhbC1sb2dpbqN0aWTZIDE2TGUwaFBjZVR6ODFlcC1EcFdTTS1FNFFWMmZuMGx2o2NpZNkgTW05M1lTTDVSclpmNzdobUlKZFI3QktZYjZPOXV1cks
//https://github.com/AhmedShaltout85/twilio-java?tab=readme-ov-file
//https://www.twilio.com/docs
// twilio account user : awcoah@gmail.com pass: A08R@ahraahabsha






