package com.waggoner.audioexamples.inputs.inputCallbacks;

import android.util.Log;

import com.waggoner.audioexamples.util.AudioUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by nathanielwaggoner on 8/18/15.
 */
public class FileWriterCallback implements InputBufferCallback {
    public static String TAG = FileWriterCallback.class.getName();
    FileOutputStream fos;
    String mFileName;
    String fileType = ".pcm";

    public FileWriterCallback(String fileName) {
        mFileName = fileName;
    }


    @Override
    public void prepare() {
        Log.e("XapPTest","Calling prepare:  filename: "+mFileName);
        try {
            fos = new FileOutputStream(mFileName);
        } catch (FileNotFoundException fnfe) {
            Log.e("Xapptest",Log.getStackTraceString(fnfe));
            Log.e(TAG, "File Not Found Exception in FileWriterCallback prepare()");
        }
    }

    @Override
    public void handleBuffer(short[] buffer) {
        if (fos == null) {
            Log.e(TAG, "fos in FileWriterCallback handleBuffer was null");
            throw new RuntimeException("FOS failed to load, failing to write buffer");
        }
        try {
            byte bData[] = AudioUtil.short2byte(buffer);
            fos.write(bData);
        } catch (IOException e) {
            Log.e(TAG, "IOException in FileWriterCallback handleBuffer()");
        }
    }

    @Override
    public void finish() {
        try {
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, "IOException in FileWriterCallback finish()");

        }
    }


}
