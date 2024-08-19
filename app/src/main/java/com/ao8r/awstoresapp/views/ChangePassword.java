package com.ao8r.awstoresapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.repository.ChangeUserPassword;
import com.ao8r.awstoresapp.services.InternetConnection;

public class ChangePassword extends AppCompatActivity {

    private EditText currentPasswordEditText, newPasswordEditText;
    private Button changePasswordButton;
    private String currentPassword, newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //declare views
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        //set click listener
        changePasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currentPassword = currentPasswordEditText.getText().toString().trim();
                newPassword = newPasswordEditText.getText().toString().trim();


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
                    if (currentPassword.isEmpty() || newPassword.isEmpty()) {
                        CustomToast.customToast(getApplicationContext(), "فضلا أكمل البيانات");
                    } else {

                        ChangeUserPassword.changeUserPassword(
                                getApplicationContext(),
                                currentPassword,
                                newPassword);
                        CustomToast.customToast(getApplicationContext(), "Navigate to Login Screen");
                        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }
}