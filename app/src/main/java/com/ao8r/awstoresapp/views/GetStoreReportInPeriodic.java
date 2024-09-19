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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.adapter.StoreReportInCertainPeriodDateAdapter;
import com.ao8r.awstoresapp.customiz_widgets.CustomDatePicker;
import com.ao8r.awstoresapp.customiz_widgets.CustomToast;
import com.ao8r.awstoresapp.customiz_widgets.ExportAsExcelSheet;
import com.ao8r.awstoresapp.customiz_widgets.ExportAsPdfFile;
import com.ao8r.awstoresapp.models.StoreReportModel;
import com.ao8r.awstoresapp.models.StoresNamesModel;
import com.ao8r.awstoresapp.repository.GetAllInCertainStoreInPeriodicDate;
import com.ao8r.awstoresapp.repository.GetAllStoresNamesBySectorNameDropdown;
import com.ao8r.awstoresapp.repository.GetAllStoresNamesByStoreNumDropdown;
import com.ao8r.awstoresapp.repository.GetAllStoresNamesDropdown;
import com.ao8r.awstoresapp.services.InternetConnection;
import com.ao8r.awstoresapp.utils.StoresConstants;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetStoreReportInPeriodic extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SearchView searchView;

    ProgressBar progressBar;
    //
    ExecutorService service = Executors.newSingleThreadExecutor();


    //
    ArrayList<StoresNamesModel> spinnerStoreNamePeriodicArrayList;
    ArrayList<StoreReportModel> storeReportPeriodicModelArrayList = new ArrayList<>();
    Spinner storeNamePeriodicSpinner;
    Button getResultItemsReportPeriodicBtn, fromDate, toDate;
    ImageButton exportExcelPeriodicImageBtn;
    //    declare vars
    public RecyclerView storeReportPeriodicRecyclerView;
    public StoreReportInCertainPeriodDateAdapter storeReportInCertainPeriodDateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_store_report_in_periodic);

        //call current date method
        CustomDatePicker.currentDate();

        //                Check internet Connectivity

        if (InternetConnection.checkConnection(getApplicationContext())) {
            // Its Available...
            CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
        } else {
            // Not Available...
            CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

        }
        // Find the toolbar view inside the activity layout
        Toolbar toolbarStoreReportPeriodic = (Toolbar) findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarStoreReportPeriodic);
        toolbarStoreReportPeriodic.setSubtitle(StoresConstants.LOGIN_USER);
        toolbarStoreReportPeriodic.setSubtitleTextColor(Color.WHITE);
        toolbarStoreReportPeriodic.setPadding(1, 2, 1, 2);

//        set title
        setTitle(StoresConstants.STORE_ANALYSIS_PERIODIC_TITLE);
//        setTitle("القائمة الرئيسية");
        toolbarStoreReportPeriodic.setTitleTextColor(Color.WHITE);
        //        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //        back Icon change its color
        toolbarStoreReportPeriodic.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);
        // initialize PROGRESS BAR
        progressBar = findViewById(R.id.progressBarStoreReportPeriodicId);

        //initialize Buttons
        getResultItemsReportPeriodicBtn = findViewById(R.id.getResultStoreReportPeriodicIdBtn);

        progressBar.setVisibility(View.GONE);

        getResultItemsReportPeriodicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StoresConstants.START_DATE.isEmpty() || StoresConstants.END_DATE.isEmpty()) {
                    CustomToast.customToast(getApplicationContext(), "فضلا أختر التاريخ");
                } else {
                    CustomToast.customToast(getApplicationContext(), "فضلا أنتظر جارى جلب البيانات");
                    //        get all items in Certain store
                    storeReportPeriodicModelArrayList.clear();
                    storeReportInCertainPeriodDateAdapter.notifyDataSetChanged();


                    service.execute(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.VISIBLE);

                                }
                            });

                            //
                            try {

                                GetAllInCertainStoreInPeriodicDate.getAllInCertainStoreInPeriodicDate(getApplicationContext(), storeReportPeriodicModelArrayList);
                            } catch (Exception e) {
                                CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
                            }

//                        storeReportInCertainPeriodDateAdapter.notifyItemInserted(storeReportPeriodicModelArrayList.size() - 1);
                            //
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    if (storeReportPeriodicModelArrayList.size() >= 1) {
//                                    GetAllInCertainStoreInPeriodicDate.getAllInCertainStoreInPeriodicDate(getApplicationContext(), storeReportPeriodicModelArrayList);
                                        storeReportInCertainPeriodDateAdapter.notifyItemInserted(storeReportPeriodicModelArrayList.size() - 1);
                                        CustomToast.customToast(getApplicationContext(), storeReportPeriodicModelArrayList.size() + " حركه / حركات");


                                    } else {
                                        CustomToast.customToast(getApplicationContext(), "لايوجد بيانات فى هذه الفتره");

                                    }
                                }
                            });
                        }
                    });

                }
            }
        });


        //from Date
        fromDate = findViewById(R.id.getEndDateReportPeriodicIdBtn);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialDatePicker materialDatePickerFrom = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("تاريخ بداية البحث")
                        .build();
                materialDatePickerFrom.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        ///
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis((Long) selection);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        StoresConstants.START_DATE = format.format(calendar.getTime());
                        fromDate.setText(StoresConstants.START_DATE);
                        CustomToast.customToast(getApplicationContext(), StoresConstants.START_DATE);
                        System.out.println(StoresConstants.START_DATE);

                    }
                });
                materialDatePickerFrom.show(getSupportFragmentManager(), "ShowDATEFrom");

            }
        });

        //to Date
        toDate = findViewById(R.id.getStartDateReportPeriodicIdBtn);
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pattern = "yyyy-MM-dd";
                String dateInString = new SimpleDateFormat(pattern).format(new Date());
                StoresConstants.END_DATE = dateInString;
                toDate.setText(StoresConstants.END_DATE);
                CustomToast.customToast(getApplicationContext(), "لتخصيص تاريخ معين أضغط 3 ثوان");
                System.out.println(StoresConstants.END_DATE);


            }
        });
        toDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                 TODO Auto-generated method stub
                MaterialDatePicker materialDatePickerTo = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("تاريخ نهاية الفتره")
                        .build();
                materialDatePickerTo.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis((Long) selection);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        StoresConstants.END_DATE = format.format(calendar.getTime());
                        toDate.setText(StoresConstants.END_DATE);
                        CustomToast.customToast(getApplicationContext(), StoresConstants.END_DATE);
                        System.out.println(StoresConstants.END_DATE);

                    }
                });
                materialDatePickerTo.show(getSupportFragmentManager(), "ShowDATETO");
                return true;
            }
        });

        //initialize ImageBUTTON
        exportExcelPeriodicImageBtn = findViewById(R.id.exportStorePeriodicDataExcel);
        exportExcelPeriodicImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storeReportPeriodicModelArrayList.isEmpty()) {
                    CustomToast.customToast(getApplicationContext(), "فضلا اختر المخزن اولا");
                } else {
                    CustomToast.customToast(getApplicationContext(), "أنتظر جارى حفظ الملف فى Documents");
                    try {
                        ExportAsExcelSheet.exportStoreReportInPeriodicDateAsExcelSheet(
                                "كود الصنف",
                                "إسم الصنف",
                                "رصيد بداية المدة",
                                "إجمالى الوارد",
                                "إجمالى الصادر",
                                "رصيد نهاية المدة",
                                "حركة فى فترة لـ " + STORE_NAME_REPORT + "-" + StoresConstants.CURRENT_DATE,
                                storeReportPeriodicModelArrayList);
                        //
                        ExportAsPdfFile.saveAsPDFPeriodic(
                                storeReportPeriodicModelArrayList,
                                "كود الصنف",
                                "إسم الصنف",
                                "رصيد بداية المدة",
                                "إجمالى الوارد",
                                "إجمالى الصادر",
                                "رصيد نهاية المدة",
                                "حركة فى فترة لـ " + STORE_NAME_REPORT + "-" + StoresConstants.CURRENT_DATE,
                                getApplicationContext()
                        );

//                        ExportAsPdfFile.generatePDFFromRV(
//                                storeReportPeriodicRecyclerView,
//                                "حركة فى فترة لـ " + STORE_NAME_REPORT + "-" + StoresConstants.CURRENT_DATE
//                        );
                    } catch (IOException | DocumentException e) {
                        e.printStackTrace();
                    }

                }

            }
        });


        //      initialise Recycler View

        storeReportPeriodicRecyclerView = findViewById(R.id.storeReportPeriodicRecyclerViewId);
        storeReportPeriodicRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),
                RecyclerView.VERTICAL, false)); //define layout manger
        storeReportInCertainPeriodDateAdapter = new StoreReportInCertainPeriodDateAdapter(storeReportPeriodicModelArrayList); // assign constructor
        storeReportPeriodicRecyclerView.setAdapter(storeReportInCertainPeriodDateAdapter); //assign adapter

        //        Store Name spinner
        storeNamePeriodicSpinner = findViewById(R.id.storeNameSpinnerReportPeriodicID);
        //      Assign Spinner

        try {

            if (StoresConstants.USER_CONTROL == 1 &&
                    StoresConstants.STORE_NUMBER == 0 &&
                    Objects.equals(StoresConstants.STORE_SECTOR, "0")) {

                //get all store names
                spinnerStoreNamePeriodicArrayList = GetAllStoresNamesDropdown.getAllStoresNamesDropdown(getApplicationContext());
            } else if (!Objects.equals(StoresConstants.STORE_SECTOR, "0")) {
                //get all store names by sector name
                spinnerStoreNamePeriodicArrayList = GetAllStoresNamesBySectorNameDropdown.getAllStoresNamesBySectorNameDropdown(getApplicationContext());
            } else if (StoresConstants.STORE_NUMBER != 0) {
                //get all store names by store num
                spinnerStoreNamePeriodicArrayList = GetAllStoresNamesByStoreNumDropdown.getAllStoresNamesByStoreNumDropdown(getApplicationContext());
            } else {
                CustomToast.customToast(getApplicationContext(), "عفو ليس لديك صلاحية لعرض البيانات");
            }
            //get all store names
//            spinnerStoreNamePeriodicArrayList = GetAllStoresNamesDropdown.getAllStoresNamesDropdown(getApplicationContext());
            //get all store names by sector name
//            spinnerStoreNamePeriodicArrayList = GetAllStoresNamesBySectorNameDropdown.getAllStoresNamesBySectorNameDropdown(getApplicationContext());
            //get all store names by store num
//            spinnerStoreNamePeriodicArrayList = GetAllStoresNamesByStoreNumDropdown.getAllStoresNamesByStoreNumDropdown(getApplicationContext());
        } catch (Exception e) {
            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
        }
        ArrayAdapter<StoresNamesModel> spinnerStoreNamesPeriodicAdapter =
                new ArrayAdapter<StoresNamesModel>(
                        GetStoreReportInPeriodic.this,
                        android.R.layout.simple_spinner_item,
                        spinnerStoreNamePeriodicArrayList);
        spinnerStoreNamesPeriodicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeNamePeriodicSpinner.setAdapter(spinnerStoreNamesPeriodicAdapter);
        storeNamePeriodicSpinner.setOnItemSelectedListener(this);
//        storeNamePeriodicSpinner.setPrompt("أختر المخزن");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sn = String.valueOf(parent.getItemAtPosition(position));
        STORE_NAME_REPORT = sn;
        System.out.println(STORE_NAME_REPORT);
        storeReportPeriodicModelArrayList.clear();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        CustomToast.customToast(getApplicationContext(), "فضلا أختر أسم المخزن");
        getResultItemsReportPeriodicBtn.setVisibility(View.INVISIBLE);

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
                storeReportInCertainPeriodDateAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                storeReportInCertainPeriodDateAdapter.getFilter().filter(query);
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


