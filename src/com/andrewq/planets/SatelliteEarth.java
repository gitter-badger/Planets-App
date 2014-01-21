package com.andrewq.planets;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SatelliteEarth extends Activity {

        Button button;

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
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);

                        }
                });

        }

}

