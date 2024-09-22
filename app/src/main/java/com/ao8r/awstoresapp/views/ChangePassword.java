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

import es.dmoral.toasty.Toasty;

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
//                    Toasty.error(getApplicationContext(), "متصل بالانترنت", Toasty.LENGTH_SHORT, true).show();
                } else {
                    // Not Available...
                    CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت ❌");
//                    Toasty.error(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت ❌", Toasty.LENGTH_SHORT, true).show();
                }


                try {
                    if (
                            currentPassword.isEmpty() &&
                                    newPassword.isEmpty() &&
                                    confirmPassword.isEmpty()) {

//                        CustomToast.customToast(getApplicationContext(), "البيانات المدرجة غير صحيحة ❌");
                        Toasty.error(getApplicationContext(), "البيانات المدرجة غير صحيحة ", Toasty.LENGTH_SHORT, true).show();
                    } else if (!confirmPassword.equals(newPassword)) {

//                        CustomToast.customToast(getApplicationContext(), "عفوا كلمة المرور الجديدة غير مطابقة ❌");
                        Toasty.error(getApplicationContext(), "عفوا كلمة المرور الجديدة غير مطابقة ", Toasty.LENGTH_SHORT, true).show();
                    } else if (!StoresConstants.CURRENT_PASSWORD.equals(currentPassword)) {

//                        CustomToast.customToast(getApplicationContext(), "عفوا كلمة المرور الحالية غير مطابقة ❌");
                        Toasty.error(getApplicationContext(), "عفوا كلمة المرور الحالية غير مطابقة", Toasty.LENGTH_SHORT, true).show();
                    } else {

                        ChangeUserPassword.changeUserPassword(
                                getApplicationContext(),
                                currentPassword,
                                newPassword);

                        System.out.println("currentPassword = " + currentPassword + "\n " + "newPassword =  " + newPassword);

//                        CustomToast.customToast(getApplicationContext(), "تم تغيير كلمة المرور بنجاح ✅" + "\n" + "سيتم تحويلك الى صحفه دخول المستخدمين");
                        Toasty.success(getApplicationContext(), "تم تغيير كلمة المرور بنجاح" , Toasty.LENGTH_SHORT, true).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //                        //redirect to login page
                                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    CustomToast.customToast(getApplicationContext(), "بيانات المستخدم غير صحيحة ❌");
                    Toasty.error(getApplicationContext(), "بيانات المستخدم غير صحيحة", Toasty.LENGTH_SHORT, true).show();
                }

            }
        });
    }

    public void navToMenu(View view) {
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

    //Go Back to MenuScreen//TODO:NOT REVIEWED (1-09-2024)
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ChangePassword.this, MenuScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}