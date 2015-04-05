package com.kodingkidz.alzhelp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;


public class InsideScrapBook extends FragmentActivity implements LandscapePagesFragment.OnLandscapeFragmentInteractionListener {
    private final String LEFT_PAGE = "Left", RIGHT_PAGE = "right";
    boolean portraitOrientation;
    /**
     * The number of pages.
     */
    private static final int LAND_NUM_PAGES = Math.min(LeftPage.NUM_PAGES, RightPage.NUM_PAGES);
    private static final int PORT_NUM_PAGES = LeftPage.NUM_PAGES + RightPage.NUM_PAGES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_scrapbook);
        portraitOrientation = findViewById(R.id.port_page) != null;
        /*timer = new Timer();
        timer.schedule(new TimerListener(), 100, 500);*/
        if (!portraitOrientation) {
            // Instantiate a ViewPager and a PagerAdapter for landscape orientation.
            ViewPager bothPages = (ViewPager) findViewById(R.id.landscape_pages);
            PagerAdapter bothPagesAdapter = new LandscapePageAdapter(getSupportFragmentManager());
            //bothPages.setPageTransformer(false, new LandscapePageAnimation()); Uncomment when I make an nice page flip animation.
            bothPages.setAdapter(bothPagesAdapter);
        } else {
            //This one is for portrait orientation.
            ViewPager singlePage = (ViewPager) findViewById(R.id.port_page);
            PagerAdapter singlePageAdapter = new PortraitPageAdapter(getSupportFragmentManager());
            //TODO Make a PortraitPageAnimation and setPageTransformer(new PortraitPageAnimation());
            singlePage.setAdapter(singlePageAdapter);
        }

    }
    private class PortraitPageAdapter extends FragmentStatePagerAdapter {

        public PortraitPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return LeftPage.newInstance(position + 1);
            } else if (position == 1) {
                return RightPage.newInstance(position + 1);
            } else if (position/2.0 == position/2) {
                return LeftPage.newInstance(position + 1);
            }
            else
            return RightPage.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return PORT_NUM_PAGES;
        }
    }
    private class LandscapePageAdapter extends FragmentStatePagerAdapter {

        public LandscapePageAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            return LandscapePagesFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return LAND_NUM_PAGES;
        }
    }

    private class LandscapePageAnimation implements ViewPager.PageTransformer {

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
