package com.waggoner.audioexamples.util;

import android.content.Context;
import android.util.Log;

import com.waggoner.audioexamples.inputs.MediaRecorderInput;

import java.io.File;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public class FileUtil {
    public static String RECORDINGS_FILE= "recordings";

    public static void setUpFilesDir(Context ctx) {
        File f = getRecordingsFile(ctx);
        f.mkdirs();
        makeNewSubDir(f.getAbsolutePath() +File.separator

                + MediaRecorderInput.THREE_GP_FILE_DIRECTORY);

     }
    public static String generateRecordingsFileName(Context ctx, String dir) {
        String base =  ctx.getExternalFilesDir(null)+File.separator+RECORDINGS_FILE+File.separator;
        if(dir!=null) {
            base+=dir+File.separator+generateFileName();;
        } else {
            base+= generateFileName();
        }
        return base;
    }
    public static String generateFileName() {
        return "recording_"+System.currentTimeMillis();
    }

    public static boolean makeNewSubDir(String name) {
        Log.e("Xapptest","Making new file: "+name);
        return new File(name).mkdirs();
    }

    public static File getRecordingsFile(Context ctx) {
        return new File(ctx.getExternalFilesDir(null)+File.separator+RECORDINGS_FILE+File.separator);
    }
    public static File getRecordingsFileForSubDir(Context ctx, String subDir) {
        return new File(ctx.getExternalFilesDir(null)+File.separator+RECORDINGS_FILE+File.separator+subDir+File.separator);
    }

}
