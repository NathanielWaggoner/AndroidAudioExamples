package waggoner.com.audioexamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import waggoner.com.audioexamples.drumKit.DrumMixer;

public class MainActivity extends AppCompatActivity {

    DrumMixer firstMixer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstMixer = new DrumMixer(this,1);
        firstMixer.generateDefaultChannels(this);
        Button btn = new Button(this);
        btn.setText("Press me for noise");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstMixer.getChannel(0).play();

            }
        });
        setContentView(btn);

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
