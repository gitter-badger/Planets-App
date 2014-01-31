package com.andrewq.planets;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlanetFragment extends Fragment {

	private Button button;
	private TextView titleView;
	private TextView descriptionText;
	private TextView bodyText;
	private ImageView imageView;

	private String url;
	private String title;
	private String[] planet;
	private String description;
	private String body;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		button = (Button) view.findViewById(R.id.button_source);
		titleView = (TextView) view.findViewById(R.id.planet_title);
		descriptionText = (TextView) view.findViewById(R.id.description_text);
		bodyText = (TextView) view.findViewById(R.id.body_text);
		imageView = (ImageView) view.findViewById(R.id.planet_image);

		Bundle args = getArguments();

		planet = args.getStringArray(MainActivity.DESCRIPTIONS);
		title = args.getString(MainActivity.TITLE);
		description = planet[0];
		body = planet[1];
		url = planet[2];
		Resources res = getActivity().getResources();
		String mDrawableName = planet[3];
		mDrawableName = mDrawableName.substring(
				mDrawableName.lastIndexOf("/") + 1,
				mDrawableName.lastIndexOf("."));
		int resID = res.getIdentifier(mDrawableName, "drawable", getActivity()
				.getPackageName());
		Drawable drawable = res.getDrawable(resID);
		imageView.setImageDrawable(drawable);

		titleView.setText(title);
		descriptionText.setText(description);
		bodyText.setText(body);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
	}

	public PlanetFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.planet_fragment, container, false);
	}

}
