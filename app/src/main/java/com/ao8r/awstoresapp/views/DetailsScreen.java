package com.ao8r.awstoresapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.adapter.AzonateAdapter;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.models.AzonateModel;
import com.ao8r.awstoresapp.utils.StoresConstants;
import com.ao8r.awstoresapp.repository.GetSingleStoreTotalQuantityRepo;
import com.ao8r.awstoresapp.services.InternetConnection;

import java.util.ArrayList;

public class DetailsScreen extends AppCompatActivity {
    //    declare vars
    private RecyclerView detailRecyclerView;
    AzonateAdapter azonateAdapter;
    ArrayList<AzonateModel> detailsModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        // Find the toolbar view inside the activity layout
        Toolbar toolbarDetails = (Toolbar) findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarDetails);

//      transparent for appBar

        toolbarDetails.setBackgroundColor(Color.TRANSPARENT);
//        set title
        setTitle("");
//        toolbarDetails.setTitleTextColor(Color.WHITE);
        //        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //        back Icon change its color
        toolbarDetails.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);
//        receive intent values

        String tempItemNumbet = getIntent().getExtras().getString(StoresConstants.EXTRA_ITEM_NUMBER);

        //      initialise Recycler View

        detailRecyclerView = findViewById(R.id.recyclerViewDetailsId);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false)); //define layout manger
        azonateAdapter = new AzonateAdapter(detailsModelArrayList); // assign constructor
        detailRecyclerView.setAdapter(azonateAdapter); //assign adapter

//        Check internet Connectivity

        if (InternetConnection.checkConnection(getApplicationContext())) {
            // Its Available...
            CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
        } else {
            // Not Available...
            CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

        }


//        get single Store Total quantity
        try {

            GetSingleStoreTotalQuantityRepo.getSingleStoreTotalQuantity(getApplicationContext(), Integer.parseInt(tempItemNumbet), detailsModelArrayList);
        } catch (Exception e) {

            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
        }


//                    to Observe changes in RecyclerView list length

        azonateAdapter.notifyItemInserted(detailsModelArrayList.size() - 1);
    }
}