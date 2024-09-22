package com.ao8r.awstoresapp.views;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anychart.AnyChartView;
import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.customiz_widgets.CustomLoader;
import com.ao8r.awstoresapp.repository.GetTopTenMoreMovesItemsRepo;
import com.ao8r.awstoresapp.utils.StoresConstants;

import es.dmoral.toasty.Toasty;

public class TopTenItemsMoreMovesScreen extends AppCompatActivity {

    AnyChartView anyChartView;
    //    String[] months = {"jan", "fab", "march", "april", "may", "jun", "july", "aug", "seb", "oct"};
//    int[] salary = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
//    View view;
//    ProgressBar progressBarMoreMoves;
//    //
//    ExecutorService serviceMoreMoves = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_items_more_moves);


        // Find the toolbar view inside the activity layout
        Toolbar toolbarTopItemsChanges = findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarTopItemsChanges);
        toolbarTopItemsChanges.setSubtitle(StoresConstants.LOGIN_USER);
        toolbarTopItemsChanges.setSubtitleTextColor(Color.WHITE);
        toolbarTopItemsChanges.setPadding(1,2,1,2);
//        set title
        setTitle(StoresConstants.TOP_TEN_MORE_MOVES);

//        setTitle("الأصناف الأكثر حركة");
        toolbarTopItemsChanges.setTitleTextColor(Color.WHITE);
        //        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        back Icon change its color
        toolbarTopItemsChanges.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);

//        assign anychart with id
        anyChartView = findViewById(R.id.anyChartId);
//        progressBarMoreMoves = findViewById(R.id.progressBarMoreMoves);

//        setUpAnyChart();
        CustomLoader.customLoader(this, "جارى التحميل ...");

        try {
            GetTopTenMoreMovesItemsRepo.getTopTenMoreMovesItemsRepo(getApplicationContext(), anyChartView);
        }catch (Exception e){
//            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
            Toasty.error(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى", Toast.LENGTH_SHORT, true).show();
        }

//        //
//        //export view as pdf
//        //
//        View view = findViewById(R.id.anyChartId);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ExportAsPdfFile.generatePdfFromView(getApplicationContext(),view);
//
//            }
//        }, 7000);
//
//        /////
    }

//    private void setUpAnyChart() {
//        Pie pie = AnyChart.pie();
//        List<DataEntry> dataEntries = new ArrayList<>();
//        for (int i= 0; i<months.length;i++){
//            dataEntries.add(new ValueDataEntry(months[i],salary[i]));
//        }
//        pie.data(dataEntries);
//        pie.title("الأكثر حركة فى المخازن");
//        anyChartView.setChart(pie);
//    }
}