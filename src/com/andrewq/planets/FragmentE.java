package com.andrewq.planets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;

public class FragmentE extends Fragment {

    //Variables
    Button button;
    Button button2;

    public FragmentE() {
        // Required empty public constructor
    }

    //Called when the view is created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Find the buttons
        button = (Button) getView().findViewById(R.id.button_4);
        button2 = (Button) getView().findViewById(R.id.mars_satellite);

        //Open a new browser window
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set the link
                String url = "http://space-facts.com/mars/";
                //Create an intent that will handle the link
                Intent i = new Intent(Intent.ACTION_VIEW);
                //Set intent type to hyperlink so that the system knows to open whatever browser is installed
                i.setData(Uri.parse(url));
                //Start the activity
                startActivity(i);

                //Make custom window animation
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        //Allow the user to be able to select which moon they want to view for mars
        button2.setOnClickListener(new View.OnClickListener() {
            //Handle the button being pressed
            @Override
            public void onClick(View v) {
                //Make a new alert dialog
                new AlertDialog.Builder(getActivity())
                        //Set the title
                        .setTitle("Select Moon")
                                //Set it's items to an array
                        .setItems(R.array.mars_moons, new DialogInterface.OnClickListener() {
                            //Handle what happens when each item is pressed
                            public void onClick(DialogInterface dialog, int which) {
                                //If the user pressed the first item
                                if (which == 0) {
                                    //Open the first moon activity
                                    Intent phobos = new Intent(getActivity(), Phobos.class);
                                    startActivity(phobos);
                                }
                                //Otherwise
                                else {
                                    //In this case, open the other moon activity
                                    Intent deimos = new Intent(getActivity(), Deimos.class);
                                    startActivity(deimos);
                                }
                            }
                        })
                                //Create a cancel button
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            //Handle when it's clicked. Here we don't need it to do anything
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing!
                            }
                        })
                                //Show the dialog after an item is pressed
                        .show();
            }
        });
    }

    //Called when the view is first created; Called before onViewCreated()
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_e, container, false);
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