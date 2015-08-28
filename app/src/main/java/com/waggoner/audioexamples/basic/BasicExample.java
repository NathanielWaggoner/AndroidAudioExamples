package com.waggoner.audioexamples.basic;

import android.content.Context;
import android.view.View;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public class BasicExample {

    private BasicMixer drumMixer;
    private BasicUi drumKitUi;


    public BasicExample(Context context) {

        /**
         * Set up our drum mixer which handles the sounds
         */
        drumMixer = new BasicMixer(BasicMixer.DEFAULT_NUM_CHANNELS,2);
        drumMixer.generateDefaultChannels(context);

        /**
         * set up our drum kits ui so that it is pretty
         */
        drumKitUi = new BasicUi();
        drumKitUi.setMixer(drumMixer);
    }

    public View createUi(Context ctx) {
        return drumKitUi.createUI(ctx);
    }
}
