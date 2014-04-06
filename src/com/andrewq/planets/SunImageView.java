package com.andrewq.planets;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class SunImageView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sun_image_view);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }
}
