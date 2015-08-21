package com.waggoner.audioexamples.core;

/**
 * Created by nathanielwaggoner on 8/6/15.
 */
public interface OuputSource {

    /**
     * TODO:
     *
     * Add seekTo
     */
    void playAudio();
    void stopAudio();
    void destroy();
}
