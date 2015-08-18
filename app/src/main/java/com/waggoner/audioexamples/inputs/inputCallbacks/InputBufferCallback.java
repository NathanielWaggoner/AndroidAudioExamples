package com.waggoner.audioexamples.inputs.inputCallbacks;

/**
 * Created by nathanielwaggoner on 8/18/15.
 */
public interface InputBufferCallback {


    /**
     * Do any preparations required for the callback to be ready for audio buffers
     */
    void prepare();
    /**
     * Accept the audio buffer which has been filled with sound data
     * @param buffer
     */
    void handleBuffer(short[] buffer);

    /**
     * Do any finishing touches before being cleaned up
     */
    void finish();

}
