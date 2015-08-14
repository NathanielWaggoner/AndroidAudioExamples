package com.waggoner.audioexamples.sources;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import com.waggoner.audioexamples.core.AudioSource;
import com.waggoner.audioexamples.core.WavInfo;

/**
 * Now things get complicated.
 * <p/>
 * We've got to make sure we're not using MP3's at this point, mostly because its a PITA to convert them around.
 * <p/>
 * So we'll stick with wavs.  So some
 * <p/>
 *
 * AudioTracks have a less favorible processor scheduling priority than SoundPool so you should prefer sound pool typically.
 *
 * Created by nathanielwaggoner on 8/7/15.
 */
public class StaticAudioTrackSource implements AudioSource {

    // dont use MP3's here, thats the big foot note.

    public static String TAG = StaticAudioTrackSource.class.getName();
    AudioTrack mAudioTrack;

    // file sample rate
    int sampleRate;
    // number of channels in the file
    int channelCount;
    // the channel configuration contstant to use.  Derived from channel count.
    int channelConfig;
    // long as returned fom MediaExtractor
    long duration;
    MediaExtractor mediaExtractor = new MediaExtractor();

    /**
     * Audio Track from RAW resources
     *
     * @param context
     * @param resource
     */
    public StaticAudioTrackSource(Context context, int resource) {

        // in static mode we call wrtie first, then we can play later.
        // in streaming you do things a bit differently
        getMediaData(context, resource);
        prepareStaticAudioTrack(context.getResources().openRawResource(resource));
    }

    public void getMediaData(Context context, int resource) {
        try {
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(resource);
            extractMediaData(afd);
            WavInfo.parseWave(context.getResources().openRawResource(resource));
            afd.close();
        } catch (IOException e) {
            Log.e("AudioTest", Log.getStackTraceString(e));
        }

    }

    private void extractMediaData(AssetFileDescriptor afd) {
        MediaExtractor mex = new MediaExtractor();
        try {
            mex.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());// the adresss location of the sound on sdcard.
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("AudioTest", Log.getStackTraceString(e));
        }

        MediaFormat mf = mex.getTrackFormat(0);
        sampleRate = mf.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        duration = mf.getLong(MediaFormat.KEY_DURATION);
        channelCount = mf.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        // getting this wrong can result in interesting audio characterists, like playing at half or double speed.
        if (channelCount == 1) {
            channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
        } else if (channelCount == 2) {
            channelConfig = AudioFormat.CHANNEL_CONFIGURATION_STEREO;
        } else {
            throw new RuntimeException("Loading Wav File with unsopported number of channels, num: "+channelCount);
        }

        Log.e("AudioTest", "sample rate: " + sampleRate + " channelCount: " + channelCount + " duration: " + duration);
    }


    private void prepareStaticAudioTrack(InputStream inputStream) {
        try {

            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, channelConfig,
                    AudioFormat.ENCODING_PCM_16BIT, WavInfo.dataLength, AudioTrack.MODE_STATIC);
            // in static mode we call wrtie first, then we can play later.
            int i = 0;
            int bufferSize = WavInfo.dataLength;
            byte[] buffer = new byte[bufferSize];
            int x = 0;
            inputStream.skip(WavInfo.dataOffset);
            while ((i = inputStream.read(buffer)) != -1) {
                mAudioTrack.write(buffer, 0, i);
            }
            inputStream.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void playAudio() {
        if(mAudioTrack.getPlaybackHeadPosition()!=0) {
            mAudioTrack.stop();
            mAudioTrack.setPlaybackHeadPosition(0);
        }
        mAudioTrack.play();
    }

    @Override
    public void stopAudio() {
        mAudioTrack.stop();
    }

    @Override
    public void destroy() {
        mAudioTrack.release();
    }

    // this is never called as far as I can tell:
    //https://code.google.com/p/android/issues/detail?id=2563#makechanges
    // currently assigned, but unfixed as of Nov 30, 2014
    public void setPlaybackPositionUpdateListener(AudioTrack.OnPlaybackPositionUpdateListener listener) {
        mAudioTrack.setPlaybackPositionUpdateListener(listener);
    }

    public void setNotificationMakerPosition(int markerInFrames) {
       int m =  mAudioTrack.setNotificationMarkerPosition(markerInFrames);
        Log.e("AudioTest","set marker to: "+markerInFrames+" result: "+m);
    }

}
