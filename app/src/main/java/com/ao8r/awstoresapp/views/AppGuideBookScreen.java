package com.ao8r.awstoresapp.views;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.utils.StoresConstants;

public class AppGuideBookScreen extends AppCompatActivity {
//    String pdfurl = "";
//    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_guide_book_screen);

        // Find the toolbar view inside the activity layout
        Toolbar toolbarAbout = findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the
        // activity and is not null
        setSupportActionBar(toolbarAbout);
        toolbarAbout.setSubtitle(StoresConstants.LOGIN_USER);
        toolbarAbout.setSubtitleTextColor(Color.WHITE);
        toolbarAbout.setPadding(1,2,1,2);

//       set title
        setTitle(StoresConstants.ABOUT_APP);

        toolbarAbout.setTitleTextColor(Color.WHITE);
//        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        back Icon change its color
        toolbarAbout.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);

        // pdf viewer

//        pdfView = findViewById(R.id.pdfViewer);
////        pdfView.fromUri(Uri.parse(pdfurl)).load();
//        pdfView.fromAsset("wize.pdf")
//                .load();

        // pdf from uri
//        new RetrievePDFfromUrl().execute(pdfurl);
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading Data...");
//        progressDialog.setCancelable(false);
//        WebView web_view = findViewById(R.id.webView);
//        web_view.requestFocus();
//        web_view.getSettings().setJavaScriptEnabled(true);
//        String myPdfUrl = "https://www.your-domain.com/path/to/your/fun.pdf";
//        String url = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + myPdfUrl;
//
//        web_view.loadUrl(url);
//        web_view.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        web_view.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                if (progress < 100) {
//                    progressDialog.show();
//                }
//                if (progress == 100) {
//                    progressDialog.dismiss();
//                }
//            }
//        });
    }
}


