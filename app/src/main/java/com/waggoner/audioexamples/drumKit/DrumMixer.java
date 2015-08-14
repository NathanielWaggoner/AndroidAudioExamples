package com.waggoner.audioexamples.drumKit;

import android.content.Context;

import com.waggoner.audioexamples.R;
import com.waggoner.audioexamples.core.Channel;
import com.waggoner.audioexamples.sources.MediaPlayerSource;
import com.waggoner.audioexamples.core.Mixer;

/**
 * The first attempt at one of these component based instruements.
 * Created by nathanielwaggoner on 8/6/15.
 */
public class DrumMixer implements Mixer {
    public static String TAG = DrumMixer.class.getName();
    Channel[] channels;
    public DrumMixer(Context context, int numChannel) {
        channels = new Channel[numChannel];
    }

    public void generateDefaultChannels(Context context) {
        for (int i = 0; i < channels.length; i++) {
            // you do not really want to use media players this way.  They're not intended for theses kinds of short, repeated sounds
            channels[i] = new Drum(new MediaPlayerSource(context, R.raw.claves),null);
        }

    }

    @Override
    public int getNumChannels() {
        return channels.length;
    }

    @Override
    public Channel getChannel(int channel) {
        Channel ret = null;
        if(itFits(channel)) {
            ret =  channels[channel];
        }
        return ret;
    }

    @Override
    public void setChannel(int channel_number, Channel channel) {
        if(itFits(channel_number)) {
            if(channels[channel_number]!=null)  {
                channels[channel_number].destroy();
            }
            channels[channel_number]=channel;
        }
    }

    boolean itFits(int channel_number) {
        return channel_number <channels.length&& channel_number>-1;

    }



}
