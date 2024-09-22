package com.ao8r.awstoresapp.views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.adapter.FavoritesAdapter;
import com.ao8r.awstoresapp.models.FavoritesModel;
import com.ao8r.awstoresapp.repository.GetAllFavsRepo;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class FavoritesScreen extends AppCompatActivity {


    //    declare vars
    private RecyclerView favRecyclerView;
    FavoritesAdapter favoritesAdapter;
    ArrayList<FavoritesModel> favoritesModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_screen);
        // Find the toolbar view inside the activity layout
        Toolbar toolbarFav = (Toolbar) findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarFav);
        toolbarFav.setSubtitle(StoresConstants.LOGIN_USER);
        toolbarFav.setSubtitleTextColor(Color.WHITE);
        toolbarFav.setPadding(1,2,1,2);
//        set title
        setTitle(StoresConstants.FAVORITE_TITLE);
//        setTitle("قائمة المفضلة");
        toolbarFav.setTitleTextColor(Color.WHITE);
        //        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        back Icon change its color
        toolbarFav.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);


        //      initialise Recycler View
        favRecyclerView = findViewById(R.id.favRecyclerView);
        favRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));//define layout manger
        favoritesAdapter = new FavoritesAdapter(getApplicationContext(), favoritesModelList);// assign constructor
        favRecyclerView.setAdapter(favoritesAdapter);//assign adapter
        favoritesAdapter.notifyItemInserted(favoritesModelList.size() - 1);
        try {

            GetAllFavsRepo.getAllFavsRepo(getApplicationContext(), favoritesModelList);
        } catch (Exception e) {
//            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
            Toasty.error(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى", Toast.LENGTH_SHORT, true).show();
        }


    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fav_menu_bar, menu);
        return true;
    }

    //   Menus
    public void navToSearch(MenuItem item) {
        intentSearchs();
    }

    public void navToMenu(MenuItem item) {
        intentMenus();
    }

//    content of Intents

    private void intentSearchs() {
        Intent intent = new Intent(getApplicationContext(), SearchScreen.class);
        startActivity(intent);
    }

    private void intentMenus() {
        Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
        startActivity(intent);
    }

}