package com.swami.vidyanand.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SliderTextActivity extends AppCompatActivity {

    private AdView mAdView;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    IndexGroup group ;

    List<PlaceholderFragment> fragmentList = new ArrayList<PlaceholderFragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_text_fragment);


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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        Intent intent = getIntent();
        String clickedGroup = intent
                .getStringExtra(Constants.CLICKED_GROUP);

        group = IndexDataSource.GetGroup(clickedGroup);

        setTitle(group.get_title());

        for (IndexItem item: group.getItems()){
            PlaceholderFragment itemFrag = new PlaceholderFragment();

            // Context context = getApplicationContext();
            Bundle args = new Bundle();
            args.putString(Constants.IMAGEPATH,
                    item.getImagePath());
            args.putString(Constants.TITLE, item.getContent());
            args.putString(Constants.CLICKED_GROUP,clickedGroup);
            itemFrag.setArguments(args);

            fragmentList.add(itemFrag);
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),fragmentList);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sliter_text, menu);
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
    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }

    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<? extends Fragment> fragments;
        /**
         * @param fm
         * @param fragments
         */
        public SectionsPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        /* (non-Javadoc)
         * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
         */
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getCount()
         */
        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_slider_text, container, false);

            String imagePath = getArguments().getString(Constants.IMAGEPATH);
            String titleVal = getArguments().getString(Constants.TITLE);

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Html.fromHtml(titleVal,null,new CustomTagHandler()));

            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);

            try {
                imageView.setImageDrawable(IndexItem.getImageDrawable(
                        this.getContext(), imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rootView;
        }

    }
}
