package com.waggoner.audioexamples.sources;

import android.content.Context;

import com.waggoner.audioexamples.core.AudioSource;

/**
 * Created by nathanielwaggoner on 8/14/15.
 */
public class SuperPoweredSource implements AudioSource {

    public SuperPoweredSource(Context ctx, int resource) {

    }
    static {
        System.loadLibrary("SuperpoweredExample");
        // load relevant source library here.
    }

    @Override
    public void playAudio() {

    }

    @Override
    public void stopAudio() {

    }

    @Override
    public void destroy() {

    }
}
