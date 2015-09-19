package com.waggoner.audioexamples.outputs;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import com.waggoner.audioexamples.basic.AudioTrackBufferCallback;
import com.waggoner.audioexamples.core.OutputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AudioTrack for playing longer files
 * Created by nathanielwaggoner on 8/21/15.
 */
public class AudioTrackSource implements OutputSource {
    public static String TAG = StaticAudioTrackSource.class.getName();
    public static int BUFFER_SIZE = 512;
    public static int SAMPLE_RATE = 16000;

    AudioTrack mAudioTrack;
    String filePath;
    Thread playbackThread;
    AudioTrackBufferCallback brt;

    AtomicBoolean play = new AtomicBoolean(false);
    // file sample rate
    int sampleRate;
    // number of channels in the file
    int channelCount;
    // the channel configuration contstant to use.  Derived from channel count.
    int channelConfig;
    // long as returned fom MediaExtractor
    long duration;

    FileInputStream fis;

    public boolean isPlaybackReady = false;

    public void setBrt(AudioTrackBufferCallback brt) {
        this.brt = brt;
    }

    public AudioTrackSource() {
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLE_RATE, 1, AudioFormat.ENCODING_PCM_16BIT,
                BUFFER_SIZE, AudioTrack.MODE_STREAM);
    }
    @Override
    public void setPlaybackFile(File file) {
        if(file!=null) {
            filePath = file.getAbsolutePath();
        }

    }
    public void setUpFileStream() {

        try {
            fis = new FileInputStream(filePath);
            isPlaybackReady = true;
        } catch (IOException e) {
            Log.e("XapPtest", Log.getStackTraceString(e));
        }
    }


    @Override
    public void playAudio() {
        resetTrack();
        setUpFileStream();

        // configure byte[] source
        // pollByte[] source
        // write out
        playbackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] bytes = new byte[BUFFER_SIZE * 2];
                    int n;
                    while (play.get()&& ((n = fis.read(bytes)) != -1)) {
                        mAudioTrack.write(bytes, 0, bytes.length);
                }
                    play.set(false);
                    mAudioTrack.stop();
                } catch (Exception e) {
                    Log.e("XapPTest", Log.getStackTraceString(e));
                }
            }
        });
        play.set(true);
        mAudioTrack.play();
        playbackThread.start();
    }

    public void playAudioFromExternalWriter(){
        resetTrack();
        play.set(true);
        mAudioTrack.play();
    }


    public void handleShortArray(short[] shorts) {
        Log.e("XapPTest","Should be writing sine waves");
        mAudioTrack.write(shorts,0,shorts.length);
    }

    public void resetTrack() {
        if(play.get()) {
            play.set(false);
            mAudioTrack.stop();
            if(playbackThread!=null) {
                try {
                    playbackThread.join();
                } catch (InterruptedException ie) {
                    Log.e("XapPTest", Log.getStackTraceString(ie));
                }
            }
        }
    }
    @Override
    public void stopAudio() {
        resetTrack();
    }

    @Override
    public void destroy() {

    }
}
