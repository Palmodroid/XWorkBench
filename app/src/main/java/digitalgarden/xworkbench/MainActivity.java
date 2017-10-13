package digitalgarden.xworkbench;

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

    public void initialize()
        {
        tx1.setText("Frequency");
        tx2.setText("Title 2");
        tx3.setText("Title 3");
        tx4.setText("Title 4");
        tx5.setText("Title 5");
        }

    // boolean sound = false;

    Tone tone = null;

    public void onClick(View view)
        {
        try
            {
            /* ********************************************************************/
            /* Ide jön a kipróbálandó programrész.
             * Az ed1-ed5 mezőket (és a tx1-tx5 címkéket is) szabadon használhatjuk
             */

            int freq = Integer.parseInt( ed1.getText().toString() );
            if ( freq == 0 )
                {
                if (tone != null )
                    SoundMaker.stop( tone );
                }
            tone = SoundMaker.start( freq );
/*            if ( !sound )
                {
                int freq = Integer.parseInt( ed1.getText().toString() );
                SoundMaker.start( freq );
                sound = true;
                }
            else
                {
                SoundMaker.stop();
                sound = false;
                }
*/
            /* ********************************************************************/
            }
        catch (Exception e)
            {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    protected void onPause()
        {
        super.onPause();
        //SoundMaker.stop();
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
