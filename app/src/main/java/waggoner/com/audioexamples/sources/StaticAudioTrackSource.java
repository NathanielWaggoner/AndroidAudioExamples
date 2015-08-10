package waggoner.com.audioexamples.sources;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaExtractor;

import java.io.IOException;
import java.io.InputStream;

import waggoner.com.audioexamples.core.AudioSource;

/**
 * Created by nathanielwaggoner on 8/7/15.
 */
public class StaticAudioTrackSource implements AudioSource {

    public static String TAG = StaticAudioTrackSource.class.getName();
    AudioTrack mAudioTrack;
    int mPeriod;
    int mMarkerPosition;
    int minBufferSize;
    MediaExtractor mediaExtractor = new MediaExtractor();

    /**
     * Audio Track from RAW resources
     *
     * @param context
     * @param resource
     */
    public StaticAudioTrackSource(Context context, int resource) {
        minBufferSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        // in static mode we call wrtie first, then we can play later.
        // in streaming you do things a bit differently
        prepareStaticAudioTrack(context, resource);
    }

    private void prepareStaticAudioTrack(Context context, int resource) {
        try {

            InputStream inputStream = context.getResources().openRawResource(resource);
            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 24000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, minBufferSize, AudioTrack.MODE_STATIC);
            // in static mode we call wrtie first, then we can play later.
            int i = 0;
            int bufferSize = 512;
            byte[] buffer = new byte[bufferSize];
            int x = 0;
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
