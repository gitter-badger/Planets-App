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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
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
		if (box == true) {
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

	public MyAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		if (arg0 == 0) {
			fragment = new FragmentA();
		}
		if (arg0 == 1) {
			fragment = new FragmentB();
		}
		if (arg0 == 2) {
			fragment = new FragmentC();
		}
		if (arg0 == 3) {
			fragment = new FragmentD();
		}
		if (arg0 == 4) {
			fragment = new FragmentE();
		}
		if (arg0 == 5) {
			fragment = new FragmentF();
		}
		if (arg0 == 6) {
			fragment = new FragmentG();
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 7;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position == 0) {
			return "Sun";
		}
		if (position == 1) {
			return "Mercury";
		}
		if (position == 2) {
			return "Venus";
		}
		if (position == 3) {
			return "Earth";
		}
		if (position == 4) {
			return "Mars";
		}
		if (position == 5) {
			return "Jupiter";
		}
		if (position == 6) {
			return "Saturn";
		}
		return null;
	}
}
