package digitalgarden.xworkbench;

import android.util.Log;

public class Tone
    {
    private static final int VOL_DOWN = -1;
    private static final int VOL_HOLD = 0;
    private static final int VOL_UP = 1;


    private int frequency;
    private int sampleRate;

    private int stage;

    private int volumeUp;
    private int volumeUpLength;

    private int volumeDown;
    private int volumeDownLength;


    Tone( int sampleRate, int frequency, int upLength, int downLength )
        {
        this.sampleRate = sampleRate;
        this.frequency = frequency;

        this.volumeUp = 0;
        this.volumeUpLength = upLength;

        this.volumeDown = downLength;
        this.volumeDownLength = downLength;

        this.stage = (upLength == 0 ? VOL_HOLD : VOL_UP);
        }

    void finish( )
        {
        stage = VOL_DOWN;
        }

    boolean isFinished()
        {
        return volumeDown == 0;
        }

    short getAmplitude( int tick )
        {
        short tone;

        if ( stage == VOL_HOLD ) // volume hold
            {
            tone = getMaxAmplitude( tick );
            }

        else if ( stage > VOL_HOLD ) // volume up
            {
            tone = (short) (getMaxAmplitude( tick ) * volumeUp/volumeUpLength);
            if ( volumeUp < volumeUpLength )
                volumeUp ++;
            else
                {
                stage = VOL_HOLD;
                }
            }

        else // if ( volumeDir < VOL_HOLD ) // volume down
            {
            tone = (short) (getMaxAmplitude( tick ) * volumeDown/volumeDownLength);
            if (volumeDown != 0 ) Log.d("TONE", Short.toString( tone ));
            if ( tone == 0 ) volumeDown = 0;
            //if ( volumeDown > 0 )
            //    volumeDown --;
            }



        return tone;
        }

    private short getMaxAmplitude ( int tick )
        {
        //return (short) ((function( frequency * 2 * Math.PI * tick / sampleRate) * Short.MAX_VALUE ));
        return (short) (Sinus.draft( frequency * Sinus.RESOLUTION * tick / sampleRate ) );
        }

    private double function( double angle )
        {
        return Math.sin( angle );
        }
    }
