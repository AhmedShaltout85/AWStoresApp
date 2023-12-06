package com.ao8r.awstoresapp.customiz_widgets;
import static com.ao8r.awstoresapp.utils.StoresConstants.BASE_IP;
import android.os.Environment;
import android.util.Log;
import com.ao8r.awstoresapp.utils.StoresConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadWriteFileFromInternalMem {


public static String getIpFromFile() {
    String txt=" ";
    // Get the directory for the user's public pictures directory.
//    File root = new File(Environment.getExternalStorageDirectory(), BASE_IP);
    File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BASE_IP);
    if (!root.exists()) {
        root.mkdirs();
    }
    File file = new File(root, StoresConstants.LOGIN_FILE_NAME);

    // Save your stream, don't forget to flush() it before closing it.
    try
    {

        FileInputStream fOut = new FileInputStream(file);
        if ( fOut != null )
        {
            InputStreamReader inputStreamReader = new InputStreamReader(fOut);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ( (receiveString = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(receiveString);
            }
            fOut.close();
            txt = stringBuilder.toString();
        }

    }
    catch (IOException e)
    {
        Log.e("Exception", "File write failed: " + e.toString());
    }
    //*********************************
    return txt;
}


    public static void generateNoteOnSD( String sBody) {
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BASE_IP);
            if (!root.exists()) {
                root.mkdirs();
            }
            File sdFile = new File(root, StoresConstants.LOGIN_FILE_NAME);
            FileWriter writer = new FileWriter(sdFile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// login screen
////      SAVE To File
//            try {
//                FileOutputStream fileOutputStream = openFileOutput(URL_LINK,MODE_PRIVATE);
//                PrintWriter printWriter = new PrintWriter(fileOutputStream);
//                printWriter.println(getUrl);
//                printWriter.close();
//                fileOutputStream.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        Toast.makeText(getApplicationContext(), getUrl, Toast.LENGTH_SHORT).show();
//
////        READ From File
//
//            try {
//                FileInputStream fileInputStream = openFileInput(URL_LINK);
//                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                URL_CONNECT_LINK = bufferedReader.readLine();
////                Toast.makeText(getApplicationContext(), URL_CONNECT_LINK, Toast.LENGTH_SHORT).show();                    CustomToast.customToast(getApplicationContext(),URL_CONNECT_LINK + "تم حفظ BaseUrl");
//                CustomToast.customToast(getApplicationContext(),URL_CONNECT_LINK + "تم حفظ BaseUrl");
//
//
//                bufferedReader.close();
//                inputStreamReader.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//