package com.ao8r.awstoresapp.customiz_widgets;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.ao8r.awstoresapp.R;
import com.google.android.material.snackbar.Snackbar;

public class CustomSnackBar {
    public static void customSnackBar(View parent_view, String snackMessage, Context context) {

        Snackbar snackbar = Snackbar.make(parent_view, snackMessage, Snackbar.LENGTH_LONG);

//        snackbar.setAction("UNDO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Snackbar sb = Snackbar.make(parent_view, "UNDO Clicked", Snackbar.LENGTH_SHORT);
//                        sb.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
//                        sb.setActionTextColor(Color.WHITE);
//                        sb.show();
//                    }
//                });
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.greyColor));
        snackbar.setActionTextColor(Color.WHITE);

//
        snackbar.getView().setPadding(10, 10, 10, 10);
//
        snackbar.show();

    }
}
