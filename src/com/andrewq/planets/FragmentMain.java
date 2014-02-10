package com.andrewq.planets;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentMain extends Fragment {

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) this.getView().findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("714F7512E59A56A3C2EF2FB8239F4135").build();
		adView.loadAd(adRequest);
	}

	public FragmentMain() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_main, container, false);
	}
}