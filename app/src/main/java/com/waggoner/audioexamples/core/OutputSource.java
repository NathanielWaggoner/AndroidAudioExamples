package com.waggoner.audioexamples.core;

import java.io.File;

/**
 * Created by nathanielwaggoner on 8/6/15.
 */
public interface OutputSource {

    /**
     * TODO:
     *
     * Add seekTo
     */
    void playAudio();
    void stopAudio();
    void destroy();
    void setPlaybackFile(File f);
}
