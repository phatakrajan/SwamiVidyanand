package com.swami.vidyanand.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.swami.vidyanand.R;
import com.swami.vidyanand.data.Constants;
import com.swami.vidyanand.data.IndexDataSource;
import com.swami.vidyanand.data.IndexGroup;
import com.swami.vidyanand.data.IndexItem;
import com.swami.vidyanand.utils.CustomTagHandler;

public class ShortItemDescriptionActivity extends AppCompatActivity {

    IndexGroup group ;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_item_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        Intent intent = getIntent();
        String clickedGroup = intent
                .getStringExtra(Constants.CLICKED_GROUP);

        group = IndexDataSource.GetGroup(clickedGroup);

        IndexItem item = group.getItems().get(0);

        setTitle(group.get_title());

        TextView textView = (TextView) findViewById(R.id.description);
        textView.setText(Html.fromHtml(item.getContent(),null,new CustomTagHandler()));

        ImageView imgView = (ImageView) findViewById(R.id.icon);
        try {
            imgView.setImageDrawable(IndexItem.getImageDrawable(getApplicationContext(), item.getImagePath()));
        }catch (Exception ex)
        {

        }
    }

}
