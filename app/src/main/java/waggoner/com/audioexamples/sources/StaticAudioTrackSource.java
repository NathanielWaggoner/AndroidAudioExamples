package waggoner.com.audioexamples.sources;

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

import waggoner.com.audioexamples.core.AudioSource;
import waggoner.com.audioexamples.core.WavInfo;

/**
 * Now things get complicated.
 *
 * We've got to make sure we're not using MP3's at this point, mostly because its a PITA to convert them around.
 *
 * So we'll stick with wavs.  So some
 *
 * Created by nathanielwaggoner on 8/7/15.
 */
public class StaticAudioTrackSource implements AudioSource {

    // dont use MP3's here, thats the big foot note.

    public static String TAG = StaticAudioTrackSource.class.getName();
    AudioTrack mAudioTrack;
    int minBufferSize;
    // file bit rate
    int bitRate;
    // file sample rate
    int sampleRate;
    // number of channels in the file
    int channelCount;
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
        } catch (IOException e){
            Log.e("XapPTest",Log.getStackTraceString(e));
        }

    }

    private void extractMediaData(AssetFileDescriptor afd) {
        MediaExtractor mex = new MediaExtractor();
        try {
            mex.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());// the adresss location of the sound on sdcard.
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("XapPTest",Log.getStackTraceString(e));
        }

        MediaFormat mf = mex.getTrackFormat(0);
        sampleRate = mf.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        channelCount = mf.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        // check your constants for this stuff
        duration = mf.getLong(MediaFormat.KEY_DURATION);

//        minBufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
//                AudioFormat.ENCODING_PCM_16BIT);

        Log.e("XappTest", "bitrate: "+bitRate+ " sample rate: "+sampleRate+" channelCount: "+channelCount+ " duration: "+duration);
    }


    private void prepareStaticAudioTrack(InputStream inputStream) {
        try {

            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
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
        mAudioTrack.play();
    }

    @Override
    public void stopAudio() {
        mAudioTrack.stop();
        // two approaches here,
    }

    @Override
    public void destroy() {
        mAudioTrack.release();
    }

    public void setPlaybackPositionUpdateListener(AudioTrack.OnPlaybackPositionUpdateListener listener) {
        mAudioTrack.setPlaybackPositionUpdateListener(listener);
    }

    public void setNotificationMakerPosition(int markerInFrames) {
        mAudioTrack.setNotificationMarkerPosition(markerInFrames);
    }

}
