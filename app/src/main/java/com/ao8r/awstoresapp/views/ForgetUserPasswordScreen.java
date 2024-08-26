package com.ao8r.awstoresapp.views;

import android.annotation.SuppressLint;
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
import com.ao8r.awstoresapp.repository.ForgetUserPassword;

public class ForgetUserPasswordScreen extends AppCompatActivity {
    private EditText empIdEditText, uNameEditText, empMobileEditText, UpassEditText;
    private String empId, uName, empMobile, Upass;
    private Button forgetUserPasswordButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forget_user_password_screen);

        forgetUserPasswordButton = findViewById(R.id.forgetPasswordButton);
        empIdEditText = findViewById(R.id.forgetEmpIdEditText);
        uNameEditText = findViewById(R.id.forgetUserNameEditText);
        empMobileEditText = findViewById(R.id.forgetMobileEditText);
        UpassEditText = findViewById(R.id.forgetPasswordEditText);

        forgetUserPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data
                empId = empIdEditText.getText().toString().trim();
                uName = uNameEditText.getText().toString().trim();
                empMobile = empMobileEditText.getText().toString().trim();
                Upass = UpassEditText.getText().toString().trim();

                System.out.println(empId +
                        " \n" + uName +
                        "\n " + empMobile +
                        "\n " + Upass);

                if (!empId.isEmpty() && !uName.isEmpty() && !empMobile.isEmpty() && !Upass.isEmpty()) {

                    //call forget user password method
                    ForgetUserPassword.forgetUserPassword(Upass, uName, empId, empMobile);

                    CustomToast.customToast(getApplicationContext(), "تم إرسال الطلب بنجاح");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 3000);
                } else {
                    CustomToast.customToast(ForgetUserPasswordScreen.this, "من فضلك ادخل جميع البيانات");
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