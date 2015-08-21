package com.waggoner.audioexamples.util;

import android.content.Context;

import java.io.File;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public class FileUtil {
    public static String RECORDINGS_FILE= "recordings";

    public static void setUpFilesDir(Context ctx) {
        getRecordingsFile(ctx).mkdirs();
    }
    public static String generateRecordingsFileName(Context ctx) {
        return ctx.getExternalFilesDir(null)+File.separator+RECORDINGS_FILE+File.separator+generateFileName();
    }
    public static String generateFileName() {
        return "recording_"+System.currentTimeMillis();
    }

    public static File getRecordingsFile(Context ctx) {
        return new File(ctx.getExternalFilesDir(null)+File.separator+RECORDINGS_FILE+File.separator);
    }

}
