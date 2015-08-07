package waggoner.com.audioexamples.drumKit;

import android.media.MediaPlayer;

import waggoner.com.audioexamples.core.AudioSource;
import waggoner.com.audioexamples.core.Channel;
import waggoner.com.audioexamples.core.Effect;
import waggoner.com.audioexamples.sources.MediaPlayerSource;

/**
 * Created by nathanielwaggoner on 8/6/15.
 */
public class Drum implements Channel {
    AudioSource mAudioSource;
    MediaPlayer.OnCompletionListener mOnCompletionListener;
    @Override
    public void play() {
        mAudioSource.playAudio();


    }

    Effect mEffect;

    public Drum(AudioSource source, Effect effect){
        setAudioSource(source);
        setEffect(effect);
    };
    @Override
    public void setAudioSource(AudioSource audioSource) {
        mAudioSource = audioSource;
        if(mAudioSource instanceof MediaPlayerSource) {
            mOnCompletionListener = new MediaPlayerOnCompletionListener();
            ((MediaPlayerSource) mAudioSource).setOnCompletionListener(mOnCompletionListener);
        }
    }

    @Override
    public void processBuffer(short[] buffer) {

    }

    @Override
    public AudioSource getAudioSource() {
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
}
