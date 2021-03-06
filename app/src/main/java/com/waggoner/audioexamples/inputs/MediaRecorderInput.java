package com.waggoner.audioexamples.inputs;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

import com.waggoner.audioexamples.core.InputSource;
import com.waggoner.audioexamples.util.FileUtil;

/**
 * Created by nathanielwaggoner on 8/27/15.
 */
public class MediaRecorderInput implements InputSource{

    // we have to manually track this in MediaRecrod
    private boolean isRecording = false;
    // file formats are kind of limited
    public static String THREE_GP_FILE_DIRECTORY ="3gp";
    // the big heavy API for recording
    MediaRecorder mRecorder;
    Context ctx;
//
    public MediaRecorderInput(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean isRecording() {
        return isRecording;
    }

    @Override
    public boolean startInput() {
        isRecording = true;
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mRecorder.setOutputFile(FileUtil.generateRecordingsFileName(ctx, MediaRecorderInput.THREE_GP_FILE_DIRECTORY));
        try {
            mRecorder.prepare();
        } catch (Exception e) {
            Log.e("XappTest", Log.getStackTraceString(e));
        }

        mRecorder.start();
        return true;
    }

    @Override
    public boolean stopInput() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        isRecording = false;
        return true;
    }

    @Override
    public boolean isPlayingRecording() {
        return false;
    }

    @Override
    public void playRecording() {

    }
}
