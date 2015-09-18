package com.waggoner.audioexamples.synth;

import android.util.Log;

import com.waggoner.audioexamples.outputs.AudioTrackSource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by nathanielwaggoner on 9/18/15.
 */
public class SinSynth {

    AudioTrackSource outputSource;
    AtomicBoolean playing;
    int frequency;
    public SinSynth(AudioTrackSource outputSource) {
        playing = new AtomicBoolean(false);
        this.outputSource = outputSource;
    }
    public void startGeneratingTone(int frequency) {
        outputSource.playAudioFromExternalWriter();
        playing.set(true);
        Log.e("XapPTest", "Should be starting to generate");

        startGenerationThread(frequency);
    }

    public void stopGeneratingTone() {
        playing.set(false);
        outputSource.stopAudio();
    }

    private void startGenerationThread(int freq) {
        this.frequency = freq;
        Thread generatorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                float increment = (float)(2*Math.PI) * frequency / 44100; // angular increment for each sample
                float angle = 0;
                float samples[] = new float[1024];
                short[] buffer = new short[1024];
                while (playing.get()) {
                    for (int i = 0; i < samples.length; i++) {
                        samples[i] = (float) Math.sin(angle);   //the part that makes this a sine wave....
                        buffer[i] = (short) (samples[i] * Short.MAX_VALUE);
                        angle += increment;
                    }
                    outputSource.handleShortArray(buffer);
                }
            }
        });
        generatorThread.start();
    }


}
