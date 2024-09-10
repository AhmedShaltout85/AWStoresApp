package com.ao8r.awstoresapp.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.repository.GetAllUserRequestInfoByEmpID;
import com.ao8r.awstoresapp.repository.RequestUser;
import com.ao8r.awstoresapp.services.InternetConnection;
import com.ao8r.awstoresapp.utils.EncryptionUtil;
import com.ao8r.awstoresapp.utils.StoresConstants;

public class RequestNewUser extends AppCompatActivity {
    private String empId,
            empName,
            empLocation,
            empJob,
            empMobile,
            uName,
            uPass,
            notes,
            encryptedPassword;
    private EditText empIdEditText,
            empNameEditText,
            empLocationEditText,
            empJobEditText,
            empMobileEditText,
            uNameEditText,
            uPassEditText,
            notesEditText;
    private Button requestNewUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_user);

// Find the toolbar view inside the activity layout
        Toolbar toolbarAdvancedSearch = findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarAdvancedSearch);
////       set title
        setTitle(StoresConstants.REQUEST_NEW_USER_TITLE);

        toolbarAdvancedSearch.setTitleTextColor(Color.WHITE);
//        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        back Icon change its color
        toolbarAdvancedSearch.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);


        //declare views
        empIdEditText = findViewById(R.id.requestEmpIdEditText);
        empNameEditText = findViewById(R.id.requestEmpNameEditText);
        empLocationEditText = findViewById(R.id.requestUserLocationEditText);
        empJobEditText = findViewById(R.id.requestUserJobEditText);
        empMobileEditText = findViewById(R.id.requestEmpMobileEditText);
        uNameEditText = findViewById(R.id.requestUserNameEditText);
        uPassEditText = findViewById(R.id.requestPasswordEditText);
        notesEditText = findViewById(R.id.requestNotesEditText);

        requestNewUserButton = findViewById(R.id.requestNewUserButton);

        //set focus to first edit text
        empIdEditText.requestFocus();

//
// set on click listener
        requestNewUserButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                empId = empIdEditText.getText().toString().trim();
                empName = empNameEditText.getText().toString().trim();
                empLocation = empLocationEditText.getText().toString().trim();
                empJob = empJobEditText.getText().toString().trim();
                empMobile = empMobileEditText.getText().toString().trim();
                uName = uNameEditText.getText().toString().trim();
                uPass = uPassEditText.getText().toString().trim();
                notes = notesEditText.getText().toString().trim();

                System.out.println(empId +
                        " \n" + empName +
                        "\n " + empLocation +
                        "\n " + empJob +
                        "\n " + empMobile +
                        "\n " + uName +
                        "\n " + uPass +
                        "\n " + notes);


                try {
                    // Generate a key and encrypt a string
                    String originalString = uPass;
                    encryptedPassword = EncryptionUtil.encrypt(originalString);

                    // Store the encrypted string in the database
                    System.out.println("original password = " + uPass);
                    System.out.println("encrypted password = " + encryptedPassword);

                    // Optionally, store the key securely (not in the database)

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    // Its Available...
                    CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                } else {
                    // Not Available...
                    CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

                }

                try {

                    if (empId.isEmpty() ||
                            empName.isEmpty() ||
                            empLocation.isEmpty() ||
                            empJob.isEmpty() ||
                            empMobile.isEmpty() ||
                            uName.isEmpty() ||
                            uPass.isEmpty()
                    ) {
                        CustomToast.customToast(getApplicationContext(), "من فضلك ادخل البيانات الصحيحة ❌");
                    } else {
                        System.out.println("before request user");
                        //request user method
                        RequestUser.requestUser(getApplicationContext(),
                                empId,
                                empName,
                                empLocation,
                                empJob,
                                empMobile,
                                uName,
//                        uPass,
                                encryptedPassword,
                                notes);

                        System.out.println("after request user");

                        //call decrypt method(TEST DECRYPT PASSWORD RETRIEVE FROM DATABASE)
//                        RetrieveEncryptedString.retrieveEncryptedString("11111");
                        //redirect to login page

                        CustomToast.customToast(getApplicationContext(), "تم إرسال الطلب بنجاح ✅");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);

                        //redirect to login page
//                    Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
//                    startActivity(intent);

                    }
                } catch (Exception e) {
                    e.getStackTrace();
                    CustomToast.customToast(getApplicationContext(), "من فضلك ادخل البيانات الصحيحة ❌");
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

    public void getUserInfo(View view) {
        empNameEditText.setText("");
        empMobileEditText.setText("");
        empJobEditText.setText("");
        empLocationEditText.setText("");

        if (InternetConnection.checkConnection(getApplicationContext())) {
            // Its Available...
            CustomToast.customToast(getApplicationContext(), "متصل بالانترنت ✅");
        } else {
            // Not Available...
            CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت ❌");

        }
        if (empIdEditText.getText().toString().isEmpty()) {
            CustomToast.customToast(getApplicationContext(), "من فضلك ادخل رقم الموظف ❌");
        } else {
            //get user info by emp id

            CustomToast.customToast(getApplicationContext(),"فضلا أنتظر جارى جلب بيانات الموظف ✅");
            //get user info by emp id
            GetAllUserRequestInfoByEmpID.getAllUserRequestInfoByEmpID(
                    getApplicationContext(),
                    empIdEditText.getText().toString().trim());

            empNameEditText.setText(StoresConstants.EMP_NAME);
            empMobileEditText.setText(StoresConstants.EMP_MOBILE);
            empJobEditText.setText(StoresConstants.EMP_JOB);
            empLocationEditText.setText(StoresConstants.EMP_LOCATION);
        }
    }
}



