package com.andreschnabel.browseandplay;

import java.io.File;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Utils {

    public static void showMessage(Context context, String message) {
        Log.d("INFO", message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isMediaFile(File f) {
        return f.isFile() && f.getName().endsWith(".mp3");
    }
}
