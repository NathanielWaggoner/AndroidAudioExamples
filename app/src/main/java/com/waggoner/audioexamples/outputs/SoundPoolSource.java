package com.waggoner.audioexamples.outputs;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.waggoner.audioexamples.core.OutputSource;

import java.io.File;

/**
 *
 * Sound pools are easier than audio tracks, but faster than MediaPlayers.  We like them!
 * Created by nathanielwaggoner on 8/7/15.
 */
public class SoundPoolSource implements OutputSource {

    SoundPool sp;
    int soundId;

    int maxStreams=1;
    int streamType = AudioManager.STREAM_MUSIC;
    int sourceQuality = 0; // this doesn't do anything right now

    float leftVolume = 1; // range = 0.0 to 1.0)
    float rightVolume = 1; // range = 0.0 to 1.0)

    int priority = 0; // stream priority (0 = lowest priority)
    int loop = 0 ;
    float rate = 1; // playback rate
    /**
     * Sound pool from raw resources.
     * @param context
     * @param rawId
     */
    public SoundPoolSource(Context context, int rawId) {
        sp = new SoundPool(maxStreams, streamType, sourceQuality);
        /** soundId for Later handling of sound pool **/

        soundId = sp.load(context,rawId, 1);

    }
    @Override
    public void playAudio() {
        sp.play(soundId, 1, 1, 0, 0, 1);
    }

    @Override
    public void stopAudio() {
        sp.stop(soundId);
    }

    @Override
    public void destroy() {
        sp.release();
    }

    @Override
    public void setPlaybackFile(File f) {

    }
}
