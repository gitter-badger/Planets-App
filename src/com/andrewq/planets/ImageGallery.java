package com.andrewq.planets;

/**
 * Created by Andrew Quebe on 4/13/14.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageGallery extends Fragment {
    Integer[] pics = {R.drawable.sun, R.drawable.mercury,
            R.drawable.venus, R.drawable.earth,
            R.drawable.mars, R.drawable.jupiter,
            R.drawable.saturn, R.drawable.neptune,
            R.drawable.uranus};
    LinearLayout imageView;

    public ImageGallery() {
        //Required empty constructor
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setContentView(R.layout.gallery_main);

        try {
            // InputStream in = (new URL("www.google.com").openStream());
        } catch (Exception e) {
            e.getMessage();
        }
        Gallery ga = (Gallery) getActivity().findViewById(R.id.Gallery01);
        ga.setAdapter(new ImageAdapter(getActivity()));

        imageView = (LinearLayout) getActivity().findViewById(R.id.ImageView01);
        ga.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                try {
                    imageView.removeAllViews();
                } catch (Exception e) {
                    e.getMessage();
                }
                TouchImageView touchImageView = new TouchImageView(getActivity());
                touchImageView.setImageResource(pics[arg2]);
                LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
                imageView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                touchImageView.setLayoutParams(lp);
                imageView.addView(touchImageView);
            }

        });

    }

    public class ImageAdapter extends BaseAdapter {

        int imageBackground;
        private Context ctx;

        public ImageAdapter(Context c) {
            ctx = c;
            TypedArray ta = getActivity().obtainStyledAttributes(R.styleable.Gallery1);
            imageBackground = ta.getResourceId(
                    R.styleable.Gallery1_android_galleryItemBackground, 1);
            ta.recycle();
        }

        @Override
        public int getCount() {

            return pics.length;
        }

        @Override
        public Object getItem(int arg0) {

            return arg0;
        }

        @Override
        public long getItemId(int arg0) {

            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ImageView iv = new ImageView(ctx);
            iv.setImageResource(pics[arg0]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(new Gallery.LayoutParams(150, 120));
            iv.setBackgroundResource(imageBackground);
            return iv;
        }
    }
}
