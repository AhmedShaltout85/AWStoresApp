package com.ao8r.awstoresapp.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.adapter.AzonateHorizontalAdapter;
import com.ao8r.awstoresapp.customiz_widgets.CustomSnackBar;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.models.AzonateModel;
import com.ao8r.awstoresapp.repository.GetAllMatchItemsByNameRepo;
import com.ao8r.awstoresapp.services.InternetConnection;
import com.ao8r.awstoresapp.utils.StoresConstants;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdvancedSearchScreen extends AppCompatActivity {
    

    ProgressBar progressBarAdvancedSearch;
    //
    ExecutorService serviceAdvancedSearch = Executors.newSingleThreadExecutor();

    RecyclerView recyclerViewHorizontal;
    AzonateHorizontalAdapter azonateHorizontalAdapter;
    public EditText itemNameEditTextAdvanced;
    ArrayList<AzonateModel> azonateModelArrayListAdvancedH = new ArrayList<>();
    //    TextView storesTotalQuantityAdvanced;
//    ImageView addToFavImgAdvanced;
    Button getQueryByNameResult;


    //    @SuppressLint("WrongViewCast")
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search_screen);

        // Find the toolbar view inside the activity layout
        Toolbar toolbarAdvancedSearch = findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarAdvancedSearch);
        toolbarAdvancedSearch.setSubtitle(StoresConstants.LOGIN_USER);
        toolbarAdvancedSearch.setSubtitleTextColor(Color.WHITE);
        toolbarAdvancedSearch.setPadding(1, 2, 1, 2);
//       set title
        setTitle(StoresConstants.ADVANCED_SEARCH_TITLE);

        toolbarAdvancedSearch.setTitleTextColor(Color.WHITE);
//        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        back Icon change its color
        toolbarAdvancedSearch.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);

//
        getQueryByNameResult = findViewById(R.id.getSearchResultAdvanced);
        itemNameEditTextAdvanced.requestFocus();

        //      initialise Recycler View Horizontal
        recyclerViewHorizontal = findViewById(R.id.advanceSearchHorizRecView);
//        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getBaseContext(),
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getBaseContext(),
                RecyclerView.VERTICAL, false)); //define layout manger
        azonateHorizontalAdapter = new AzonateHorizontalAdapter(getApplicationContext(),
                azonateModelArrayListAdvancedH); // assign constructor
        recyclerViewHorizontal.setAdapter(azonateHorizontalAdapter); //assign adapter


//        EditText
        itemNameEditTextAdvanced = findViewById(R.id.itemNameQuerySearchEditTextH); //assign editText to item Nu.

        progressBarAdvancedSearch = findViewById(R.id.advancedSearchProgressBarId);

        progressBarAdvancedSearch.setVisibility(View.GONE);


//        ImageView
//        addToFavImgAdvanced = findViewById(R.id.idAddToFavImgSearchPage);
//        Add Action in Button in-query
        getQueryByNameResult.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (itemNameEditTextAdvanced.getText().toString().isEmpty()) {
                    CustomSnackBar.customSnackBar(view, "فضلا أكمل البيانات", getApplicationContext());
                } else {
//        getValue in EditText
                    azonateModelArrayListAdvancedH.clear();
                    azonateHorizontalAdapter.notifyDataSetChanged();

                    StoresConstants.ITEM_NAME = itemNameEditTextAdvanced.getText().toString().trim();

//                Check internet Connectivity

                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        // Its Available...
                        CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                    } else {
                        // Not Available...
                        CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

                    }
////            GET VALUE BY NAME
//                    GetAllMatchItemsByNameRepo.getAllMatchItemsByNameRepo(
//                            getApplicationContext(),
//                            StoresConstants.ITEM_NAME,
//                            azonateModelArrayListAdvancedH);
                    //                    to Observe changes in RecyclerView list length

                    //////


                    CustomToast.customToast(getApplicationContext(), "فضلا أنتظر جارى جلب البيانات");
                    //        get all items in Certain store
//                    storeReportPeriodicModelArrayList.clear();
//                    storeReportInCertainPeriodDateAdapter.notifyDataSetChanged();

                    serviceAdvancedSearch.execute(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBarAdvancedSearch.setVisibility(View.VISIBLE);

                                }
                            });

                            //
                            try {

                                GetAllMatchItemsByNameRepo.getAllMatchItemsByNameRepo(
                                        getApplicationContext(),
                                        StoresConstants.ITEM_NAME,
                                        azonateModelArrayListAdvancedH);
                            } catch (Exception e) {
                                e.getStackTrace();
                                CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
                            }

//                            lInCertainStoreInPeriodicDate(getApplicationContext(), storeReportPeriodicModelArrayList);

//                        storeReportInCertainPeriodDateAdapter.notifyItemInserted(storeReportPeriodicModelArrayList.size() - 1);
                            //
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBarAdvancedSearch.setVisibility(View.GONE);
                                    if (azonateModelArrayListAdvancedH.size() >= 1) {
//                                    GetAllInCertainStoreInPeriodicDate.getAllInCertainStoreInPeriodicDate(getApplicationContext(), storeReportPeriodicModelArrayList);
                                        azonateHorizontalAdapter.notifyItemInserted(azonateModelArrayListAdvancedH.size() - 1);

                                    } else {
                                        CustomToast.customToast(getApplicationContext(), "لايوجد بيانات لهذا الاسم");

                                    }
                                }
                            });
                        }
                    });
                    /////


                    azonateHorizontalAdapter.notifyDataSetChanged();

                    //        hide keyboard after typed
                    try {

                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                }
            }
        });


    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu_bar, menu);
        return true;
    }

    //   Menus
    public void navToFav(MenuItem item) {
        intentFavs();
    }

    public void navToMenu(MenuItem item) {
        intentMenus();
    }

//    content of Intents

    private void intentFavs() {
//        GetAllFavsRepo.getAllFavsRepo(getApplicationContext(), favoritesModelArrayList);

        Intent intent = new Intent(this, FavoritesScreen.class);
        startActivity(intent);
    }

    private void intentMenus() {
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

}