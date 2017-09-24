package digitalgarden.xworkbench;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
    {
    @SuppressWarnings("unused")
    private EditText ed1;
    @SuppressWarnings("unused")
    private TextView tx1;
    @SuppressWarnings("unused")
    private EditText ed2;
    @SuppressWarnings("unused")
    private TextView tx2;
    @SuppressWarnings("unused")
    private EditText ed3;
    @SuppressWarnings("unused")
    private TextView tx3;
    @SuppressWarnings("unused")
    private EditText ed4;
    @SuppressWarnings("unused")
    private TextView tx4;
    @SuppressWarnings("unused")
    private EditText ed5;
    @SuppressWarnings("unused")
    private TextView tx5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = (EditText) findViewById(R.id.ed1);
        tx1 =(TextView) findViewById(R.id.tx1);
        ed2 = (EditText) findViewById(R.id.ed2);
        tx2 =(TextView) findViewById(R.id.tx2);
        ed3 = (EditText) findViewById(R.id.ed3);
        tx3 =(TextView) findViewById(R.id.tx3);
        ed4 = (EditText) findViewById(R.id.ed4);
        tx4 =(TextView) findViewById(R.id.tx4);
        ed5 = (EditText) findViewById(R.id.ed5);
        tx5 =(TextView) findViewById(R.id.tx5);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Vector asset
        // https://stackoverflow.com/questions/36362080/android-fab-plus-sign-not-present-on-android-drawable
        // https://stackoverflow.com/questions/41407811/android-vectordrawables-usesupportlibrary-true-is-stopping-app
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener( this );

        initialize();
        }

    /*
    private final int duration = 30; // seconds
    private final int sampleRate = 8000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private final double freqOfTone = 440; // hz
    private final byte generatedSnd[] = new byte[2 * numSamples];
    Handler handler = new Handler();
    private AudioTrack audioTrack;
    private boolean play = false;
    */

    public void initialize()
        {
        tx1.setText("Title 1");
        tx2.setText("Title 2");
        tx3.setText("Title 3");
        tx4.setText("Title 4");
        tx5.setText("Title 5");

        /*audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                8000, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples,
                AudioTrack.MODE_STREAM);*/
        }

    @Override
    protected void onResume()
        {
        super.onResume();

        /*
        // Use a new tread as this can take a while
        Thread thread = new Thread(new Runnable()
            {
            public void run()
                {
                genTone();
                handler.post(new Runnable()
                    {
                    public void run()
                        {
                        playSound();
                        }
                    });
                }
            });
        thread.start();
        */
        }

    /*
    void genTone()
        {
        // fill out the array
        while(play)
            {
            for (int i = 0; i < numSamples; ++i)
                {
                //  float angular_frequency =
                sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
                }
            int idx = 0;

            // convert to 16 bit pcm sound array
            // assumes the sample buffer is normalised.
            for (double dVal : sample)
                {
                short val = (short) (dVal * 32767);
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
                }
            audioTrack.write(generatedSnd, 0, numSamples);
            }
        }

    void playSound()
        {
        play = true;
        audioTrack.play();
        }

    public void playTone(double freqOfTone, double duration)
        {
        // double duration = 1000;                // seconds
        // double freqOfTone = 1000;           // hz
        int sampleRate = 8000;              // a number

        double dnumSamples = duration * sampleRate;
        dnumSamples = Math.ceil(dnumSamples);
        int numSamples = (int) dnumSamples;
        double sample[] = new double[numSamples];
        byte generatedSnd[] = new byte[2 * numSamples];

        for (int i = 0; i < numSamples; ++i)
            {      // Fill the sample array
            sample[i] = Math.sin(freqOfTone * 2 * Math.PI * i / (sampleRate));
            }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalized.
        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        int i = 0 ;

        int ramp = numSamples / 20 ;                                    // Amplitude ramp as a percent of sample count

        for (i = 0; i< ramp; ++i)
            {                                     // Ramp amplitude up (to avoid clicks)
            double dVal = sample[i];
            // Ramp up to maximum
            final short val = (short) ((dVal * 32767 * i/ramp));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }

        for (i = i; i< numSamples - ramp; ++i)
            {                        // Max amplitude for most of the samples
            double dVal = sample[i];
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }

        for (i = i; i< numSamples; ++i)
            {                               // Ramp amplitude down
            double dVal = sample[i];
            // Ramp down to zero
            final short val = (short) ((dVal * 32767 * (numSamples-i)/ramp ));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }

        AudioTrack audioTrack = null;                                   // Get audio track
        try
            {
            int bufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
            sampleRate, AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT, bufferSize,
            AudioTrack.MODE_STREAM);
            audioTrack.play();                                          // Play the track
            audioTrack.write(generatedSnd, 0, generatedSnd.length);     // Load the track
            }
        catch (Exception e){ }
        if (audioTrack != null) audioTrack.release();           // Track play done. Release track.
        }
        */

    public void onClick(View view)
        {
        try
            {
            /* ********************************************************************/
            /* Ide jön a kipróbálandó programrész.
             * Az ed1-ed5 mezőket (és a tx1-tx5 címkéket is) szabadon használhatjuk
             */

            AudioTrack audioTrack;
            int sampleRate = 8000;
            int bufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, bufferSize,
                    AudioTrack.MODE_STREAM);
            audioTrack.play();                                          // Play the track

            short[] value = new short[5];
            int i;
            for (i = 0; i < 32000; i++)
                {                        // Max amplitude for most of the samples
                double dVal = Math.sin(440 * 2 * Math.PI * i / (sampleRate));
                // scale to maximum amplitude
                value[0] = (short) ((dVal * 32767));
                // in 16 bit wav PCM, first byte is the low order byte
                audioTrack.write(value, 0, 1);     // Load the track
                }

            int ramp = 4000;
            for (int r = ramp; r >= 0 ; i++, r--)
                {                               // Ramp amplitude down
                double dVal = Math.sin(440 * 2 * Math.PI * i / (sampleRate));
                // Ramp down to zero
                value[0] = (short) ((dVal * 32767 * r/ramp ));
                // in 16 bit wav PCM, first byte is the low order byte
                audioTrack.write(value, 0, 1);     // Load the track
                }

            audioTrack.stop();
            audioTrack.release();

            /* ********************************************************************/
            }
        catch (Exception e)
            {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
        {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
        {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            {
            return true;
            }

        return super.onOptionsItemSelected(item);
        }
    }
