package com.kodingkidz.alzhelp;

import com.kodingkidz.alzhelp.util.SystemUiHider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;



public class InsideScrapBook extends FragmentActivity implements LeftPage.LeftOnFragmentInteractionListener, RightPage.RightOnFragmentListener {
    private VelocityTracker mVelocityTracker = null;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
                if (VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId) >= 1) {
                   //Flip page to the right
                } else if (VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId) <= -1) {
                   //Flip page to the left
                }
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_scrapbook);
        /*FragmentManager fragMan = getFragmentManager();
        fragMan.beginTransaction().add(R.id.left_page, new LeftPage()).commit();
        fragMan.beginTransaction().add(R.id.right_page, new RightPage()).commit();*/

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

}
