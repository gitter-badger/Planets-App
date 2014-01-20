package com.andrewq.planets;

import android.app.Activity;
import android.os.Bundle;

public class SatelliteEarth extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.satellite_earth);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
	}
	
	

}
