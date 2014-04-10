package com.andrewq.planets;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.WindowManager;

/**
 * Created by Andrew Quebe on 4/8/14.
 */
public class SunGLActivity extends Activity {
    private final Handler mHandler = new Handler();
    private SunSurfaceView mGLView = null;
    private final Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (mGLView != null) {
                if (mGLView.mRenderer != null) {
                    float fps = mGLView.mRenderer.fps;
                    setTitle(String.format("%.1f FPS", fps));
                }
            }

            mHandler.postDelayed(this, 1000);
        }
    };
    private ActionBar mActionbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionbar = getActionBar();

        mActionbar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mGLView = new SunSurfaceView(this);
        setContentView(mGLView);

        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGLView != null)
            mGLView.onPause();
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGLView != null)
            mGLView.onResume();
        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, 1000);
    }
}

class SunSurfaceView extends GLSurfaceView {
    public SunRenderer mRenderer = null;
    private ScaleGestureDetector scaleDetector = null;
    private VelocityTracker velocityTracker = null;
    private boolean isScale = false;
    private float mPreviousX = 0;
    private float mPreviousY = 0;

    public SunSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        mRenderer = new SunRenderer(context);
        setRenderer(mRenderer);

        scaleDetector = new ScaleGestureDetector(context, new MyGestureListener());
        velocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        velocityTracker.addMovement(e);

        float x = e.getX(0);
        float y = e.getY(0);

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isScale = false;
                mRenderer.rotateSpeed = 0;
                mRenderer.tiltSpeed = 0;
                break;

            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000);
                mRenderer.rotateSpeed = -velocityTracker.getXVelocity() / (mRenderer.screenWidth * mRenderer.ratioX * mRenderer.scaleFactor * Math.PI);
                mRenderer.tiltSpeed = velocityTracker.getYVelocity() * 0.5f / (mRenderer.screenHeight * mRenderer.ratioY * mRenderer.scaleFactor);
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isScale) {
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;
                    mRenderer.rotateAngle -= dx / (mRenderer.screenWidth * mRenderer.ratioX * mRenderer.scaleFactor * Math.PI);
                    mRenderer.tiltAngle += dy * 0.5f / (mRenderer.screenHeight * mRenderer.ratioY * mRenderer.scaleFactor);
                }
                break;
        }

        mPreviousX = x;
        mPreviousY = y;

        return scaleDetector.onTouchEvent(e);
    }

    private class MyGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mRenderer.scaleFactor *= detector.getScaleFactor();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            isScale = true;
        }
    }
}
