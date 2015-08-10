package waggoner.com.audioexamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import waggoner.com.audioexamples.drumKit.Drum;
import waggoner.com.audioexamples.drumKit.DrumMixer;
import waggoner.com.audioexamples.sources.MediaPlayerSource;
import waggoner.com.audioexamples.sources.SoundPoolSource;
import waggoner.com.audioexamples.sources.StaticAudioTrackSource;

public class MainActivity extends AppCompatActivity {

    DrumMixer firstMixer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstMixer = new DrumMixer(this,3);
        firstMixer.setChannel(0, new Drum(new MediaPlayerSource(this, R.raw.claves), null));
        firstMixer.setChannel(1, new Drum(new SoundPoolSource(this, R.raw.claves), null));
        firstMixer.setChannel(2, new Drum(new StaticAudioTrackSource(this, R.raw.claves), null));

//        firstMixer.generateDefaultChannels(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        Button btn = new Button(this);
        btn.setText("Media Player Button");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstMixer.getChannel(0).play();

            }
        });
        Button second = new Button(this);
        second.setText("Sound Pool Button");
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstMixer.getChannel(1).play();

            }
        });
        Button third = new Button(this);
        third.setText("AudioTrack Button");
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstMixer.getChannel(2).play();
            }
        });
        layout.addView(btn);
        layout.addView(second);
        layout.addView(third);

        setContentView(layout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
