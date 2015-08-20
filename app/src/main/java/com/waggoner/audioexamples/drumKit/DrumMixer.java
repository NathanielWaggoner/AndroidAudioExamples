package com.waggoner.audioexamples.drumKit;

import android.content.Context;

import com.waggoner.audioexamples.R;
import com.waggoner.audioexamples.core.Channel;
import com.waggoner.audioexamples.core.Mixer;
import com.waggoner.audioexamples.outputs.MediaPlayerSource;
import com.waggoner.audioexamples.outputs.SoundPoolSource;
import com.waggoner.audioexamples.outputs.StaticAudioTrackSource;
import com.waggoner.audioexamples.outputs.SuperPoweredSource;

/**
 * The first attempt at one of these component based instruements.
 * Created by nathanielwaggoner on 8/6/15.
 */
public class DrumMixer implements Mixer {

    public static final int DEFAULT_NUM_CHANNELS = 4;
    /**
     * TODO:
     * <p/>
     * Move UI creation into helper class for this....
     */
    public static String TAG = DrumMixer.class.getName();
    Channel[] channels;

    public DrumMixer(int numChannel) {
        channels = new Channel[numChannel];
    }

    public void generateDefaultChannels(Context context) {
        setChannel(0, new Drum(new MediaPlayerSource(context, R.raw.claves), null));
        setChannel(1, new Drum(new SoundPoolSource(context, R.raw.claves), null));
        setChannel(2, new Drum(new StaticAudioTrackSource(context, R.raw.claves), null));
        setChannel(3, new Drum(new SuperPoweredSource(context, R.raw.claves), null));

    }

    @Override
    public int getNumChannels() {
        return channels.length;
    }

    @Override
    public Channel getChannel(int channel) {
        Channel ret = null;
        if (itFits(channel)) {
            ret = channels[channel];
        }
        return ret;
    }

    @Override
    public void setChannel(int channel_number, Channel channel) {
        if (itFits(channel_number)) {
            if (channels[channel_number] != null) {
                channels[channel_number].destroy();
            }
            channels[channel_number] = channel;
        }
    }

    boolean itFits(int channel_number) {
        return channels!=null && channel_number < channels.length && channel_number > -1;

    }

}
