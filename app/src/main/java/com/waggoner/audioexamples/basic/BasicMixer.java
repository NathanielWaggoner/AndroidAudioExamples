package com.waggoner.audioexamples.basic;

import android.content.Context;
import android.net.Uri;

import com.waggoner.audioexamples.R;
import com.waggoner.audioexamples.core.Channel;
import com.waggoner.audioexamples.core.InputSource;
import com.waggoner.audioexamples.core.Mixer;
import com.waggoner.audioexamples.inputs.AudioRecordInput;
import com.waggoner.audioexamples.inputs.MediaRecorderInput;
import com.waggoner.audioexamples.outputs.AudioTrackSource;
import com.waggoner.audioexamples.outputs.MediaPlayerSource;
import com.waggoner.audioexamples.outputs.SoundPoolSource;
import com.waggoner.audioexamples.outputs.StaticAudioTrackSource;
import com.waggoner.audioexamples.outputs.SuperPoweredSource;
import com.waggoner.audioexamples.util.FileUtil;

import java.io.File;

/**
 * The first attempt at one of these component based instruements.
 * Created by nathanielwaggoner on 8/6/15.
 */
public class BasicMixer implements Mixer {

    public static final int DEFAULT_NUM_CHANNELS = 6;
    public static final int DEFAULT_NUM_INPUTS = 3;
    /**
     * TODO:
     * <p/>
     * Move UI creation into helper class for this....
     */
    public static String TAG = BasicMixer.class.getName();
    Channel[] channels;
    InputSource[] inputs;

    public BasicMixer(int numChannel, int numInputs) {
        channels = new Channel[numChannel];
        inputs = new InputSource[numInputs];
    }

    public void generateDefaultChannels(Context context) {
        setChannel(0, new Drum(new MediaPlayerSource(context, R.raw.claves), null));
        setChannel(1, new Drum(new SoundPoolSource(context, R.raw.claves), null));
        setChannel(2, new Drum(new StaticAudioTrackSource(context, R.raw.claves), null));
        setChannel(3, new Drum(new SuperPoweredSource(context, R.raw.claves), null));

        AudioTrackSource src = new AudioTrackSource();
        src.setPlaybackFile(BasicUi.getFileToPlay(FileUtil.getRecordingsFile(context)));
        setChannel(4, new Drum(src, null));


        File f = BasicUi.getFileToPlay(context, MediaRecorderInput.THREE_GP_FILE_DIRECTORY);
        if(f!=null) {
            Uri uri = Uri.parse(f.getAbsolutePath());
            MediaPlayerSource mediaRecordPlaybackSrc = new MediaPlayerSource(context, uri);
            setChannel(5, new Drum(mediaRecordPlaybackSrc, null));
        } else  {

        }

        inputs[0] = new AudioRecordInput(FileUtil.generateRecordingsFileName(context,null));
        inputs[1] = new MediaRecorderInput(context);
        inputs[2] = new AudioRecordInput(new AudioTrackBufferCallback());
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
