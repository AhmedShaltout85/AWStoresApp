package com.ao8r.awstoresapp.customiz_widgets;


import com.ao8r.awstoresapp.utils.StoresConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDatePicker {

public static String currentDate(){
    String pattern = "yyyy-MM-dd";
    String dateInString = new SimpleDateFormat(pattern).format(new Date());
    StoresConstants.CURRENT_DATE = dateInString;
    return StoresConstants.CURRENT_DATE;
}

//    public static int getMonthFormat(String month){
//        if(month == "JAN")
//            return 1;
//        if(month == "FEB")
//            return 2;
//        if(month == "MAR")
//            return 3;
//        if(month == "APR")
//            return 4;
//        if(month == "MAY")
//            return 5;
//        if(month == "JUN")
//            return 6;
//        if(month == "JUL")
//            return 7;
//        if(month == "AUG")
//            return 8;
//        if(month == "SEP")
//            return 9;
//        if(month == "OCT")
//            return 10;
//        if(month == "NOV")
//            return 11;
//        if(month == "DEC")
//            return 12;
//
//        //default should never happen
//        return 1;
//    }

//    public static String makeDateString(int day, String month, int year)
//    {
//        return year+"-"+getMonthFormat(month)+"-"+day;
//    }
//    public static void customDatePicker(Context context, int day, int month, int year, String selectedDate) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, day);
//        calendar.set(Calendar.MONTH + 1, month);
//        calendar.set(Calendar.YEAR, year);
//        selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
//
////        datePickerDialog = new DatePickerDialog(context,(view, year1, month1, dayOfMonth) ->
////                datePicker = (year1+ "-"+month1+"-"+dayOfMonth)
////                ,
////                year,
////                month,
////                day
////                );
////        datePickerDialog.show();
//
//    }
}
