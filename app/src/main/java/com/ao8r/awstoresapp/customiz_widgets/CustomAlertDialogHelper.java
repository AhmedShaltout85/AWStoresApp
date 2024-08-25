package com.ao8r.awstoresapp.customiz_widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CustomAlertDialogHelper {

    public static void showAlert(Context context,
                                 String title,
                                 String message,
                                 String positiveButtonText,
                                 DialogInterface.OnClickListener positiveButtonListener,
                                 String negativeButtonText,
                                 DialogInterface.OnClickListener negativeButtonListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(positiveButtonText, positiveButtonListener);

        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText, negativeButtonListener);
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}



//call Alter


// Example usage of the AlertDialogHelper
//        CustomAlertDialogHelper.showAlert(this,
//                                                  "Alert Title",
//                                                  "This is an alert message.",
//                                                  "OK",
//                                                  new DialogInterface.OnClickListener() {
//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//        // Positive button action
//        dialog.dismiss();
//    }
//},
//        "Cancel",
//        new DialogInterface.OnClickListener() {
//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//        // Negative button action
//        dialog.dismiss();
//    }
//});

