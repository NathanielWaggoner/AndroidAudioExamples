package com.waggoner.audioexamples.outputs;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import com.waggoner.audioexamples.core.OutputSource;
import com.waggoner.audioexamples.util.AudioUtil;

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
    int BUFFER_SIZE = 512;
    AudioTrack mAudioTrack;
    String filePath;

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

    /**
     * Constructor for dealing with files.  We're only going to use this on our own files right now, so its not set up
     * to parse useful data or convert.  Our own recordings are all going to be 16bit pcm written straight to file so we can
     * just do them and not fuss
     */
    public AudioTrackSource() {
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, 1, AudioFormat.ENCODING_PCM_16BIT,
                BUFFER_SIZE, AudioTrack.MODE_STREAM);
    }
    public AudioTrackSource(File file) {
        this();

    }

    public void setPlaybackFile(File file) {
        filePath = file.getAbsolutePath();

    }
    public void setUpFileStream() {

        try {
            fis = new FileInputStream(filePath);
            isPlaybackReady = true;
        } catch (IOException e) {
            Log.e("XapPtest", Log.getStackTraceString(e));
        }
    }

    Thread playbackThread;


    @Override
    public void playAudio() {
        if(play.get()) {
            play.set(false);
            mAudioTrack.stop();
            try {
                playbackThread.join();
            } catch(InterruptedException ie){
                Log.e("XapPTest",Log.getStackTraceString(ie));
            }
        }
        setUpFileStream();
        playbackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] bytes = new byte[BUFFER_SIZE * 2];
                    int n;
                    while (play.get()&& ((n = fis.read(bytes)) != -1)) {
                        short[] deezShorts = AudioUtil.bytesToShorts(bytes);
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

    @Override
    public void stopAudio() {

    }

    @Override
    public void destroy() {

    }
}
