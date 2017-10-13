package digitalgarden.xworkbench;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * http://stephendnicholas.com/posts/android-handlerthread
 *
 * Thread: runs ONE runnable
 * HandlerThread: runs tasks defined by Messages, one after another
 */

class SoundMaker implements Runnable
    {
    private final Object lock = new Object();

    private static SoundMaker soundMaker = null;

    // This class cannot be instantiated from outside
    private SoundMaker() {}

    private static final int SAMPLE_RATE = 16000;

    private static final int UP_LENGTH = 0; // SAMPLE_RATE / 50;
    private static final int DOWN_LENGTH = SAMPLE_RATE / 50;
    private static final int TAIL_LENGTH = SAMPLE_RATE * 3;

    private List<Tone> tones = new ArrayList<>();

    private boolean running = false;


    /**
     * start instantiates SoundMaker, if necessary
     * and adds tone to it
     * @param frequency frequency of the tone
     */
    static Tone start( int frequency )
        {
        Tone tone;

        if ( soundMaker != null )
            {
            synchronized ( soundMaker.lock )
                {
                if ( !soundMaker.running )
                    soundMaker = null;
                }
            }

        if ( soundMaker == null )
            {
            soundMaker = new SoundMaker();
            new Thread( soundMaker ).start();
            }

        tone = new Tone(SAMPLE_RATE, frequency, UP_LENGTH, DOWN_LENGTH);
        synchronized ( soundMaker.lock )
            {
            soundMaker.tones.add( tone );
            }

        return tone;
        }


    static void stop( Tone tone )
        {
        if ( soundMaker != null )
            {
            synchronized ( soundMaker.lock )
                {
                tone.finish();
                }
            }
        // else throw exception?
        }


    @Override
    public void run()
        {
        Log.d("MUSIC", "Thread start");

        AudioTrack audioTrack;
        int bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
        audioTrack.play();

        short[] toneArray = new short[1];

        int tick = 0;

        while ( true )
            {
            int tone = 0;
            int numberOfTones = 0;

            synchronized (lock)
                {
                for (Tone t : tones)
                    {
                    if (!t.isFinished())
                        {
                        tone += t.getAmplitude(tick);
                        numberOfTones++;
                        }
                    }
                }

            if (numberOfTones != 0)
                {
                toneArray[0] = (short) tone;//(tone / numberOfTones);
                audioTrack.write(toneArray, 0, 1);
                }

/*            // Sound can change only at ramp 0
            if ( rampUp == 0 )
                {

                if ( modeTemp == SOUND_DOWN_NEXT_STOP)
                    {
                    tail--;
                    if ( tail == 1 )
                        {
                        // There is already one round left
                        synchronized ( soundMaker.lock )
                            {
                            running = false;
                            }
                        }
                    else if ( tail == 0 )
                        {
                        break;
                        }
                    }

                else // turn off every sign of stop
                    {
                    tick = 0;
                    tail = TAIL_LENGTH;
                    synchronized (lock)
                        {
                        running = true;
                        }

                    if (modeTemp == SOUND_DOWN_NEXT_NEW)
                        {
                        Log.d("MUSIC", "Sound UP");

                        modeTemp = SOUND_UP;
                        synchronized (lock)
                            {
                            frequencyTemp = frequency;
                            mode = SOUND_UP;
                            }
                        }
                    }
                }

            // SOUND_UP - ramp sound up and hold
            if ( modeTemp == SOUND_UP && rampUp < UP_LENGTH )
                {
                rampUp++;
                rampDown = DOWN_LENGTH;
                }
            // SOUND_DOWN - ramp sound down and NEXT_NEW: SOUND UP from 0; NEXT_PAUSE: hold
            else if ( modeTemp < 0 && rampDown > 0 )
                rampDown --;
*/
            tick++;
            }

        /*
        audioTrack.stop();
        audioTrack.release();
        Log.d("MUSIC", "Thread END");
        */
        }
    }
