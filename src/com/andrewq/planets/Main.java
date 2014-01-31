/*License

THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS 
OF THIS CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR "LICENSE"). 
THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW. 
ANY USE OF THE WORK OTHER THAN AS AUTHORIZED UNDER THIS LICENSE 
OR COPYRIGHT LAW IS PROHIBITED.

Creative Commons License

This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License; 
you may not use this work except in compliance with the License.

You may obtain a copy of the License in the LICENSE file, 
or at http://creativecommons.org/licenses/by-nc-nd/3.0/deed.en_US

BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, 
YOU ACCEPT AND AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE. 
TO THE EXTENT THIS LICENSE MAY BE CONSIDERED TO BE A CONTRACT, 
THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED HERE IN CONSIDERATION 
OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 */

package com.andrewq.planets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

public class Main extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	private DrawerLayout mDrawerLayout;
	private ListView mLeftDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

	@SuppressWarnings("unused")
	private CharSequence mTitle;
	private String[] mFragmentTitles;
    private NavigationDrawerFragment mNavigationDrawerFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.main);
		
        // Set up the drawer
		mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
		
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mLeftDrawer);

		return super.onPrepareOptionsMenu(menu);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerLayout.isDrawerOpen(mLeftDrawer)) {
				mDrawerLayout.closeDrawer(mLeftDrawer);
			} else {
				mDrawerLayout.openDrawer(mLeftDrawer);
			}
			return true;

		case R.id.action_about:
			aboutMenuItem();
			break;
		case R.id.action_settings:
			Intent intent = new Intent(getApplicationContext(), Settings.class);
			startActivity(intent);
			break;
		}

		return true;
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

	@SuppressWarnings("unused")
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Fragment newFragment = new FragmentA();
		FragmentManager fm = getSupportFragmentManager();
		switch (position) {
		case 0:
			newFragment = new FragmentA();
			break;
		case 1:
			newFragment = new FragmentB();
			break;
		case 2:
			newFragment = new FragmentC();
			break;
		case 3:
			newFragment = new FragmentD();
			break;
		case 4:
			newFragment = new FragmentE();
			break;
		case 5:
			newFragment = new FragmentF();
			break;
		case 6:
			newFragment = new FragmentG();
			break;
		case 7:
			newFragment = new FragmentH();
			break;
		case 8:
			newFragment = new FragmentI();
			break;

		}

		fm.beginTransaction().replace(R.id.content_frame, newFragment).commit();

		mLeftDrawer.setItemChecked(position, true);
		setTitle(mFragmentTitles[position]);
		mDrawerLayout.closeDrawer(mLeftDrawer);
	}

	/*
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(title);
	} */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		
	}

}
