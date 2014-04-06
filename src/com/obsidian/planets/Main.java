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

package com.obsidian.planets;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andrewq.planets.FragmentHome;
import com.andrewq.planets.FragmentPlanets;
import com.andrewq.planets.R;
import com.andrewq.planets.Settings;
import com.google.analytics.tracking.android.EasyTracker;

public class Main extends FragmentActivity {

    /* Things to do:
     * Put in more quotes
     * Work on sun image view (large)
     */

    //Variables
    private DrawerLayout mDrawerLayout;
    private ListView mLeftDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mFragmentTitles;

    private ActionBar mActionBar;

    //Called when the user first opens the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the layout
        setContentView(R.layout.main);

        //Enable the wakelock
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Initialize variables
        mTitle = mDrawerTitle = getTitle();
        mFragmentTitles = getResources().getStringArray(R.array.fragments);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mLeftDrawer = (ListView) findViewById(R.id.left_drawer);

        mLeftDrawer.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mFragmentTitles));
        mLeftDrawer.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //Handle the drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_dark, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View v) {
                getActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View v) {
                getActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            selectItem(0);
        }

        //Start the application with the drawer open if it is the first time they have opened it
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPrefs.getBoolean("key", true);

                if (isFirstStart) {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                    Editor e = getPrefs.edit();
                    e.putBoolean("key", false);
                    e.commit();
                }
            }
        });

        t.start();
    }

    //Handle theme changer options
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //Set getPrefs to a preference manager
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        //Give theme_chooser the preference key defined in XML
        int theme_chooser = Integer.parseInt(getPrefs.getString("prefSetTheme", "1"));
        //Get an instance of the ActionBar
        mActionBar = getActionBar();

        //Set the action bar colors to whatever the user selects from the ListPreference
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

    //Called when the application is reopened
    @Override
    protected void onResume() {
        super.onResume();

        //Resume wakelock
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    //Called when the application is cleared in the recent apps section of the Android System
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Clear wakelock
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    //Called when the user presses the home button or goes to another application
    @Override
    protected void onStop() {
        super.onStop();

        //Close Google Analytics
        EasyTracker.getInstance(this).activityStop(this);

        //Clear wakelock
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    //Called when the user leaves the application
    @Override
    protected void onPause() {
        super.onPause();
    }

    //Handle drawer toggle
    @SuppressWarnings("unused")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mLeftDrawer);
        return super.onPrepareOptionsMenu(menu);
    }

    //Inflate the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Handle what happens when an action bar item is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(mLeftDrawer)) {
                    mDrawerLayout.closeDrawer(mLeftDrawer);
                } else {
                    mDrawerLayout.openDrawer(mLeftDrawer);
                }
                return true;

            //Search feature coming soon
            /*case R.id.action_search:
                searchMenuItem();
                break;*/
        }

        return true;
    }

    //Handle when each nav drawer item is pressed
    private void selectItem(int position) {
        Fragment newFragment = new FragmentHome();
        FragmentManager fm = getSupportFragmentManager();
        switch (position) {
            case 0:
                newFragment = new FragmentHome();
                break;
            case 1:
                newFragment = new FragmentPlanets();
                break;
            case 2:
                String url = "mailto:andrewquebe.14@gmail.com?subject=RE: Planets Feedback/Suggestion&body=Dear Developer,%0D%0A";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case 3:
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }

        fm.beginTransaction().replace(R.id.content_frame, newFragment).commit();

        mLeftDrawer.setItemChecked(position, true);
        setTitle(mFragmentTitles[position]);
        mDrawerLayout.closeDrawer(mLeftDrawer);
    }

    //Set the title of the nav drawer
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(title);
    }

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

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            selectItem(position);
        }
    }

}