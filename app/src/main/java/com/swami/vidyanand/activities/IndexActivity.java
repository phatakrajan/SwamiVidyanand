package com.swami.vidyanand.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.swami.vidyanand.R;
import com.swami.vidyanand.SwamiVidyanandApplication;
import com.swami.vidyanand.data.Constants;
import com.swami.vidyanand.data.IndexAdapter;
import com.swami.vidyanand.data.IndexCommon;
import com.swami.vidyanand.data.IndexDataSource;
import com.swami.vidyanand.data.IndexGroup;
import com.swami.vidyanand.data.IndexItem;
import com.swami.vidyanand.gcm.GcmRegistrationAsyncTask;
import com.swami.vidyanand.xml.XMLParser;
import com.swami.vidyanand.xml.XmlModel;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class IndexActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private AdView mAdView;

    private InterstitialAd interstitial;

    ListView listView;

    List<? extends IndexCommon> groups;

    long lastPress = 0;

    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);



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

        AdRequest adRequestInt = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        interstitial.loadAd(adRequestInt);

        IndexDataSource dataSrc = new IndexDataSource();

        groups = dataSrc.getGroups();

        listView = (ListView) findViewById(R.id.listView);

        @SuppressWarnings("unchecked")
        List<IndexCommon> listOfGroups = (List<IndexCommon>) groups;
        IndexAdapter adapter = new IndexAdapter(this, R.layout.activity_index,
                listOfGroups);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = null;
        String uniqueId = groups.get(position).get_uniqueId();
        List<XmlModel> myData = null;

        IndexGroup group = IndexDataSource.GetGroup(uniqueId);

        String fileName = "";
        if (uniqueId.equalsIgnoreCase(Constants.ID_ASHRAM)) {
            fileName = "xml/Ashram.xml";
        } else if (uniqueId.equalsIgnoreCase(Constants.ID_MATAJI)) {
            fileName = "xml/Mataji.xml";
        } else if (uniqueId
                .equalsIgnoreCase(Constants.ID_PARAMPARA)) {
            fileName = "xml/Parampara.xml";
        } else if (uniqueId.equalsIgnoreCase(Constants.ID_PARICHAY)) {
            fileName = "xml/Parichay.xml";
        } else if (uniqueId
                .equalsIgnoreCase(Constants.ID_PRABODHAN)) {
            fileName = "xml/Prabodhan.xml";
        } else if (uniqueId.equalsIgnoreCase(Constants.ID_SADHANA)) {
            fileName = "xml/Sadhana.xml";
        } else if (uniqueId.equalsIgnoreCase(Constants.ID_SAMPARK)) {
            fileName = "xml/Sampark.xml";
        } else if (uniqueId.equalsIgnoreCase(Constants.ID_SUVICHAR)) {
            fileName = "xml/Suvichar.xml";
        } else if (uniqueId.equalsIgnoreCase(Constants.ID_GRANTHALAY)) {
            fileName = "xml/Granthalay.xml";
        }

        try {
            if (group.getItems().size() == 0) {
                InputStream is = getResources().getAssets().open(fileName);
                XMLParser parser = new XMLParser();
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser sp = factory.newSAXParser();
                XMLReader reader = sp.getXMLReader();
                reader.setContentHandler(parser);
                reader.parse(new InputSource(is));

                myData = parser.list;
                if (myData != null) {
                    for (XmlModel xmlRowData : myData) {
                        if (xmlRowData != null) {
                            String title = xmlRowData.getTitle();
                            String element = xmlRowData.getElement();
                            String imagePath = xmlRowData.getImagePath();
                            String description = xmlRowData.getDescription();
                            String content = xmlRowData.getContent();

                            group.getItems().add(
                                    new IndexItem(element, title, description,
                                            imagePath, content, group));
                        }
                    }
                }

            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "Clicked Item :- " + uniqueId + "Exception : "
                            + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }


        if (uniqueId.equals(Constants.ID_ASHRAM) || uniqueId.equals(Constants.ID_PARAMPARA)){
            intent = new Intent(IndexActivity.this,
                    SliderImageActivity.class);
        }
        else if (uniqueId.equals(Constants.ID_MATAJI) || uniqueId.equals(Constants.ID_PARICHAY)
                || uniqueId.equals(Constants.ID_SADHANA) || uniqueId.equals(Constants.ID_GRANTHALAY)
                || uniqueId.equals(Constants.ID_SAMPARK)){
            intent = new Intent(IndexActivity.this,
                    ShortItemDescriptionActivity.class);
        }
        else if (uniqueId.equals(Constants.ID_PRABODHAN) || uniqueId.equals(Constants.ID_SUVICHAR)){
            intent = new Intent(IndexActivity.this,
                    SliderTextActivity.class);
        }
        intent.putExtra(Constants.CLICKED_GROUP, uniqueId);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
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

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }

        SwamiVidyanandApplication.getInstance().trackScreenView(Constants.ID_HOME);
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {


        long currentTime = System.currentTimeMillis();
        if(currentTime - lastPress > 3000){
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            lastPress = currentTime;
        }else{
            super.onBackPressed();
        }
    }
}
