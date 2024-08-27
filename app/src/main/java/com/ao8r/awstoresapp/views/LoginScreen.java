package com.ao8r.awstoresapp.views;

import static com.ao8r.awstoresapp.utils.StoresConstants.URL_CONNECT_LINK;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.customiz_widgets.ReadWriteFileFromInternalMem;
import com.ao8r.awstoresapp.repository.LoginRepo;
import com.ao8r.awstoresapp.services.InternetConnection;
import com.ao8r.awstoresapp.utils.StoresConstants;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LoginScreen extends AppCompatActivity {

    //    declare variables
    CardView topCardView;
    Button loginUser;
    CheckBox rememberMe;
    EditText loginName;
    EditText loginPassword;
    String password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

//        declare CardsView in LoginRepo Page With Rounded Corners
        topCardView = findViewById(R.id.loginCardView);
        topCardView.setBackgroundResource(R.drawable.top_login_card_view_rounded_corners);
        bottomSheetDialog = new BottomSheetDialog(this); // assign BottomSheet
        createBottomSheetDialog(); // create bottomSheetDialog Method
        bottomSheetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

//        assign values to objects
        loginUser = findViewById(R.id.userLoginButton); //login button
        loginName = findViewById(R.id.userNameEditText); //userName EditText
        loginPassword = findViewById(R.id.passwordEditText); // password EditText
        rememberMe = findViewById(R.id.rememberMeCheckBox); // remember checkBox

        //////////////////////////////////// remember login /////////////////
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        /////////////////To get Stored Data/////////////////////////////////

        String name = sharedPreferences.getString("name", " ");
        String passwords = sharedPreferences.getString("password", "");
        ////////////////////////////////////////////////////////////////////

        loginPassword.setText(passwords);
        loginName.setText(name);
///
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                }, REQUEST_CODE
        );
////

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get data from user TextInput
                StoresConstants.LOGIN_USER = loginName.getText().toString();
                password = loginPassword.getText().toString();
                StoresConstants.CURRENT_PASSWORD = password.trim();
                System.out.println("Login Password:"+StoresConstants.CURRENT_PASSWORD);
//                loginPassword.requestFocus();
                // check user input data validation

                ///////////////To Store Data////////////////////////////
                if (rememberMe.isChecked()) {

                    editor.putString("name", StoresConstants.LOGIN_USER);
                    editor.putString("password", password);
                    editor.commit();
                } else {

                    editor.putString("name", "");
                    editor.putString("password", "");

                    editor.commit();
                }
                try {
                    //TODO: HANDLE ERROR WHEN SERVER IS OFFLINE(02-04-2023)
                    ////////////////////////////////////////////
//                TODO: get BaseIp
                    if (URL_CONNECT_LINK == null || URL_CONNECT_LINK.isEmpty()) {
                        URL_CONNECT_LINK = ReadWriteFileFromInternalMem.getIpFromFile();
                    } else {
                        URL_CONNECT_LINK = "41.33.226.212";
                    }
                    /////////////////////////////////////////////////////////////
                } catch (Exception exception) {
                    exception.getStackTrace();
                    CustomToast.customToast(getApplicationContext(), "الخادم فى وضع OFFLINE");
                }


                //////////////////////////////////////////////////////////////
//                Check internet Connectivity

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    // Its Available...
                    CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                } else {
                    // Not Available...
                    CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

                }

//                Check users Authority

                if (StoresConstants.LOGIN_USER.trim().length() == 0 || password.length() == 0) {
                    CustomToast.customToast(getApplicationContext(), "من فضلك أدخل البيانات");

                } else {

//              Send LoginRepo data to authorize
//
                    try {

                        LoginRepo.login(view.getContext(), password);
                    } catch (Exception e) {
                        CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
                    }

                }
            }
        });


    }

    private void createBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.custom_setting_bottom_sheet_dailog, null, false);
        Button addToSdFile = view.findViewById(R.id.addToSdFileIdButton);
        EditText getIpAddress = view.findViewById(R.id.getIpAddressEditText);

        addToSdFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String getUrl = getIpAddress.getText().toString().trim();
//              TODO: write ip
                ReadWriteFileFromInternalMem.generateNoteOnSD(getUrl);

                Toast.makeText(getApplicationContext(), getUrl, Toast.LENGTH_SHORT).show();


                getIpAddress.setText("");
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(view);
    }

    public void navToSetting(View view) {

        bottomSheetDialog.show();
    }

    // TODO: navToRequestNewUser
    public void navToRequestNewUser(View view) {
        Intent intent = new Intent(this, RequestNewUser.class);
        startActivity(intent);
    }

    // TODO: navToForgetPassword
    public void navToForgetPassword(View view) {
        Intent intent = new Intent(this, ForgetUserPasswordScreen.class);
        startActivity(intent);
    }
}