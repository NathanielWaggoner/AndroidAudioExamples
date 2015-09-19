package com.waggoner.audioexamples.synth;

import com.waggoner.audioexamples.outputs.AudioTrackSource;

/**
 * Created by nathanielwaggoner on 9/18/15.
 */
public class SamplePlayer {

    AudioTrackSource mTrackSource;

    Ocsillator ocillator;
    public static final int SAMPLES_PER_BUFFER = AudioTrackSource.BUFFER_SIZE;
    public static final int SAMPLE_RATE = AudioTrackSource.SAMPLE_RATE;
    volatile boolean playing;
    public SamplePlayer(){
        mTrackSource = new AudioTrackSource();
        ocillator = new Ocsillator();
    }

    public void stop() {
        playing = false;
    }
    public void play() {
        playing = true;
        Thread playThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mTrackSource.playAudioFromExternalWriter();
                // this is probably going to be chunky at first...
                short[] buffer = new short[SAMPLES_PER_BUFFER];
                while(playing) {
                    int result = ocillator.getSamples(buffer);
                    mTrackSource.handleShortArray(buffer);
                }
            }
        });
        playThread.start();

    }
    public void setFrequency(int frequency) {
        ocillator.setFrequency(frequency);
    }
    public void setSin() {
        ocillator.setOscWaveshape(Ocsillator.WAVESHAPE.SIN);
    }
    public void setSaw() {
        ocillator.setOscWaveshape(Ocsillator.WAVESHAPE.SAW);
    }

    public void setSqu() {
        ocillator.setOscWaveshape(Ocsillator.WAVESHAPE.SQU);
    }

}
