package com.sdm.mgpa1;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashScreen extends Activity{
    protected boolean _active = true;
    // boolean use to check for whether the page is active and running
    protected int _splashTime = 5000;
    // meant for wait so after something the splashpage will auto transit to the
    //main menu

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            _active = false;
        }
        return true;
    }
    // boolean active = false = dun want this view anymore = go to next screen
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        // Create the view from splashpage.xml

        //thread for displaying the Splash Screen
        Thread splashTread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    int waited = 0;
                    while (_active && (waited < _splashTime))
                    {
                        sleep(200);
                        if (_active)
                        {
                            waited += 200;
                        }
                    }
                }
                catch (InterruptedException e)
                {
                    //do nothing
                }
                finally
                {
                    finish();
                    //Create new activity based on and intent with CurrentActivity
                    Intent intent = new Intent(SplashScreen.this, Mainmenu.class);
                    startActivity(intent);
                }
            }
        };
        splashTread.start();
    }
}
