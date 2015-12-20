package com.swami.vidyanand.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.swami.vidyanand.R;
import com.swami.vidyanand.SwamiVidyanandApplication;
import com.swami.vidyanand.data.Constants;
import com.swami.vidyanand.utils.CustomTagHandler;

public class DailySuvicharActivity extends AppCompatActivity {

    String message = null ;
    TextView tip_message ;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_suvichar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) findViewById(R.id.ad_view);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

        // Obtain the shared Tracker instance.
        SwamiVidyanandApplication application = (SwamiVidyanandApplication)getApplication();
        application.trackScreenView(Constants.ID_DAILYSUVICHAR);

        Intent intent = getIntent();
        if (savedInstanceState != null) {
            message = savedInstanceState.getString(Constants.MESSAGE);
        }
        else {
            message = intent.getStringExtra(Constants.MESSAGE);
        }
        tip_message = (TextView) findViewById(R.id.message);
        tip_message.setText(Html.fromHtml(message, null, new CustomTagHandler()));
        tip_message.setMovementMethod(LinkMovementMethod.getInstance());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }

        SwamiVidyanandApplication.getInstance().trackScreenView(getTitle().toString());
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
