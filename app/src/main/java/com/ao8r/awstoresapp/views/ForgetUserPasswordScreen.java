package com.ao8r.awstoresapp.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.repository.ForgetUserPassword;
import com.ao8r.awstoresapp.repository.GetAllUserInfoForgetPasswordByEmpID;
import com.ao8r.awstoresapp.services.InternetConnection;
import com.ao8r.awstoresapp.utils.EncryptionUtil;
import com.ao8r.awstoresapp.utils.StoresConstants;

import es.dmoral.toasty.Toasty;

public class ForgetUserPasswordScreen extends AppCompatActivity {
    private EditText empIdEditText, uNameEditText, empMobileEditText, uPassEditText;
    private String empId, uName, empMobile, Upass;
    private Button forgetUserPasswordButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forget_user_password_screen);

        //add sms permission//TODO: //31-08-2024
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.SEND_SMS}, 1);
//        }

        // Find the toolbar view inside the activity layout
        Toolbar toolbarAdvancedSearch = findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarAdvancedSearch);
////       set title
        setTitle(StoresConstants.FORGOT_USER_PASSWORD_TITLE);

        toolbarAdvancedSearch.setTitleTextColor(Color.WHITE);
//        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        back Icon change its color
        toolbarAdvancedSearch.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);


        forgetUserPasswordButton = findViewById(R.id.forgetPasswordButton);
        empIdEditText = findViewById(R.id.forgetEmpIdEditText);
        uNameEditText = findViewById(R.id.forgetUserNameEditText);
        empMobileEditText = findViewById(R.id.forgetMobileEditText);
        uPassEditText = findViewById(R.id.forgetPasswordEditText);

        empIdEditText.requestFocus();

        forgetUserPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get data
                empId = empIdEditText.getText().toString().trim();
                uName = uNameEditText.getText().toString().trim();
                empMobile = empMobileEditText.getText().toString().trim();
                Upass = uPassEditText.getText().toString().trim();

                System.out.println(empId +
                        "\n" + uName +
                        "\n" + empMobile +
                        "\n" + Upass);


                if (InternetConnection.checkConnection(getApplicationContext())) {
                    // Its Available...
                    CustomToast.customToast(getApplicationContext(), "متصل بالانترنت✅");
//                    Toasty.success(getApplicationContext(), "متصل بالانترنت✅", Toasty.LENGTH_SHORT).show();
                } else {
                    // Not Available...
//                    Toasty.error(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت ❌", Toasty.LENGTH_SHORT).show();
                    CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت ❌");

                }

                System.out.println("Forget Password button clicked");

//                try {
                //
                if (empId.isEmpty() &&
                        uName.isEmpty() &&
                        empMobile.isEmpty() &&
                        Upass.isEmpty()) {
                    Toasty.warning(getApplicationContext(), "من فضلك ادخل جميع البيانات ", Toasty.LENGTH_SHORT, true).show();
//                    CustomToast.customToast(getApplicationContext(), "من فضلك ادخل جميع البيانات ❌");


                } else {

                    System.out.println("Forget Password button clicked start");

                    try {
                        //
                        GetAllUserInfoForgetPasswordByEmpID.getAllUserRequestInfoByEmpID(getApplicationContext(), empId);
//                        GetAllUserRequestInfoByEmpID.getAllUserRequestInfoByEmpID(getApplicationContext(), empId);
                        //
                        if (!StoresConstants.EMP_ID.equals(empId)) {
//                            CustomToast.customToast(getApplicationContext(), "رقم الموظف غير صحيح ❌");
                            Toasty.error(getApplicationContext(), "رقم الموظف غير صحيح", Toasty.LENGTH_SHORT, true).show();
                        } else if (!StoresConstants.EMP_MOBILE.equals(empMobile)) {
                            Toasty.error(getApplicationContext(), "رقم الهاتف غير صحيح ", Toasty.LENGTH_SHORT, true).show();
//                            CustomToast.customToast(getApplicationContext(), "رقم الهاتف غير صحيح ❌");
                        } else if (!StoresConstants.EMP_USERNAME.equals(uName)) {
                            Toasty.error(getApplicationContext(), "اسم المستخدم غير صحيح ", Toasty.LENGTH_SHORT, true).show();
//                            CustomToast.customToast(getApplicationContext(), "اسم المستخدم غير صحيح ❌");
                        }else {
                            //Forget Password call
                            ForgetUserPassword.forgetUserPassword(getApplicationContext(),
                                    empId,
                                    empMobile,
                                    uName,
                                    EncryptionUtil.encrypt(Upass));

//                        CustomToast.customToast(getApplicationContext(), "تم تعيين كلمة المرور بنجاح ✅");

                            Toasty.success(getApplicationContext(), "تم تعيين كلمة المرور بنجاح ", Toasty.LENGTH_SHORT, true).show();
                            System.out.println("Forget Password button clicked end");
////
                            //send sms using twilio
//                        TwilioServicesToSendSms.sendSmsUsingTwilio(getApplicationContext(),"+201032743609",
//                                "username: ahmed"+"\n"+
//                                "password: 12345");

                            //send sms using fast2sms
//                        SmsSender.sendSms("+201032743609", "welcome to Stores App \n your_pass: 123456");


                            //TODO://31-08-2024 send sms
//                        SmsManager smsManager = SmsManager.getDefault();
//                        smsManager.sendTextMessage("+201277175547", null, "username: ahmed"+"\n"+
//                                "password: 12345" +"\n", null, null);

//call forget user password method


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 2000);
                        }
                    } catch (Exception e) {
                        e.getMessage();
                        System.out.println(e.getMessage());
//                        CustomToast.customToast(getApplicationContext(), "من فضلك ادخل البيانات الصحيحة ❌");

                    }

                }


                //        hide keyboard after typed
                try {

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });


    }


}