package com.ao8r.awstoresapp.customiz_widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.models.StoreReportModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExportAsPdfFile {

    //save view as pdf
//    Code snippet
    public static void generatePdfFromView(Context context, View view) {
        // Create a PDF document.
        PdfDocument document = new PdfDocument();

        // Get the dimensions of the view.
        int width = view.getWidth();
        int height = view.getHeight();

        // Create a bitmap image of the view.
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
//        view.layout(0, 0, view.getWidth(), view.getHeight());
        view.draw(canvas);


        // Create a page in the PDF document.
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(width, height, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        // Draw the bitmap image to the PDF page.
        Canvas pdfCanvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
//        pdfCanvas.save();
        pdfCanvas.drawPaint(paint);
        pdfCanvas.drawBitmap(bitmap, 0, 0, null);

        // Finish the page and add it to the document.
        document.finishPage(page);

        // Save the PDF document to a file.
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "viewPDF.pdf");
        try {
            document.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the PDF document.
        document.close();
    }


    //save img as pdf

    public static void generatePdfFromRecyclerView(Context context, RecyclerView recyclerView, String fileName) {
        try {
            // Measure the recycler view
            int totalHeight = 0;
            int maxWidth = 0;
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = recyclerView.getChildAt(i);
                view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                totalHeight += view.getMeasuredHeight();
                maxWidth = Math.max(maxWidth, view.getMeasuredWidth());
            }

            // Create a bitmap and a canvas to draw the views
            Bitmap bigBitmap = Bitmap.createBitmap(maxWidth, totalHeight, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            // Draw each child view on the canvas
            for (int i = 0; i < childCount; i++) {
                View view = recyclerView.getChildAt(i);
                bigCanvas.save();
                bigCanvas.translate(0, i * view.getHeight());
                view.draw(bigCanvas);
                bigCanvas.restore();
            }

            // Save the bitmap as a PDF using iText library
            File file = createPdfFile(context, fileName);
            if (file != null) {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bigBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                image.setAbsolutePosition(0, 0);
                document.add(image);
                document.close();
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private static File createPdfFile(Context context, String fileName) {
        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "/");
        if (!pdfFolder.exists()) {
            if (!pdfFolder.mkdirs()) {
                return null;
            }
        }

        File pdfFile = new File(pdfFolder, fileName + ".pdf");
        if (pdfFile.exists()) {
            pdfFile.delete();
        }

        try {
            if (pdfFile.createNewFile()) {
                return pdfFile;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
// save pdf from RV as img

    public static void generatePDFFromRV(RecyclerView view, String fileName) {

        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }

                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            Document document = new Document(PageSize.A4);
            final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName + ".pdf");
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < size; i++) {

                try {
                    //Adding the content to the document
                    Bitmap bmp = bitmaCache.get(String.valueOf(i));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Image image = Image.getInstance(stream.toByteArray());
                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
                    image.scalePercent(scaler);
                    image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER | com.itextpdf.text.Image.ALIGN_TOP);
//                    TODO: edited 18-04-2023
                    float leftMargin = 10;
                    float rightMargin = 10;
                    float topMargin = 10;
                    float bottomMargin = 10;
                    document.setPageSize(new Rectangle(image.getWidth() + leftMargin + rightMargin, image.getHeight() + topMargin + bottomMargin));
                    document.newPage();
                    document.setMargins(leftMargin, rightMargin, topMargin, bottomMargin);
                    image.setAbsolutePosition(leftMargin, bottomMargin);
                    //
                    if (!document.isOpen()) {
                        document.open();
                    }
                    document.add(image);

                } catch (Exception ex) {
                    Log.e("TAG-ORDER PRINT ERROR", ex.getMessage());
                }
            }

            if (document.isOpen()) {
                document.close();
            }


        }

    }

    //save REPORT TEXT AS PDF file

    public static void saveAsPDF(
            ArrayList<StoreReportModel> storeReportModelArrayList,
            String fileName,
            String itemCode,
            String itemName,
            String totalQtyNow,
            String lastDate,
            Context context) throws DocumentException {
        Document document = new Document();
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName + ".pdf");
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        //
        Font f = FontFactory.getFont("assets/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        f.setStyle(Font.BOLD);
        f.setColor(new BaseColor(context.getResources().getColor(R.color.colorPrimaryDark)));


//        PdfPTable pdfTable = new PdfPTable(1);
        PdfPTable table = new PdfPTable(4);

        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        table.addCell(new Paragraph(itemName,f));
        table.addCell(new Paragraph(itemCode,f));
        table.addCell(new Paragraph(totalQtyNow,f));
        table.addCell(new Paragraph(lastDate,f));

        for (StoreReportModel item : storeReportModelArrayList) {
            table.addCell(new Paragraph(item.getItemNameReport(),f));
            table.addCell(item.getItemCode().substring(0, 7));
            table.addCell(item.getTotalQtyNow());
            table.addCell(item.getLastDate().substring(0, 10));
        }

        document.add(table);

        document.close();
    }

    //
    public static void saveAsPDFPeriodic(
            ArrayList<StoreReportModel> storeReportModelArrayList,
            String itemCode,
            String itemName,
            String totalQtyInStartedPoint,
            String totalQtyIncome,
            String totalQtyOutcome,
            String totalQtyEndPoint,
            String fileName,
            Context context) throws DocumentException {
        Document document = new Document();
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName + ".pdf");
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        //
        Font f = FontFactory.getFont("assets/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        f.setStyle(Font.BOLD);
        f.setColor(new BaseColor(context.getResources().getColor(R.color.colorPrimaryDark)));
//        PdfPTable pdfTable = new PdfPTable(1);

        PdfPTable table = new PdfPTable(6);

        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        table.addCell(new Paragraph(itemName,f));
        table.addCell(new Paragraph(itemCode,f));
        table.addCell(new Paragraph(totalQtyInStartedPoint,f));
        table.addCell(new Paragraph(totalQtyIncome,f));
        table.addCell(new Paragraph(totalQtyOutcome,f));
        table.addCell(new Paragraph(totalQtyEndPoint,f));

        for (StoreReportModel item : storeReportModelArrayList) {
            table.addCell(new Paragraph(item.getItemNameReport(),f));
            table.addCell(item.getItemCode().substring(0, 7));
            table.addCell(item.getTotalQtyInStartedPoint());
            table.addCell(item.getTotalQtyIncome());
            table.addCell(item.getTotalQtyOutcome());
            table.addCell(item.getTotalQtyEndPoint());
        }

        document.add(table);

        document.close();
    }
}

