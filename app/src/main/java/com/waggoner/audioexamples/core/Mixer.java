package com.waggoner.audioexamples.core;

/**
 * Created by nathanielwaggoner on 8/6/15.
 */
public interface Mixer {

    /**
     * Get the number of channels this mixer is configured for
     * @return
     */
    int getNumChannels();

    /**
     * Get the channel at this location in the mixer
     * @param channel
     * @return
     */
    Channel getChannel(int channel);

    /**
     * Set the Channel at the ChannelNumber location on the mixer
     * @param channel_number
     * @param channel
     */
    void setChannel(int channel_number, Channel channel);


}
