package com.waggoner.audioexamples.util;

import android.content.Context;

import java.io.File;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public class FileUtil {
    public static String RECORDINGS_FILE= "recordings";

    public static String generateRecordingsFileName(Context ctx) {
        return ctx.getExternalFilesDir(null)+File.pathSeparator+RECORDINGS_FILE+File.pathSeparator+generateFileName();
    }
    public static String generateFileName() {
        return "recording_"+System.currentTimeMillis();
    }

    public File getRecordingsFile(Context ctx) {
        return new File(ctx.getExternalFilesDir(null)+File.pathSeparator+RECORDINGS_FILE);
    }

}
