package com.andrewq.planets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class MainActivity extends FragmentActivity {

	ViewPager viewPager;

	// constants which act as keys for sending Bundle values to PlanetFragment
	public static final String TITLE = "title";
	public static final String DESCRIPTIONS = "descriptions";

	// set up arrays from String resources in order to dynamically build a
	// PlanetFragment. These get passed in via the MyAdapter constructor
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		String[] sun = getResources().getStringArray(R.array.Sun);
		String[] mercury = getResources().getStringArray(R.array.Mercury);
		String[] venus = getResources().getStringArray(R.array.Venus);
		String[] earth = getResources().getStringArray(R.array.Earth);
		String[] mars = getResources().getStringArray(R.array.Mars);
		String[] jupiter = getResources().getStringArray(R.array.Jupiter);
		String[] saturn = getResources().getStringArray(R.array.Saturn);
		String[] uranus = getResources().getStringArray(R.array.Uranus);
		String[] neptune = getResources().getStringArray(R.array.Neptune);

		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), sun,
				mercury, venus, earth, mars, jupiter, saturn, uranus, neptune));
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.action_about:
			aboutMenuItem();
			break;
		case R.id.action_settings:
			Intent intent = new Intent(getApplicationContext(), Settings.class);
			startActivity(intent);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		boolean box = getPrefs.getBoolean("pref_wakelock", true);
		// shorthand for what you previously had here. When you evaluate an IF
		// statement it checks to see if what you have placed inside the
		// parenthesis evaluates to true or false
		if (box) {
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} else {
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
		super.onWindowFocusChanged(hasFocus);
	}

	public void aboutMenuItem() {
		new AlertDialog.Builder(this)
				.setTitle("A Message from the Developer")
				.setMessage(
						"Hello! I would like to thank you for downloading this app and "
								+ "I think you'll find it useful. \n\n"
								+ "This app is useful whether you are a young child, a student, a parent, or just someone who wants to learn more about the solar system you live in. "
								+ "There are facts here about the celestial bodies in our solar system. \n\n"
								+ "I'm a student in high school and intend to study computer science at M.I.T. With technology advancing every day, "
								+ "a job in the tech industry is one of my goals that I think will be very successful. "
								+ "This application has been a work in progress and has overcome a lot of technical challenges. "
								+ "I hope that this app is useful for you and that it suits all your needs! \n\n"
								+ "Thank you.")
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				}).show();
	}
}

class MyAdapter extends FragmentStatePagerAdapter {

	private static final String[] PLANETS = { "Sun", "Mercury", "Venus",
			"Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune" };
	private final String[] SUN;
	private final String[] MERCURY;
	private final String[] VENUS;
	private final String[] EARTH;
	private final String[] MARS;
	private final String[] JUPITER;
	private final String[] SATURN;
	private final String[] URANUS;
	private final String[] NEPTUNE;

	public MyAdapter(FragmentManager fm, String[] sun, String[] mercury,
			String[] venus, String[] earth, String[] mars, String[] jupiter,
			String[] saturn, String[] uranus, String[] neptune) {
		super(fm);
		this.SUN = sun;
		this.MERCURY = mercury;
		this.VENUS = venus;
		this.EARTH = earth;
		this.MARS = mars;
		this.JUPITER = jupiter;
		this.SATURN = saturn;
		this.URANUS = uranus;
		this.NEPTUNE = neptune;
	}

	// Use one PlanetFragment and dynamically build it according to the items in
	// the corresponding array.
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new PlanetFragment();
		Bundle bundle = new Bundle();
		String planet = getPlanet(position);
		String[] planetDetails = getPlanetDetails(planet);
		bundle.putString(MainActivity.TITLE, PLANETS[position]);
		bundle.putStringArray(MainActivity.DESCRIPTIONS, planetDetails);
		fragment.setArguments(bundle);
		return fragment;
	}

	// Find from the PLANETS array the item at index "position"
	public String getPlanet(int position) {
		String planet = PLANETS[position];
		return planet;
	}

	// Use the previously found item in the PLANETS array and return the array
	// with that planet name
	public String[] getPlanetDetails(String planet) {
		String[] planetDetails = null;
		if (planet.equals("Sun")) {
			return SUN;
		} else if (planet.equals("Mercury")) {
			return MERCURY;
		} else if (planet.equals("Venus")) {
			return VENUS;
		} else if (planet.equals("Earth")) {
			return EARTH;
		} else if (planet.equals("Mars")) {
			return MARS;
		} else if (planet.equals("Jupiter")) {
			return JUPITER;
		} else if (planet.equals("Saturn")) {
			return SATURN;
		} else if (planet.equals("Uranus")) {
			return URANUS;
		} else if (planet.equals("Neptune")) {
			return NEPTUNE;
		}

		return planetDetails;
	}

	@Override
	public int getCount() {
		return 9;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return PLANETS[position];
	}
}
