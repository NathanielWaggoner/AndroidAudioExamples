package com.waggoner.audioexamples.basic;

import com.waggoner.audioexamples.inputs.inputCallbacks.InputBufferCallback;
import com.waggoner.audioexamples.outputs.AudioTrackSource;

/**
 * Created by nathanielwaggoner on 8/28/15.
 */
public class AudioTrackBufferCallback implements InputBufferCallback {

    AudioTrackSource outputSource;


    @Override
    public void prepare() {
        outputSource = new AudioTrackSource();
        outputSource.playAudioFunnyStyle();
    }

    @Override
    public void handleBuffer(short[] buffer) {
        outputSource.handleShortArray(buffer);
    }

    @Override
    public void finish() {

    }
}
