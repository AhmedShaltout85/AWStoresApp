package com.ao8r.awstoresapp.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.adapter.AzonateAdapter;
import com.ao8r.awstoresapp.customiz_widgets.CustomSnackBar;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.models.AzonateModel;
import com.ao8r.awstoresapp.models.FavoritesModel;
import com.ao8r.awstoresapp.utils.StoresConstants;
import com.ao8r.awstoresapp.repository.AddItemsToFavoritesRepo;
import com.ao8r.awstoresapp.repository.CheckItemExistsInFavoritesRepo;
import com.ao8r.awstoresapp.repository.GetSingleStoreTotalQuantityRepo;
import com.ao8r.awstoresapp.repository.GetTotalQuantityRepo;
import com.ao8r.awstoresapp.services.InternetConnection;

import java.util.ArrayList;

public class SearchScreen extends AppCompatActivity {
    double totalQty;

    //    declare vars
    public RecyclerView azonateRecyclerView;
    public AzonateAdapter azonateAdapter;
    ArrayList<AzonateModel> azonateModelArrayList = new ArrayList<>();
    public EditText itemNumberEditText;
    TextView storesTotalQuantity;
    ImageView addToFavImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        // Find the toolbar view inside the activity layout
        Toolbar toolbarSearch = findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarSearch);
        toolbarSearch.setSubtitle(StoresConstants.LOGIN_USER);
        toolbarSearch.setSubtitleTextColor(Color.WHITE);
        toolbarSearch.setPadding(1,2,1,2);
//       set title
        setTitle(StoresConstants.SEARCH_TITLE);

        toolbarSearch.setTitleTextColor(Color.WHITE);
//        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        back Icon change its color
        toolbarSearch.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);
//


//      initialise Recycler View

        azonateRecyclerView = findViewById(R.id.searchRecyclerViewId);
        azonateRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),
                RecyclerView.VERTICAL, false)); //define layout manger
        azonateAdapter = new AzonateAdapter(azonateModelArrayList); // assign constructor
        azonateRecyclerView.setAdapter(azonateAdapter); //assign adapter
//        EditText
        itemNumberEditText = findViewById(R.id.itemNumberQuerySearchEditText); //assign editText to item Nu.
//        TextView
        storesTotalQuantity = findViewById(R.id.textViewTotalQtySum); //assign textView to total QTY
        storesTotalQuantity.setText("الرصيد الكلى فى المخازن: " + "  " + totalQty); //assign value to text
//        ImageView
        addToFavImg = findViewById(R.id.idAddToFavImgSearchPage);

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

    public void getSearchResult(View view) {
        try {
            azonateModelArrayList.clear();
            azonateAdapter.notifyDataSetChanged();

            if (itemNumberEditText.getText().toString().isEmpty()) {
                CustomSnackBar.customSnackBar(view, "فضلا أكمل البيانات", getApplicationContext());
            } else {
//        getValue in EditText
                StoresConstants.ITEM_NUMBER = Integer.parseInt(itemNumberEditText.getText().toString());

//                Check internet Connectivity

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    // Its Available...
                    CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                } else {
                    // Not Available...
                    CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

                }
//        get all stores total Values for certain item
                try {

                    totalQty = GetTotalQuantityRepo.getSumTotalQuantity(getApplicationContext());
                    storesTotalQuantity.setText("الرصيد الكلى فى المخازن: " + "  " + totalQty); //assign value to text
                } catch (Exception e) {
                    CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
                }


//        get single Store Total quantity

                try {

                    GetSingleStoreTotalQuantityRepo.getSingleStoreTotalQuantity(
                            getApplicationContext(),
                            StoresConstants.ITEM_NUMBER,
                            azonateModelArrayList);
                } catch (Exception e) {
                    CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
                }


//                    to Observe changes in RecyclerView list length

                azonateAdapter.notifyItemInserted(azonateModelArrayList.size() - 1);

//            check if item exists in Favorites List or NOT

                try {

                    CheckItemExistsInFavoritesRepo.getItemNoFromFavoritesRepo(getApplicationContext());
                } catch (Exception e) {
                    CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
                }

                if (StoresConstants.FAV_ITEM_NUMBER == StoresConstants.ITEM_NUMBER) {
                    addToFavImg.setImageResource(R.drawable.unfavorite);
                } else {
                    addToFavImg.setImageResource(R.drawable.add_to_favorites);

                }
            }
//        hide keyboard after typed
            try {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } catch (Exception e) {
            e.getStackTrace();
            CustomToast.customToast(getApplicationContext(), "حدث خطا فى الاتصال بالخادم");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addToFav(View view) {

        if (StoresConstants.FAV_ITEM_NUMBER == StoresConstants.ITEM_NUMBER) {
            addToFavImg.setImageResource(R.drawable.unfavorite);
            CustomToast.customToast(getApplicationContext(), "هذا الصنف موجود مسبقا");

        } else {

//        call AddItemsToFavs
            if (itemNumberEditText.getText().toString().isEmpty()) {
                CustomToast.customToast(getApplicationContext(), "فضلا أدخل رقم الصنف");
            } else {
                try {
                    addToFavImg.setImageResource(R.drawable.add_to_favorites);
                    AddItemsToFavoritesRepo.insertItemsToFavorites(getApplicationContext(), view);
                    addToFavImg.setImageResource(R.drawable.unfavorite);
                } catch (Exception e) {
                    CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
                }

            }
        }
    }


}