package com.waggoner.audioexamples.basic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.waggoner.audioexamples.ui.SimpleUi;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public class BasicUi implements SimpleUi {


    boolean recording = false;
    BasicMixer mixer;
    ViewGroup rootView;
    public void setMixer(BasicMixer mixer){
         this.mixer = mixer;
    }
    @Override
    public View createUI(Context ctx) {
        if(mixer == null) {
            throw new RuntimeException("No mixer curretly set, cannot generate UI");
        }
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
                }
            }
        });

        playRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mixer.inputs[0].isPlayingRecording()) {
                    mixer.inputs[0].playRecording();
                    playRecord.setText("Recording Playing");
                } else {
                    mixer.inputs[0].stopInput();
                    playRecord.setText("Recording Not Playing");
                }
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

    @Override
    public void destoryUi() {

    }
}
