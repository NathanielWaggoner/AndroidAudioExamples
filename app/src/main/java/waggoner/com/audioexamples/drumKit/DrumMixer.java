package waggoner.com.audioexamples.drumKit;

import android.content.Context;

import waggoner.com.audioexamples.R;
import waggoner.com.audioexamples.core.Channel;
import waggoner.com.audioexamples.core.MediaPlayerSource;
import waggoner.com.audioexamples.core.Mixer;

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
            channels[i] = new Drum(new MediaPlayerSource(context, R.raw.cowbell),null);
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
