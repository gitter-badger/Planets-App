package com.andrewq.planets;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.analytics.tracking.android.EasyTracker;

public class FragmentPlanets extends Fragment {

    private ViewPager viewPager;
    private PagerTitleStrip pagerTitleStrip;

    public FragmentPlanets() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(getActivity()).activityStart(getActivity());  // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(getActivity()).activityStop(getActivity());  // Add this method.
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewPager = (ViewPager) getView().findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getFragmentManager()));

        pagerTitleStrip = (PagerTitleStrip) getView().findViewById(R.id.pager_title_strip);

        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity());
        int theme_chooser = Integer.parseInt(getPrefs.getString("prefSetTheme", "1"));
        pagerTitleStrip.setTextColor(Color.parseColor("#ffffff"));

        if (theme_chooser == 1) {
            //Red
            pagerTitleStrip.setBackgroundColor(Color.parseColor("#ff4646"));
        } else if (theme_chooser == 2) {
            //Orange
            pagerTitleStrip.setBackgroundColor(Color.parseColor("#fdba35"));
        } else if (theme_chooser == 3) {
            //Blue
            pagerTitleStrip.setBackgroundColor(Color.parseColor("#33b5e5"));
        } else if (theme_chooser == 4) {
            //Green
            pagerTitleStrip.setBackgroundColor(Color.parseColor("#9acc04"));
        } else if (theme_chooser == 5) {
            //Purple
            pagerTitleStrip.setBackgroundColor(Color.parseColor("#aa65cc"));
        } else {
            //Black
            pagerTitleStrip.setBackgroundColor(Color.parseColor("#767676"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planets, container, false);
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
        if (arg0 == 7) {
            fragment = new FragmentH();
        }
        if (arg0 == 8) {
            fragment = new FragmentI();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 9;
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
        if (position == 7) {
            return "Uranus";
        }
        if (position == 8) {
            return "Neptune";
        }
        return null;
    }
}