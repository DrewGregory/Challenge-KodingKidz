package com.kodingkidz.alzhelp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


public class InsideScrapBook extends FragmentActivity implements LandscapePagesFragment.OnLandscapeFragmentInteractionListener {
    private final String LEFT_PAGE = "Left", RIGHT_PAGE = "right";
    boolean portraitOrientation;
    /**
     * The number of pages.
     */
    private static final int NUM_PAGES = Math.min(LeftPage.NUM_PAGES, RightPage.NUM_PAGES);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_scrapbook);
        portraitOrientation = findViewById(R.id.little_page) != null;
        /*timer = new Timer();
        timer.schedule(new TimerListener(), 100, 500);*/
        if (!portraitOrientation) {
            // Instantiate a ViewPager and a PagerAdapter.
            ViewPager bothPages = (ViewPager) findViewById(R.id.big_left_page);
            PagerAdapter bothPagesAdapter = new PageAdapter(getSupportFragmentManager(), LEFT_PAGE);
            bothPages.setPageTransformer(false, new PageFlipAnimation());
            bothPages.setAdapter(bothPagesAdapter);
        }
    }

    private class PageAdapter extends FragmentStatePagerAdapter {
        String pageType;
        public PageAdapter(android.support.v4.app.FragmentManager fm, String rightOrLeft) {
            super(fm);
            pageType = rightOrLeft;
        }

        @Override
        public Fragment getItem(int position) {
            return LandscapePagesFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private class PageFlipAnimation implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            float percentage = 1 - Math.abs(position);
            page.setCameraDistance(10000);
            setVisibility(page, position);
            setTranslation(page);
            setRotation(page, position, percentage);
        }

        private void setVisibility(View page, float position) {
            if (position < 0.5 && position > -0.5) {
                page.setVisibility(View.VISIBLE);
            } else {
                page.setVisibility(View.INVISIBLE);
            }
        }

        private void setTranslation(View page) {
            ViewPager viewPager = (ViewPager) page.getParent();
            int scroll = viewPager.getScrollX() - page.getLeft();
            page.setTranslationX(scroll);
        }


        private void setRotation(View page, float position, float percentage) {
            if (position > 0) {
                page.setRotationY(-180 * (percentage + 1));
            } else {
                page.setRotationY(180 * (percentage + 1));
            }
        }
    }

}
