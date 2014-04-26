package com.andrewq.planets;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;

public class SatelliteEarth extends Activity {

    Button button;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.satellite_earth);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);

        button = (Button) findViewById(R.id.moon_button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String url = "http://space-facts.com/the-moon/";
                Bundle scaleBundle = ActivityOptions.makeScaleUpAnimation(
                        v, 0, 0, v.getWidth(), v.getHeight()).toBundle();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i, scaleBundle);

                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        int theme_chooser = Integer.parseInt(getPrefs.getString("prefSetTheme", "1"));
        mActionBar = getActionBar();

        if (theme_chooser == 1) {
            //Red
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc0202")));
        } else if (theme_chooser == 2) {
            //Orange
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff8801")));
        } else if (theme_chooser == 3) {
            //Blue
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0497c9")));
        } else if (theme_chooser == 4) {
            //Green
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#679a03")));
        } else if (theme_chooser == 5) {
            //Purple
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9832cb")));
        } else {
            //Black
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#292929")));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }
}
