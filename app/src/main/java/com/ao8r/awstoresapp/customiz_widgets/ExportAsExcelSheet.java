package com.ao8r.awstoresapp.customiz_widgets;

import android.os.Environment;

import com.ao8r.awstoresapp.models.StoreReportModel;
import com.ao8r.awstoresapp.utils.StoresConstants;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExportAsExcelSheet {

//    export excel sheet for store in between two date

    public static void exportStoreReportInPeriodicDateAsExcelSheet(
            String itemCode,
            String itemName,
            String totalQtyInStartedPoint,
            String totalQtyIncome,
            String totalQtyOutcome,
            String totalQtyEndPoint,
            String fileName,
            ArrayList<StoreReportModel> storeReportModelArrayList) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet(StoresConstants.START_DATE +"--"+StoresConstants.END_DATE);

        XSSFRow row;

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(0);
        Cell cell0 = row.createCell(0);
        Cell cell1 = row.createCell(1);
        Cell cell2 = row.createCell(2);
        Cell cell3 = row.createCell(3);
        Cell cell4 = row.createCell(4);
        Cell cell5 = row.createCell(5);


        cell0.setCellValue(itemCode);
        cell0.setCellStyle(cellStyle);
        cell1.setCellValue(itemName);
        cell1.setCellStyle(cellStyle);
        cell2.setCellValue(totalQtyInStartedPoint);
        cell2.setCellStyle(cellStyle);
        cell3.setCellValue(totalQtyIncome);
        cell3.setCellStyle(cellStyle);
        cell4.setCellValue(totalQtyOutcome);
        cell4.setCellStyle(cellStyle);
        cell5.setCellValue(totalQtyEndPoint);
        cell5.setCellStyle(cellStyle);


        for (int i = 0; i < storeReportModelArrayList.size(); i++) {
            row = sheet.createRow(i + 1);
            StoreReportModel obj = storeReportModelArrayList.get(i);
            // assuming obj has two fields
            System.out.println("<<جـــــــــارى الطـــــــباعه>>");
            Cell cell = row.createCell(0);
            cell.setCellValue(obj.getItemCode().substring(0,7));
            System.out.println(obj.getItemCode().substring(0,7));

            cell = row.createCell(1);
            cell.setCellValue(obj.getItemNameReport());
            System.out.println(obj.getItemNameReport());

            cell = row.createCell(2);
            cell.setCellValue(obj.getTotalQtyInStartedPoint());
            System.out.println(obj.getTotalQtyInStartedPoint());

            cell = row.createCell(3);
            cell.setCellValue(obj.getTotalQtyIncome());
            System.out.println(obj.getTotalQtyIncome());

            cell = row.createCell(4);
            cell.setCellValue(obj.getTotalQtyOutcome());
            System.out.println(obj.getTotalQtyOutcome());

            cell = row.createCell(5);
            cell.setCellValue(obj.getTotalQtyEndPoint());
            System.out.println(obj.getTotalQtyEndPoint());
        }

        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/"+ fileName+".xlsx";
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();


    }
//
//    for export store

    public static void exportStoreReportAsExcelSheet(
            String itemCode,
            String itemName,
            String totalQtyNow,
            String fileName,
            ArrayList<StoreReportModel> storeReportModelArrayList) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet( "تقرير جرد " +StoresConstants.STORE_NAME_REPORT);

        XSSFRow row;

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


        row = sheet.createRow(0);
        Cell cell0 = row.createCell(0);
        Cell cell1 = row.createCell(1);
        Cell cell2 = row.createCell(2);


        cell0.setCellValue(itemCode);
        cell0.setCellStyle(cellStyle);
        cell1.setCellValue(itemName);
        cell1.setCellStyle(cellStyle);
        cell2.setCellValue(totalQtyNow);
        cell2.setCellStyle(cellStyle);


        for (int i = 0; i < storeReportModelArrayList.size(); i++) {
            row = sheet.createRow(i + 1);
            StoreReportModel obj = storeReportModelArrayList.get(i);
            // assuming obj has two fields
            System.out.println("<<جـــــــــارى الطـــــــباعه>>");
            Cell cell = row.createCell(0);
            cell.setCellValue(obj.getItemCode().substring(0,7));
            System.out.println(obj.getItemCode().substring(0,7));

            cell = row.createCell(1);
            cell.setCellValue(obj.getItemNameReport());
            System.out.println(obj.getItemNameReport());

            cell = row.createCell(2);
            cell.setCellValue(obj.getTotalQtyNow());
            System.out.println(obj.getTotalQtyNow());
        }

//        String filePath = Environment.getExternalStorageDirectory().getPath() +"/"+ fileName+".xlsx";
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +"/"+ fileName+".xlsx";
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


}

