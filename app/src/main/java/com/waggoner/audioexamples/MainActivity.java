package com.waggoner.audioexamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.waggoner.audioexamples.drumKit.DrumKit;

public class MainActivity extends AppCompatActivity {

    DrumKit drumKit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drumKit = new DrumKit(this);
        setContentView(drumKit.createUi(this));
    }
}
