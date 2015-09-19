package com.waggoner.audioexamples.synth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.waggoner.audioexamples.R;
import com.waggoner.audioexamples.ui.SimpleUi;

/**
 * Created by nathanielwaggoner on 9/18/15.
 */
public class SynthUi implements SimpleUi {
    Context ctx;
    SamplePlayer sp;
    @Override
    public View createUI(Context ctx) {
        this.ctx = ctx;
        sp = new SamplePlayer();
        View layout = LayoutInflater.from(ctx).inflate(R.layout.synth_layout,null);
        Button sin = (Button) layout.findViewById(R.id.sin);
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setSin();
            }
        });
        Button saw = (Button) layout.findViewById(R.id.saw);
        saw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setSaw();
            }
        });

        Button squ = (Button) layout.findViewById(R.id.squ);
        squ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setSqu();
            }
        });
        final TextView frequencyDisplay = (TextView) layout.findViewById(R.id.frequency_display);
        frequencyDisplay.setText("0");
        SeekBar seekBar = (SeekBar)layout.findViewById(R.id.frequency);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sp.setFrequency(progress);
                frequencyDisplay.setText(""+progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final Button start = (Button) layout.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.playing) {
                    sp.stop();
                    start.setText("Start");
                } else {
                    sp.play();
                    start.setText("Stop");
                }
            }
        });
        return layout;
    }

    @Override
    public void destoryUi() {

    }
}
