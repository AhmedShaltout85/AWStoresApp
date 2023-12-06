package com.ao8r.awstoresapp.customiz_widgets;

import android.app.ProgressDialog;
import android.content.Context;

import com.ao8r.awstoresapp.R;

public class CustomLoader {

    public static void customLoader(Context context, String msg){

        final ProgressDialog progress = new ProgressDialog(context,R.style.MyAlertDialogStyle);    //ProgressDialog
        progress.setTitle("");
        progress.setMessage(msg);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        Objects.requireNonNull(progress.getWindow()).setGravity(0);
//        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        progress.setContentView(R.layout.custom_progress_bar);
        progress.setCancelable(false);
        progress.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progress.dismiss();
            }
        }).start();
    }
}
