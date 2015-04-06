package com.kodingkidz.alzhelp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;


public class InsideScrapBook extends FragmentActivity implements LandscapePagesFragment.OnLandscapeFragmentInteractionListener {
    final public String SHARED_PREFS = "com.kodingkidz.alzhelp.sharedPreferences";
    final public String ALBUM_NAME = "com.kodingkidz.alzhelp.albumName";
    final public String ALBUM_DESCRIPTION = "com.kodingkidz.alzhelp.albumDescription";
    final public String ALBUM_PICTURE_PATH = "com.kodingkidz.alzhelp.albumPicturePath";
    boolean portraitOrientation;
    String[] descs;

    SharedPreferences prefs;
    /**
     * The number of pages.
     */
    private static int LAND_NUM_PAGES;
    private static int PORT_NUM_PAGES;
    int screenWidth, screenLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_scrapbook);
        portraitOrientation = findViewById(R.id.port_page) != null;
        WindowManager wm = getWindowManager();
        Rect rect = new Rect();
        wm.getDefaultDisplay().getRectSize(rect);
        screenWidth = rect.width();
        screenLength = rect.height();
        prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String albumName = getIntent().getStringExtra(ALBUM_NAME);
        descs = toStringArray(prefs.getString(albumName + ALBUM_DESCRIPTION, ""));
        String[] imagePaths = toStringArray(prefs.getString( albumName + ALBUM_PICTURE_PATH, ""));
        Bitmap[] images = new Bitmap[imagePaths.length];
        for (int index = 0; index < images.length; index++) {
            images[index] = BitmapFactory.decodeFile(imagePaths[index]);
        }
        LeftPage.setPics(images);
        RightPage.setDescs(descs);
        LAND_NUM_PAGES = Math.min(LeftPage.NUM_PAGES, RightPage.NUM_PAGES);
        PORT_NUM_PAGES = LeftPage.NUM_PAGES + RightPage.NUM_PAGES;
        if (!portraitOrientation) {
            // Instantiate a ViewPager and a PagerAdapter for landscape orientation.
            ViewPager bothPages = (ViewPager) findViewById(R.id.landscape_pages);
            PagerAdapter bothPagesAdapter = new LandscapePageAdapter(getSupportFragmentManager());
            //TODO bothPages.setPageTransformer(false, new LandscapePageAnimation()); Uncomment when we make an nice page flip animation.
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
            } else if (position / 2.0 == position / 2) {
                return LeftPage.newInstance(position + 1);
            } else
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
    /**
     * We made this method because you can only store key values and not arrays in SharedPreferences.
     * @param stringInOneLine just pass the string returned from SharedPreferences (with the tabs)
     * @return the Album in its array, without the tabs.
     */
    String[] toStringArray (String stringInOneLine) {

        String[] albumsInArray = stringInOneLine.split("\t");
        //Because it adds a tab before the first album in the createAlbum() method, we have to remove the first element
        String[] tempAlbums = new String[albumsInArray.length - 1];
        if (albumsInArray.length > 0) {
            for (int index = 1; index < albumsInArray.length; index++) {
                tempAlbums[index - 1] = albumsInArray[index];
            }
        }
        return tempAlbums;
    }
}
