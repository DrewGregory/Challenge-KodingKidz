package com.kodingkidz.alzhelp;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;


public class InsideScrapBook extends FragmentActivity {
    private VelocityTracker mVelocityTracker = null;
    boolean portraitOrientation;
    int currentPos = 1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                FragmentManager fragMan = getFragmentManager();
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
                if (VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId) >= 1) {
                    //Flip page to the right
                    System.out.println("Turning page to the left.");
                    currentPos--;
                    if (currentPos != 0) {
                        LeftPage newLeft = LeftPage.newInstance(currentPos);
                        RightPage newRight = RightPage.newInstance(currentPos); //I already added 1 to currentPos in previous line.
                        fragMan.beginTransaction().add(R.id.left_page_fragment_container, newLeft).commit();
                        fragMan.beginTransaction().add(R.id.right_page_fragment_container, newRight).commit();
                    } else {
                        Intent home = new Intent(this, MainActivity.class);
                        startActivity(home);
                    }
                } else if (VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId) <= -1) {
                    //Flip page to the left
                    System.out.println("Turning page to the right.");
                    ++currentPos;
                    if (currentPos != 3) {
                        LeftPage newLeft = LeftPage.newInstance(currentPos);
                        RightPage newRight = RightPage.newInstance(currentPos); //I already subtracted 1 to currentPos in previous line.
                        fragMan.beginTransaction().add(R.id.left_page_fragment_container, newLeft).commit();
                        fragMan.beginTransaction().add(R.id.right_page_fragment_container, newRight).commit();
                    }
                }
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_scrapbook);
        portraitOrientation = findViewById(R.id.little_page) != null;
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


}
