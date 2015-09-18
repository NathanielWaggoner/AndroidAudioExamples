package com.waggoner.audioexamples.outputs;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

import com.waggoner.audioexamples.core.OutputSource;

import java.io.File;
import java.io.IOException;

/**
 * Created by nathanielwaggoner on 8/14/15.
 */
public class SuperPoweredSource implements OutputSource {

    boolean playing = false;

    public SuperPoweredSource(Context ctx, int resource) {
        String samplerateString = null, buffersizeString = null;

        //  very important to tie your buffer sizes and smaple rates tot he device is possible.
        if (Build.VERSION.SDK_INT >= 17) {
            AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
            samplerateString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            buffersizeString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
        }
        // all devices are supposed to support 44100, and 512 is a typically conveniest buffer - neither of these is garunteed to be correct.
        if (samplerateString == null) samplerateString = "44100";
        if (buffersizeString == null) buffersizeString = "512";

        // there's no C api's for hitting raw directly, you have to pass in
        AssetFileDescriptor fd0 = ctx.getResources().openRawResourceFd(resource);

        long[] params = {
                fd0.getStartOffset(),
                fd0.getLength(),
                Integer.parseInt(samplerateString),
                Integer.parseInt(buffersizeString)
        };
        try {
            fd0.getParcelFileDescriptor().close();
        } catch (IOException e) {
            android.util.Log.d("", "Close error.");
        }

        SuperpoweredExample(ctx.getPackageResourcePath(), params);

    }


    @Override
    public void playAudio() {
        playing = !playing;
        onPlayPause(playing);
    }

    @Override
    public void stopAudio() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setPlaybackFile(File f) {

    }

    static {
        System.loadLibrary("SuperpoweredExample");
        // load relevant source library here.
    }

    private native void SuperpoweredExample(String apkPath, long[] offsetAndLength);

    private native void onPlayPause(boolean play);

}
