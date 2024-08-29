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
import com.ao8r.awstoresapp.services.InternetConnection;
import com.ao8r.awstoresapp.utils.EncryptionUtil;
import com.ao8r.awstoresapp.utils.StoresConstants;

public class ForgetUserPasswordScreen extends AppCompatActivity {
    private EditText empIdEditText, uNameEditText, empMobileEditText, uPassEditText;
    private String empId, uName, empMobile, Upass;
    private Button forgetUserPasswordButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forget_user_password_screen);

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
                CustomToast.customToast(ForgetUserPasswordScreen.this, "Forget Password Page");
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
                    CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                } else {
                    // Not Available...
                    CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

                }

                System.out.println("Forget Password button clicked");

                try {
                    if (empId.isEmpty() &&
                            uName.isEmpty() &&
                            empMobile.isEmpty() &&
                            Upass.isEmpty()) {

                        CustomToast.customToast(getApplicationContext(), "من فضلك ادخل جميع البيانات");
                    } else {


                        System.out.println("Forget Password button clicked start");
                        ForgetUserPassword.forgetUserPassword(getApplicationContext(),
                                empId,
                                empMobile,
                                uName,
//                                Upass);
                                EncryptionUtil.encrypt(Upass));
                        CustomToast.customToast(getApplicationContext(), "تم إرسال الطلب بنجاح");
                        System.out.println("Forget Password button clicked end");


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
                    e.getStackTrace();
                    CustomToast.customToast(getApplicationContext(), "من فضلك ادخل البيانات الصحيحة");
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