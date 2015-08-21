package com.waggoner.audioexamples.basic;

import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.util.Log;

import com.waggoner.audioexamples.core.OutputSource;
import com.waggoner.audioexamples.core.Channel;
import com.waggoner.audioexamples.core.Effect;
import com.waggoner.audioexamples.outputs.MediaPlayerSource;
import com.waggoner.audioexamples.outputs.StaticAudioTrackSource;

/**
 * Created by nathanielwaggoner on 8/6/15.
 */
public class Drum implements Channel {
    public OutputSource mAudioSource;
    MediaPlayer.OnCompletionListener mOnCompletionListener;
    AudioTrack.OnPlaybackPositionUpdateListener mOnPlaybackPositioNupdateListener;

    /**
     * TODO:
     *
     * Seek to functionality when play is pressed (seek to beginning)
     */
    @Override
    public void play() {
        mAudioSource.playAudio();
    }

    Effect mEffect;

    public Drum(OutputSource source, Effect effect){
        setAudioSource(source);
        setEffect(effect);
    };
    @Override
    public void setAudioSource(OutputSource audioSource) {
        mAudioSource = audioSource;
        if(mAudioSource instanceof MediaPlayerSource) {
            mOnCompletionListener = new MediaPlayerOnCompletionListener();
            ((MediaPlayerSource) mAudioSource).setOnCompletionListener(mOnCompletionListener);
        } else if (mAudioSource instanceof StaticAudioTrackSource) {
            Log.e("XappTest","Settong plabakupdate listener");
            StaticAudioTrackSource source = (StaticAudioTrackSource) mAudioSource;
            source.setPlaybackPositionUpdateListener(mOnPlaybackPositioNupdateListener);
        }
    }

    @Override
    public void processBuffer(short[] buffer) {

    }

    @Override
    public OutputSource getAudioSource() {
        return mAudioSource ;
    }

    @Override
    public void destroy() {
        mAudioSource.destroy();
        mEffect.destroy();
    }

    @Override
    public void setEffect(Effect effect) {
        mEffect = effect;
    }

    @Override
    public Effect getEffect() {
        return mEffect;
    }

    private class MediaPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.seekTo(0);
        }
    }
    private class OnPlaybackPositionUpdateListener implements AudioTrack.OnPlaybackPositionUpdateListener {
        /**
         * Called on the listener to notify it that the previously set marker has been reached
         * by the playback head.
         */
        @Override
        public void onMarkerReached(AudioTrack track){
            Log.e("XappTest", "Marker was reached!");
            if(track.getPlayState()!= AudioTrack.PLAYSTATE_STOPPED){
                track.stop();
            }
            track.reloadStaticData();
            track.setPlaybackHeadPosition(0);
        };

        /**
         * Called on the listener to periodically notify it that the playback head has reached
         * a multiple of the notification period.
         */
        @Override

        public void onPeriodicNotification(AudioTrack track){

        };

    }
}
