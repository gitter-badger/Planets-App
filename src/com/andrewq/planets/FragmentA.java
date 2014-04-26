package com.andrewq.planets;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;

public class FragmentA extends Fragment {

    Button button;
    ImageView imageView;

    public FragmentA() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // final Context classContext = null;
        button = (Button) getView().findViewById(R.id.button_1);
        imageView = (ImageView) getView().findViewById(R.id.sun);

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String url = "http://space-facts.com/the-sun/";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }
                });
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String url = "http://space-facts.com/the-sun/";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                        //getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }
                });

                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        imageView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(), "NOTE: This is still in Beta!",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getActivity(), SunGLActivity.class));
                            }
                        });
                    }
                });

                t.start();
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String url = "http://space-facts.com/the-sun/";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }
                });
                break;
            default:
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return inflater.inflate(R.layout.fragment_a_no_opengl, container, false);
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return inflater.inflate(R.layout.fragment_a, container, false);
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return inflater.inflate(R.layout.fragment_a_no_opengl, container, false);
            default:
        }

        return inflater.inflate(R.layout.fragment_a, container, false);
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
}