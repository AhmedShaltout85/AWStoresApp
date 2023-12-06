package com.ao8r.awstoresapp.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.utils.StoresConstants;

public class MenuScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);


        // Find the toolbar view inside the activity layout
        Toolbar toolbarMenu = (Toolbar) findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarMenu);

        toolbarMenu.setSubtitle(StoresConstants.LOGIN_USER);
        toolbarMenu.setSubtitleTextColor(Color.WHITE);
        toolbarMenu.setPadding(1,2,1,2);
//        set title
        setTitle(StoresConstants.MENU_TITLE);
//        setTitle("القائمة الرئيسية");
        toolbarMenu.setTitleTextColor(Color.WHITE);
        //        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
        //        back Icon change its color
        toolbarMenu.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);


    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_bar, menu);
        return true;
    }

// nav to search and fav Views and Menus Items

    //    views
    public void navToFavorites(View view) {

        intentFavs();
    }

    public void navToSearchScreen(View view) {
        intentSearchs();
    }

    //   Menus

//    public void navToSearch(MenuItem item) {
//        intentSearchs();
//    }

//    public void navToFav(MenuItem item) {
//
//        intentFavs();
//    }

    public void navToAppGuideBook(MenuItem item) {

        intentAppGuideBook();
    }

//    content of Intents

    private void intentAppGuideBook() {
//        Intent intent = new Intent(this, AppGuideBookScreen.class);
//        startActivity(intent);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=5X7WWVTrBvM"));
        try {
            MenuScreen.this.startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void intentSearchs() {
        Intent intent = new Intent(this, SearchScreen.class);
        startActivity(intent);
    }

    private void intentFavs() {
        Intent intent = new Intent(this, FavoritesScreen.class);
        startActivity(intent);
    }

    public void navToTopItemsChanges(View view) {
        Intent intent = new Intent(this, TopTenItemsMoreMovesScreen.class);
        startActivity(intent);
    }


    public void navToAdvanceSearch(View view) {
        Intent intent = new Intent(this, AdvancedSearchScreen.class);
        startActivity(intent);

    }

    public void navToGetReportForCertainStore(View view) {
        Intent intent = new Intent(this, GetStoreReport.class);
        startActivity(intent);
    }

    public void navToGetStoreReportInPeriodic(View view) {

        Intent intent = new Intent(this, GetStoreReportInPeriodic.class);
        startActivity(intent);
    }


}