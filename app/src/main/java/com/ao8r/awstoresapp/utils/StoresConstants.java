package com.ao8r.awstoresapp.utils;


import java.sql.Connection;

public class StoresConstants {

    //connection info
    public static Connection connection;
    public static final String HOST = "41.33.226.212";
    public static final int PORT = 5010;
    public static final String DB_NAME = "awco";
    public static final String USERNAME = "awco";
    public static final String PASSWORD = "awco";
    public static final String SRC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    //employee info
    public static String EMP_ID;
    public static String EMP_NAME;
    public static String EMP_LOCATION;
    public static String EMP_JOB;
    public static String EMP_MOBILE;
    public static String EMP_USERNAME;

    //    public static  String NEW_PASSWORD;
    public static String CURRENT_PASSWORD;
    // user info
    public static long CURRENT_USER_ID;
    public static String LOGIN_USER;
    public static int USER_CONTROL;
    public static  int USER_PERMISSION_LEVEL;
    public static int STORE_NUMBER;
    public static String STORE_SECTOR;
    public static int ITEM_NUMBER;
    public static int FAV_ITEM_NUMBER;
    //    itemsEXTRA info
    public static String EXTRA_TOTAL_QUANTITY;
    public static String EXTRA_ITEM_NAME;
    public static String EXTRA_ITEM_NUMBER;
    public static String ITEM_NAME = "";
    public static String STORE_NAME_REPORT = "";
    public static String REPORT_ITEM_CODE = "رقــم الصـنف      :   ";
    public static String REPORT_ITEM_NAME = "إســم الصــنف     :   ";
    public static String REPORT_TOTAL_QTY = "الرصـيد الحـالى   :   ";
    public static String REPORT_START_QTY = "رصيد بداية المدة  :   ";
    public static String REPORT_INCOME_QTY = "إجمـالى الـوارد   :  ";
    public static String REPORT_OUTCOME_QTY = "إجمـالى الصـادر   :  ";
    public static String REPORT_FINAL_QTY = "رصيد نهاية المدة  :   ";

    //    titles
    public static final String MENU_TITLE = "القائمة الرئيسية";
    public static final String SEARCH_TITLE = "إستعلام برقم الصنف";
    public static final String FAVORITE_TITLE = "قائمة المفضلة";
    public static final String ADVANCED_SEARCH_TITLE = "بحث بالإسم";
    public static final String TOP_TEN_MORE_MOVES = "الأصناف الأكثر حركة";
    public static final String STORE_ANALYSIS_TITLE = "جرد مخزن";
    public static final String STORE_ANALYSIS_PERIODIC_TITLE = "حركة مخزن فى فترة";
    public static final String REQUEST_NEW_USER_TITLE = "طلب مستخدم جديد";
    public static final String FORGOT_USER_PASSWORD_TITLE = "تعيين كلمة مرور جديدة";
    public static String START_DATE = "";
    public static String END_DATE = "";
    public static String CURRENT_DATE = "";
    public static final String ABOUT_APP = "حول التطبيق";

    //    write/read file

    public static String URL_CONNECT_LINK;
    public static final String LOGIN_FILE_NAME = "storesBaseIp.txt";
    public static String BASE_IP = "baseIp";

    //Twilio SMS Sender info
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";
    public static final String SENDER_PHONE_NUMBER = "";
}
