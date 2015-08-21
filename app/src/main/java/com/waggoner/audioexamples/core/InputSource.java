package com.waggoner.audioexamples.core;

/**
 * Created by nathanielwaggoner on 8/18/15.
 */
public interface InputSource {

    /**
     * returns true if successfully started input
     * @return
     */
    boolean startInput();

    /**
     * returns true is succesfully stopped input
     * @return
     */
    boolean stopInput();

    boolean isPlayingRecording();

    void playRecording();
}
