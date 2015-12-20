package com.swami.vidyanand.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.swami.vidyanand.R;
import com.swami.vidyanand.data.Constants;
import com.swami.vidyanand.gcm.GcmRegistrationAsyncTask;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String message = "";
        final Intent mainIntent ;
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().size() > 0) {
                message = getIntent().getStringExtra(Constants.MESSAGE);
                mainIntent = new Intent(SplashActivity.this,
                        DailySuvicharActivity.class);
                mainIntent.putExtra(Constants.MESSAGE, message);
				/*try {
					ShortcutBadger.setBadge(getApplicationContext(), 0);
				} catch (ShortcutBadgeException e) {
					// TODO Auto-generated catch block
				}*/
            } else {
                mainIntent = new Intent(SplashActivity.this,
                        IndexActivity.class);
            }
        }else {
            mainIntent = new Intent(SplashActivity.this,
                    IndexActivity.class);
        }

        new GcmRegistrationAsyncTask(this).execute();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
