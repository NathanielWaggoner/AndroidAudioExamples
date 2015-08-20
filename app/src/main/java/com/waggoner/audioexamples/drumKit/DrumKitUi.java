package com.waggoner.audioexamples.drumKit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.waggoner.audioexamples.ui.BasicUi;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public class DrumKitUi implements BasicUi{


    DrumMixer mixer;
    ViewGroup rootView;
    public void setMixer(DrumMixer mixer){
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


        layout.addView(btn);
        layout.addView(second);
        layout.addView(third);
        layout.addView(fourth);

        return layout;

    }

    @Override
    public void destoryUi() {

    }
}
