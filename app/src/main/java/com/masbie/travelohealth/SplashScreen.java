package com.masbie.travelohealth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.masbie.travelohealth.dao.TokenDao;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.dao.external.auth.LoginDao;
import com.masbie.travelohealth.pojo.auth.TokenPojo;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.response.ResultPojo;
import java.util.concurrent.CountDownLatch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private static final int      UI_ANIMATION_DELAY = 300;
    private final        Handler  hideHandler        = new Handler();
    private final        Runnable hideOperation      = new Runnable()
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

        new TokenValidationTask().execute();
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

    private class TokenValidationTask extends AsyncTask<Void, Void, Void>
    {
        private CountDownLatch checkLatch;
        private boolean needLogin = true;

        @Override protected void onPreExecute()
        {
            Timber.d("onPreExecute");

            super.onPreExecute();
            this.checkLatch = new CountDownLatch(1);
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            Timber.d("doInBackground");

            @Nullable TokenPojo token = TokenDao.retrieveToken(SplashScreen.this);

            if(token == null)
            {
                this.checkLatch.countDown();
            }
            else
            {
                LoginDao.ping(SplashScreen.this, new Callback<ResponsePojo<Void>>()
                {
                    @SuppressWarnings("ConstantConditions") @Override public void onResponse(@NonNull Call<ResponsePojo<Void>> call, @NonNull Response<ResponsePojo<Void>> response)
                    {
                        @NonNull final ResultPojo<Void> result = response.body().getData();
                        if(result.getStatus() == 1)
                        {
                            needLogin = false;
                        }
                        checkLatch.countDown();
                    }

                    @Override public void onFailure(@NonNull Call<ResponsePojo<Void>> call, @NonNull Throwable throwable)
                    {
                        Dao.defaultFailureTask(SplashScreen.this, call, throwable);
                        checkLatch.countDown();
                    }
                });
            }

            try
            {
                this.checkLatch.await();
            }
            catch(InterruptedException e)
            {
                Timber.e(e);
            }
            return null;
        }

        @Override protected void onPostExecute(Void aVoid)
        {
            Timber.d("onPostExecute");
            super.onPostExecute(aVoid);
            new Handler().postDelayed(new Runnable()
            {
                public void run()
                {
                    @NonNull Intent intent;
                    if(needLogin)
                    {
                        intent = new Intent(SplashScreen.this, LoginActivity.class);
                    }
                    else
                    {
                        intent = new Intent(SplashScreen.this, Home.class);
                    }
                    SplashScreen.super.startActivity(intent);
                    SplashScreen.super.finish();
                }
            }, 2 * 1000);
        }
    }
}
