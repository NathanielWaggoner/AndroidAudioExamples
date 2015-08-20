package com.waggoner.audioexamples.outputs;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.waggoner.audioexamples.core.OuputSource;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by nathanielwaggoner on 8/6/15.
 */
public class MediaPlayerSource implements OuputSource {

    public static String TAG = MediaPlayerSource.class.getName();
    MediaPlayer mySource;
    /**
     * TODO:
     * or setDataSource(String), or setDataSource(FileDescriptor, long, long)
     */

    /**
     * Create a source from raw resources
     * @param context
     * @param raw_sound_file
     */
    public MediaPlayerSource(Context context, int raw_sound_file) {
        // we don't prepare here, create handles that
        mySource= MediaPlayer.create(context, raw_sound_file);
    }

    /**
     * Create a source from a Uri
     * @param context
     * @param uri
     */
    public MediaPlayerSource(Context context, Uri uri) {
        try {
            mySource = new MediaPlayer();
            // There are a bunch of AudioStreams:
            /** The audio stream for system sounds */
            // public static final int STREAM_SYSTEM = AudioSystem.STREAM_SYSTEM;
            /** The audio stream for the phone ring */
            // public static final int STREAM_RING = AudioSystem.STREAM_RING;
            /** The audio stream for music playback */
            // public static final int STREAM_MUSIC = AudioSystem.STREAM_MUSIC;
            /** The audio stream for alarms */
            // public static final int STREAM_ALARM = AudioSystem.STREAM_ALARM;
            /** The audio stream for notifications */
            //  public static final int STREAM_NOTIFICATION = AudioSystem.STREAM_NOTIFICATION;
            /** @hide The audio stream for phone calls when connected to bluetooth */
            // public static final int STREAM_BLUETOOTH_SCO = AudioSystem.STREAM_BLUETOOTH_SCO;
            /** @hide The audio stream for enforced system sounds in certain countries (e.g camera in Japan) */
            // public static final int STREAM_SYSTEM_ENFORCED = AudioSystem.STREAM_SYSTEM_ENFORCED;
            /** The audio stream for DTMF Tones */
            // public static final int STREAM_DTMF = AudioSystem.STREAM_DTMF;
            /** @hide The audio stream for text to speech (TTS) */
            // public static final int STREAM_TTS = AudioSystem.STREAM_TTS;

            mySource.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mySource.setDataSource(context, uri);
            // need a prepare call here
            // prepare is a blocking call!  We're fine with that, but if you're not you can use perpareAsyn()
            // mySource.setOnPreparedListener(new MyOnPreparedListener())
            mySource.prepare();
        } catch(IOException exception) {
            Log.e(TAG,Log.getStackTraceString(exception));
        }
    }

    public MediaPlayerSource(FileDescriptor fd) {
        try {
            mySource = new MediaPlayer();
            mySource.setDataSource(fd);
            mySource.prepare();
        } catch(IOException exception) {

        }
    }

    /**
     * Create a source from a FileDescriptor
     */

    public void destroy(){
        if(mySource != null) {
            mySource.stop();
            mySource.release();
            mySource = null;
        }
    }
    @Override
    public void playAudio() {
        if(mySource.isPlaying()) {
            mySource.pause();
        }
        mySource.seekTo(0);
        mySource.start();

    }

    @Override
    public void stopAudio() {
        mySource.stop();
    }

    public void rewind() {
        mySource.seekTo(0);;
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener){
        if(mySource!=null) {
            mySource.setOnCompletionListener(listener);
        }
    };
}
