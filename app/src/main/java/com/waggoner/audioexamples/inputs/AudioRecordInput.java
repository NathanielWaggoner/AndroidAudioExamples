package com.waggoner.audioexamples.inputs;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.waggoner.audioexamples.core.InputSource;
import com.waggoner.audioexamples.inputs.inputCallbacks.FileWriterCallback;
import com.waggoner.audioexamples.inputs.inputCallbacks.InputBufferCallback;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by nathanielwaggoner on 8/18/15.
 */
public class AudioRecordInput implements InputSource {

    public static final String TAG = AudioRecordInput.class.getName();
    public static final int OUTPUT_TYPE_FILE=0;
    public static final int OUTPUT_TYPE_CALLBACK=1;
    /**
     * 44100 is the only sample rate garunteed to be available on all devices.
     * http://developer.android.com/reference/android/media/AudioRecord.html#AudioRecord(int,%20int,%20int,%20int,%20int)
     *
     * I believe this may actuall be kinda of... falsish..
     */
    private static final int INPUT_SOURCE = MediaRecorder.AudioSource.MIC;
    public static final int SAMPLE_RATE = 16000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    static int BufferElements2Rec = 1024;
    static int BytesPerElement = 2;
    AudioRecord record;


    private LinkedBlockingQueue<short[]> audioPassBufferList;

    /**
     * We never want to do processing on the same thread that we do polling.  Device performance at this level is very, very unpredictable so
     * anything that blocks polling of the input stream is unacceptable.
     */
    Thread recordingThread;

    Thread processingThread;

    AtomicBoolean isRecording;
    int mOutputType;


    InputBufferCallback mInputBufferCallback;

    public static String generateDefaultFileName() {
        return "stupid recording is stupid";
    }

    @Override
    public boolean isPlayingRecording() {
        return false;
    }

    @Override
    public void playRecording() {
        /**
         * TODO:  This doesn't belong here...
         */
    }

    /**
     * Helper constructor for a write to file behavior
     * @param fileName
     */
    public AudioRecordInput(String fileName) {
        this(OUTPUT_TYPE_FILE, new FileWriterCallback(fileName));
    }

    /**
     * Helper constructor for a write to custom calblack behavior
     * @param callback
     */
    public AudioRecordInput(InputBufferCallback callback) {
        this(OUTPUT_TYPE_CALLBACK, callback);
    }

    /**
     * Helper constructor for configuring output type and callbacks
     * @param outputType
     * @param callback
     */
    public AudioRecordInput(int outputType, InputBufferCallback callback) {
        this(INPUT_SOURCE, SAMPLE_RATE, RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING, BufferElements2Rec * BytesPerElement, outputType, callback);
    }

    /**
     * Fully configurable constructor
     * @param audioSource
     * @param sampleRateInHz
     * @param channelConfig
     * @param audioFormat
     * @param bufferSizeInBytes
     * @param outputType
     * @param callback
     */
    public AudioRecordInput(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat,
                            int bufferSizeInBytes,int outputType, InputBufferCallback callback) {
        mOutputType = outputType;
        mInputBufferCallback = callback;
        isRecording = new AtomicBoolean(false);
        audioPassBufferList = new LinkedBlockingQueue<short[]>();
        record = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat,bufferSizeInBytes);

        recordingThread = new Thread(new Runnable() {
            public void run(){
                writeAudio();
            }
        });
        processingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                processAudio();
            }
        });
    }
    @Override
    public boolean startInput() {
        mInputBufferCallback.prepare();
        if(mInputBufferCallback == null) {
            throw new RuntimeException("No InputBufferCallback set for audio handling");
        }
        isRecording.set(true);
        record.startRecording();
        recordingThread.start();
        processingThread.start();
        return true;
    }


    private void writeAudio() {
        short sData[] = new short[BufferElements2Rec];
        while (audioPassBufferList != null && isRecording.get()) {
            // gets the voice output from microphone to byte format
            record.read(sData, 0, BufferElements2Rec);
            audioPassBufferList.offer(sData);
        }
    }
    private void processAudio() {
        short[] audioBuffer;
        int pollingTimeout = 500;
        try {
            while (audioPassBufferList != null && (audioBuffer = audioPassBufferList.poll(pollingTimeout, TimeUnit.MILLISECONDS)) != null) {
                mInputBufferCallback.handleBuffer(audioBuffer);
            }
        } catch(InterruptedException ie) {
            Log.e(TAG,Log.getStackTraceString(ie));
        }
    }

    @Override
    public boolean stopInput() {
        if (null != record) {
            isRecording.set(false);
            record.stop();
            record.release();
            record = null;
            recordingThread = null;
            return true;
        } else {
            return false;
        }
    }
}
