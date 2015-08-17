package com.waggoner.audioexamples.sources;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

import com.waggoner.audioexamples.core.AudioSource;
import com.waggoner.audioexamples.core.WavInfo;

import java.io.IOException;

/**
 * Created by nathanielwaggoner on 8/14/15.
 */
public class SuperPoweredSource implements AudioSource {

    boolean playing = false;
    public SuperPoweredSource(Context ctx, int resource) {
        String samplerateString = null, buffersizeString = null;
        if (Build.VERSION.SDK_INT >= 17) {
            AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
            samplerateString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            buffersizeString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
            Log.e("XappTest","SysSampleRate: "+samplerateString+" SysBuffSize: "+buffersizeString);
        }
        if (samplerateString == null) samplerateString = "44100";
        if (buffersizeString == null) buffersizeString = "512";
        AssetFileDescriptor fd0 = ctx.getResources().openRawResourceFd(resource);
        Log.e("XappTest","FD startOffset: "+fd0.getStartOffset()+" and length: "+fd0.getLength());
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

    static {
        System.loadLibrary("SuperpoweredExample");
        // load relevant source library here.
    }
    private native void SuperpoweredExample(String apkPath, long[] offsetAndLength);
    private native void onPlayPause(boolean play);

}
