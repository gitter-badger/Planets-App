package com.andrewq.planets;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentHome extends Fragment {

	private RandomQuotes mRandomQuotes = new RandomQuotes();
	TextView mQuoteView;
	Handler handler = new Handler();

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	public FragmentHome() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		mQuoteView = (TextView) v.findViewById(R.id.tvRandomQuote);
		mQuoteView.setGravity(Gravity.CENTER);
		handler.post(updateTextRunnable);

		return v;

	}

	Runnable updateTextRunnable = new Runnable() {

		@Override
		public void run() {
			final String quotes = mRandomQuotes.getQuotes();
			mQuoteView.setText(quotes);
		}
	};

}
