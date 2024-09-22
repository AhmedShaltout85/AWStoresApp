package com.ao8r.awstoresapp.views;

import static com.ao8r.awstoresapp.utils.StoresConstants.STORE_NAME_REPORT;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.adapter.StoreReportAdapter;
import com.ao8r.awstoresapp.customiz_widgets.CustomDatePicker;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.customiz_widgets.ExportAsExcelSheet;
import com.ao8r.awstoresapp.customiz_widgets.ExportAsPdfFile;
import com.ao8r.awstoresapp.models.StoreReportModel;
import com.ao8r.awstoresapp.models.StoresNamesModel;
import com.ao8r.awstoresapp.repository.GetAllItemsInCertainStore;
import com.ao8r.awstoresapp.repository.GetAllStoresNamesBySectorNameDropdown;
import com.ao8r.awstoresapp.repository.GetAllStoresNamesByStoreNumDropdown;
import com.ao8r.awstoresapp.repository.GetAllStoresNamesDropdown;
import com.ao8r.awstoresapp.services.InternetConnection;
import com.ao8r.awstoresapp.utils.StoresConstants;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.dmoral.toasty.Toasty;

public class GetStoreReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    ImageButton exportExcelImageBtn;
    ArrayList<StoresNamesModel> spinnerStoreNameArrayList;
    ArrayList<StoreReportModel> storeReportModelArrayList = new ArrayList<>();
    Spinner storeNameSpinner;
    Button getResultItemsReportBtn;
    //    declare vars
    public RecyclerView storeReportRecyclerView;
    public StoreReportAdapter storeReportAdapter;
    private SearchView searchView;

    //
    ProgressBar progressBarStoreReport;
    //
    ExecutorService serviceStoreReport = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_store_report);

        try {

            //call current date method
            CustomDatePicker.currentDate();
        } catch (Exception e) {
            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
        }


        //                Check internet Connectivity

        if (InternetConnection.checkConnection(getApplicationContext())) {
            // Its Available...
            CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
        } else {
            // Not Available...
            CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

        }

        // Find the toolbar view inside the activity layout
        Toolbar toolbarStoreReport = (Toolbar) findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarStoreReport);
        toolbarStoreReport.setSubtitle(StoresConstants.LOGIN_USER);
        toolbarStoreReport.setSubtitleTextColor(Color.WHITE);
        toolbarStoreReport.setPadding(1, 2, 1, 2);
//        set title
        setTitle(StoresConstants.STORE_ANALYSIS_TITLE);

//        setTitle("القائمة الرئيسية");
        toolbarStoreReport.setTitleTextColor(Color.WHITE);
        //        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //        back Icon change its color
        toolbarStoreReport.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);

        // initialize PROGRESS BAR
        progressBarStoreReport = findViewById(R.id.progressBarStoreReportId);

        //initialize Button
        getResultItemsReportBtn = findViewById(R.id.getResultReportItemsImgBtnID);

        progressBarStoreReport.setVisibility(View.GONE);

        getResultItemsReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CustomToast.customToast(getApplicationContext(), "فضلا أنتظر جارى جلب البيانات");
                Toasty.info(getApplicationContext(), "فضلا أنتظر جارى جلب البيانات", Toast.LENGTH_SHORT, true).show();
                //        get all items in Certain store
                storeReportModelArrayList.clear();
                storeReportAdapter.notifyDataSetChanged();
////                    CustomLoader.customLoader(v.getContext(), "جـــارى تحميل البيانات");
//                    GetAllItemsInCertainStore.getAllItemsInCertainStore(getApplicationContext(), storeReportModelArrayList);
//                    storeReportAdapter.notifyItemInserted(storeReportModelArrayList.size() - 1);
                serviceStoreReport.execute(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBarStoreReport.setVisibility(View.VISIBLE);

                            }
                        });
                        //
                        try {

                            GetAllItemsInCertainStore.getAllItemsInCertainStore(getApplicationContext(), storeReportModelArrayList);
                        } catch (Exception e) {
//                            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
                            Toasty.error(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى", Toasty.LENGTH_SHORT, true).show();
                        }

//                        storeReportInCertainPeriodDateAdapter.notifyItemInserted(storeReportPeriodicModelArrayList.size() - 1);
                        //
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBarStoreReport.setVisibility(View.GONE);
                                if (storeReportModelArrayList.size() >= 1) {
//                                    GetAllInCertainStoreInPeriodicDate.getAllInCertainStoreInPeriodicDate(getApplicationContext(), storeReportPeriodicModelArrayList);
                                    storeReportAdapter.notifyItemInserted(storeReportModelArrayList.size() - 1);
//                                    CustomToast.customToast(getApplicationContext(), storeReportModelArrayList.size() + " عنصر");
                                    Toasty.info(getApplicationContext(), storeReportModelArrayList.size() + " عنصر", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        });
                    }
                });

            }
        });

        //initialize ImageBUTTON
        exportExcelImageBtn = findViewById(R.id.exportStoreDataExcel);
        exportExcelImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storeReportModelArrayList.isEmpty()) {
//                    CustomToast.customToast(getApplicationContext(), "فضلا اختر المخزن اولا");
                    Toasty.warning(getApplicationContext(), "فضلا اختر المخزن اولا", Toast.LENGTH_SHORT, true).show();
                } else {
//                    CustomToast.customToast(getApplicationContext(), "أنتظر جارى حفظ الملف فى Documents");
                    Toasty.info(getApplicationContext(), "أنتظر جارى حفظ الملف فى Documents", Toast.LENGTH_SHORT, true).show();
                    try {
//                    ExportAsPdfFile.generatePdfFromRecyclerView(getApplicationContext(),
//                            storeReportRecyclerView,
//                            "تقرير جرد " + STORE_NAME_REPORT + "-" + StoresConstants.CURRENT_DATE
//                            );

                        ExportAsExcelSheet.exportStoreReportAsExcelSheet(
                                "كود الصنف",
                                "إسم الصنف",
                                "الرصيد",
                                "تاريخ أخر إرسال",
                                "تقرير جرد " + STORE_NAME_REPORT + "-" + StoresConstants.CURRENT_DATE,
                                storeReportModelArrayList);
                        //
//                        ExportAsPdfFile.generatePDFFromRV(storeReportRecyclerView,
//                                "تقرير جرد " + STORE_NAME_REPORT + "-" + StoresConstants.CURRENT_DATE
//                        );
                        ExportAsPdfFile.saveAsPDF(
                                storeReportModelArrayList,
                                "تقرير جرد " + STORE_NAME_REPORT + "-" + StoresConstants.CURRENT_DATE,
                                "كود الصنف",
                                "إسم الصنف",
                                "الرصيد",
                                "تاريخ أخر إرسال",
                                getApplicationContext()

                        );
                    } catch (IOException | DocumentException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
///////////////////////////
        //TODO: (SEARCH VIEW)EDITED IN 28-03-2023

//        searchView = findViewById(R.id.searchViewReport);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                storeReportAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//////////////////////////////////////////

//      initialise Recycler View

        storeReportRecyclerView = findViewById(R.id.storeReportRecycleViewID);
        storeReportRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),
                RecyclerView.VERTICAL, false)); //define layout manger
        storeReportAdapter = new StoreReportAdapter(storeReportModelArrayList); // assign constructor
        storeReportRecyclerView.setAdapter(storeReportAdapter); //assign adapter

        //        Store Name spinner
        storeNameSpinner = findViewById(R.id.storeNameSpinnerID);
        //      Assign Spinner

        try {

            if (StoresConstants.USER_CONTROL == 1 &&
                    StoresConstants.STORE_NUMBER == 0 &&
                    Objects.equals(StoresConstants.STORE_SECTOR, "0")) {

                //get all store names
                spinnerStoreNameArrayList = GetAllStoresNamesDropdown.getAllStoresNamesDropdown(getApplicationContext());
            } else if (!Objects.equals(StoresConstants.STORE_SECTOR, "0")) {

                //get all store names by sector name
                spinnerStoreNameArrayList = GetAllStoresNamesBySectorNameDropdown.getAllStoresNamesBySectorNameDropdown(getApplicationContext());
            } else if (StoresConstants.STORE_NUMBER != 0) {

                //get all store names by store num
                spinnerStoreNameArrayList = GetAllStoresNamesByStoreNumDropdown.getAllStoresNamesByStoreNumDropdown(getApplicationContext());
            }else{
//                CustomToast.customToast(getApplicationContext(), "عفو ليس لديك صلاحية لعرض البيانات");
                Toasty.error(getApplicationContext(), "عفو ليس لديك صلاحية لعرض البيانات", Toast.LENGTH_SHORT, true).show();
            }

        } catch (Exception e) {
//            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
            Toasty.error(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى", Toast.LENGTH_SHORT, true).show();
        }
        ArrayAdapter<StoresNamesModel> spinnerStoreNamesAdapter =
                new ArrayAdapter<StoresNamesModel>(
                        GetStoreReport.this,
                        android.R.layout.simple_spinner_item,
                        spinnerStoreNameArrayList);
        spinnerStoreNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeNameSpinner.setAdapter(spinnerStoreNamesAdapter);
        storeNameSpinner.setOnItemSelectedListener(this);
//        storeNameSpinner.setPrompt("أختر المخزن");


    }


    // Get Spinner Data
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sn = String.valueOf(parent.getItemAtPosition(position));
        STORE_NAME_REPORT = sn;
        System.out.println(String.valueOf(STORE_NAME_REPORT));
        storeReportModelArrayList.clear();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        CustomToast.customToast(getApplicationContext(), "فضلا أختر أسم المخزن");
        Toasty.warning(getApplicationContext(), "فضلا أختر أسم المخزن", Toast.LENGTH_SHORT, true).show();
        getResultItemsReportBtn.setVisibility(View.INVISIBLE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.store_report_menu_bar, menu);

        SearchManager searchManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        }
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                storeReportAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                storeReportAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }


}