package com.ao8r.awstoresapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.repository.ChangeUserPassword;
import com.ao8r.awstoresapp.services.InternetConnection;
import com.ao8r.awstoresapp.utils.StoresConstants;

public class ChangePassword extends AppCompatActivity {

    private EditText currentPasswordEditText, newPasswordEditText, confrimPasswordEditText;
    private Button changePasswordButton;
    private String currentPassword, newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //declare views
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confrimPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        //set click listener
        changePasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currentPassword = currentPasswordEditText.getText().toString().trim();
                newPassword = newPasswordEditText.getText().toString().trim();
                confirmPassword = confrimPasswordEditText.getText().toString().trim();


                //        hide keyboard after typed
                try {

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {
                    e.getStackTrace();
                }


                if (InternetConnection.checkConnection(getApplicationContext())) {
                    // Its Available...
                    CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                } else {
                    // Not Available...
                    CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

                }


                try {
                    if (
                            currentPassword.isEmpty() &&
                                    newPassword.isEmpty() &&
                                    confirmPassword.isEmpty()
                    ) {
                        CustomToast.customToast(getApplicationContext(), "البيانات المدرجة غير صحيحة");

//
//                        ChangeUserPassword.changeUserPassword(
//                                getApplicationContext(),
//                                currentPassword,
//                                newPassword);
//
//                        System.out.println("currentPassword = " + currentPassword + "\n " + "newPassword =  " + newPassword);
//
////                        CustomToast.customToast(getApplicationContext(), "إعادة التوجيهه الى صحفه دخول المستخدمين");
//                        CustomLoader.customLoader(getApplicationContext(), "إعادة التوجيهه الى صحفه دخول المستخدمين");
//
//                        //redirect to login page
//                        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
//                        startActivity(intent);

                    } else if (!confirmPassword.equals(newPassword)) {
                        CustomToast.customToast(getApplicationContext(), "عفوا كلمة المرور الجديدة غير مطابقة");

                    } else if (!StoresConstants.CURRENT_PASSWORD.equals(currentPassword)) {
                        CustomToast.customToast(getApplicationContext(), "عفوا كلمة المرور الحالية غير مطابقة");

                    } else {

                        ChangeUserPassword.changeUserPassword(
                                getApplicationContext(),
                                currentPassword,
                                newPassword);

                        System.out.println("currentPassword = " + currentPassword + "\n " + "newPassword =  " + newPassword);

                        CustomToast.customToast(getApplicationContext(), "إعادة التوجيهه الى صحفه دخول المستخدمين");

                        new Handler().postDelayed(new Runnable() {
                            @Override public void run() {
                                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                                startActivity(intent);
                                finish(); } }, 4000);

//                        //redirect to login page
//                        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
//                        startActivity(intent);

//                        CustomToast.customToast(getApplicationContext(), "البيانات المدرجة غير صحيحة");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CustomToast.customToast(getApplicationContext(), "بيانات المستخدم غير صحيحة");
                }

            }
        });
    }
}