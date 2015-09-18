package com.waggoner.audioexamples.basic;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.waggoner.audioexamples.inputs.AudioRecordInput;
import com.waggoner.audioexamples.inputs.MediaRecorderInput;
import com.waggoner.audioexamples.outputs.AudioTrackSource;
import com.waggoner.audioexamples.outputs.MediaPlayerSource;
import com.waggoner.audioexamples.synth.SinSynth;
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
    boolean mediaRecording = false;
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
        startStopRecord.setText("Start AudioTrack Recording");

        final Button playRecord = new Button(ctx);
        playRecord.setText("Play AduioTrack Record");


        final Button playSinTone = new Button(ctx);
        playSinTone.setText("Play Sin Tone");
        playSinTone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SinSynth synth = new SinSynth(new AudioTrackSource());
                synth.startGeneratingTone(440);
            }
        });
        startStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recording = !recording;
                if (recording) {
                    mixer.inputs[0].startInput();
                    startStopRecord.setText("Stop AudioTrack Recording");
                } else {
                    mixer.inputs[0].stopInput();
                    startStopRecord.setText("Start AudioTrack Recording");
                    ((AudioTrackSource) mixer.channels[4].getAudioSource()).setPlaybackFile(getFileToPlay(BasicUi.this.ctx, null));
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


        final Button startStopMediaRecord = new Button(ctx);
        startStopMediaRecord.setText("Start MediaRecord Recording");
        startStopMediaRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecording = !mediaRecording;
                if (mediaRecording) {
                    mixer.inputs[1].startInput();
                    startStopMediaRecord.setText("Stop MediaRecord Recording");
                } else {
                    mixer.inputs[1].stopInput();
                    startStopMediaRecord.setText("Start MediaRecord Recording");
                    ((MediaPlayerSource) mixer.channels[5].getAudioSource()).setPlaybackFile(getFileToPlay(BasicUi.this.ctx, MediaRecorderInput.THREE_GP_FILE_DIRECTORY));
                }

            }
        });

        final Button playMediaRecord = new Button(ctx);
        playMediaRecord.setText("Play MediaRecord Record");
        playMediaRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mixer.channels[5].play();
                playRecord.setText("Media Recording Playing");
            }
        });

        final Button roundTrip = new Button(ctx);
        roundTrip.setText("Start Round Trip");
        roundTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mixer.inputs[2].isRecording()) {
                    roundTrip.setText("Start Round Trip");

                    mixer.inputs[2].stopInput();
                    mixer.inputs[2] = new AudioRecordInput(new AudioTrackBufferCallback());
                } else {
                    roundTrip.setText("Stop Round Trip");

                    ((AudioRecordInput) mixer.inputs[2]).mInputBufferCallback.prepare();
                    mixer.inputs[2].startInput();
                }
            }
        });

        layout.addView(btn);
        layout.addView(second);
        layout.addView(third);
        layout.addView(fourth);
        layout.addView(startStopRecord);
        layout.addView(playRecord);
        layout.addView(startStopMediaRecord);
        layout.addView(playMediaRecord);
        layout.addView(roundTrip);
        layout.addView(playSinTone);
        return layout;

    }
    public static File getFileToPlay(Context ctx, String dir) {
        File newDir = FileUtil.getRecordingsFile(ctx);
        File toPlay = null;
        if(dir!= null) {
            File mDir = new File(newDir.getAbsolutePath() + File.separator + dir + File.separator);
            Log.e("XapPtest","Looking in file: "+mDir.getAbsolutePath());
            toPlay = getFileToPlay(mDir);
        } else {
            toPlay = getFileToPlay(newDir);
        }
        return toPlay;
    }

    public static File getFileToPlay(File dir) {
        String[] files = dir.list();
        String file = null;
        if (files.length > 0) {
            for (int i = files.length-1; i>0; i--) {
                File f = new File(files[i]);
                Log.e("XapPtest", "Looking at file: " + f.getAbsolutePath());
                if (!f.isDirectory()) {
                    file = files[i];
                    break;
                }
            }
            Log.e("Xapptest", Arrays.toString(files));
        }
        if(file== null) {
            return null;
        }
        Log.e("XapPTest","returnign file: "+dir.getAbsolutePath() + File.separator + file);
        return new File(dir.getAbsolutePath() + File.separator + file);
    }


    @Override
    public void destoryUi() {

    }
}
