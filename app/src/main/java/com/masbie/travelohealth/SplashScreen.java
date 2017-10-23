package com.masbie.travelohealth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import timber.log.Timber;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity
{
    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler hideHandler = new Handler();
    private final Runnable hideOperation = new Runnable()
    {
        @Override
        public void run()
        {
            hide();
        }
    };
    private View mContentView;
    private final Runnable hideCompletelyOperation = new Runnable()
    {
        @Override
        public void run()
        {
            SplashScreen.this.hideCompletely();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.d("onCreate");

        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.activity_splash_screen);

        this.mContentView = findViewById(R.id.fullscreen_content);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        Timber.d("onPostCreate");

        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        this.delayedHide(50);
    }

    @Override protected void onPostResume()
    {
        Timber.d("onPostResume");
        super.onPostResume();


    }

    @Override protected void onDestroy()
    {
        Timber.d("onDestroy");

        super.onDestroy();
    }

    private void hide()
    {
        Timber.d("hide");

        // Hide UI first
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.hide();
        }

        // Schedule a runnable to remove the status and navigation bar after a delay
        //this.hideHandler.postDelayed(this.hideCompletelyOperation, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void hideCompletely()
    {
        Timber.d("hideCompletely");

        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        this.mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis)
    {
        Timber.d("hideCompletely");

        this.hideHandler.removeCallbacks(this.hideOperation);
        this.hideHandler.postDelayed(this.hideOperation, delayMillis);
    }
}
