package com.waggoner.audioexamples.basic;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.waggoner.audioexamples.outputs.AudioTrackSource;
import com.waggoner.audioexamples.ui.SimpleUi;
import com.waggoner.audioexamples.util.FileUtil;

import java.io.File;
import java.util.Arrays;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public class BasicUi implements SimpleUi {


    Context ctx;
    boolean recording = false;
    BasicMixer mixer;
    ViewGroup rootView;

    public void setMixer(BasicMixer mixer) {
        this.mixer = mixer;
    }

    @Override
    public View createUI(Context ctx) {
        if (mixer == null) {
            throw new RuntimeException("No mixer curretly set, cannot generate UI");
        }
        this.ctx = ctx;
        LinearLayout layout = new LinearLayout(ctx);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        Button btn = new Button(ctx);
        btn.setText("Media Player Button");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mixer.getChannel(0).play();

            }
        });
        Button second = new Button(ctx);
        second.setText("Sound Pool Button");
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mixer.getChannel(1).play();

            }
        });
        Button third = new Button(ctx);
        third.setText("AudioTrack Button");
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mixer.getChannel(2).play();
            }
        });
        Button fourth = new Button(ctx);
        fourth.setText("SuperPowered Button");
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mixer.getChannel(3).play();
            }
        });

        final Button startStopRecord = new Button(ctx);
        startStopRecord.setText("Start Recording");

        final Button playRecord = new Button(ctx);
        playRecord.setText("Play Record");


        startStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recording = !recording;
                if (recording) {
                    mixer.inputs[0].startInput();
                    startStopRecord.setText("Stop Recording");
                } else {
                    mixer.inputs[0].stopInput();
                    startStopRecord.setText("Start Recording");
                    ((AudioTrackSource) mixer.channels[4].getAudioSource()).setPlaybackFile(getFileToPlay(BasicUi.this.ctx));
                }
            }
        });

        playRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mixer.channels[4].play();
                playRecord.setText("Recording Playing");
            }
        });

        layout.addView(btn);
        layout.addView(second);
        layout.addView(third);
        layout.addView(fourth);
        layout.addView(startStopRecord);
        layout.addView(playRecord);

        return layout;

    }

    public static File getFileToPlay(Context ctx) {
        File dir = FileUtil.getRecordingsFile(ctx);
        String[] files = dir.list();
        String file = null;
        if (files.length > 0) {
            Log.e("Xapptest", Arrays.toString(files));
            file = files[files.length - 1];
        }
        return new File(dir.getAbsolutePath()+File.separator+file);
    }

    @Override
    public void destoryUi() {

    }
}
