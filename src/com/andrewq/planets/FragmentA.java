package com.andrewq.planets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FragmentA extends Fragment {

	Button button;
	ImageView imageView;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// final Context classContext = null;
		button = (Button) getView().findViewById(R.id.button_1);
		imageView = (ImageView) getView().findViewById(R.id.sun);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String url = "http://space-facts.com/the-sun/";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});

		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "NOTE: This is still in Beta.",
						Toast.LENGTH_LONG).show();
				startActivity(new Intent(getActivity(), SunImageView.class));
			}
		});
	}

	public FragmentA() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_a, container, false);
	}
}