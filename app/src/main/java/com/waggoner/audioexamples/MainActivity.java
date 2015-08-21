package com.waggoner.audioexamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.waggoner.audioexamples.basic.BasicExample;

public class MainActivity extends AppCompatActivity {

    BasicExample drumKit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drumKit = new BasicExample(this);
        setContentView(drumKit.createUi(this));
    }
}
