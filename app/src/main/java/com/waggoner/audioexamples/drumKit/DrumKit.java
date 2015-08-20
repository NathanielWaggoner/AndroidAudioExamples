package com.waggoner.audioexamples.drumKit;

import android.content.Context;
import android.view.View;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public class DrumKit {

    private DrumMixer drumMixer;
    private DrumKitUi drumKitUi;


    public DrumKit(Context context) {

        /**
         * Set up our drum mixer which handles the sounds
         */
        drumMixer = new DrumMixer(DrumMixer.DEFAULT_NUM_CHANNELS);
        drumMixer.generateDefaultChannels(context);

        /**
         * set up our drum kits ui so that it is pretty
         */
        drumKitUi = new DrumKitUi();
        drumKitUi.setMixer(drumMixer);
    }

    public View createUi(Context ctx) {
        return drumKitUi.createUI(ctx);
    }
}
