package digitalgarden.xworkbench;



class Sinus
    {
    static final int RESOLUTION = 1000;

    static short[] values = new short[ RESOLUTION ];

    static
        {
        for (int n = 0; n < RESOLUTION; n++)
            {
            values[n] = (short)Math.sin( 2 * Math.PI * n / RESOLUTION );
            }
        }

    static short draft( int degree )
        {
        return values[ degree % RESOLUTION ];
        }


    }
