package com.kodingkidz.alzhelp;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeftPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeftPage extends android.support.v4.app.Fragment {

    private static final String ARG_POSITION = "Right Page Position";
    static int[] picIds = {R.drawable.demo_pic_one, R.drawable.demo_pic_two, R.drawable.demo_pic_three};
    public static final int NUM_PAGES = picIds.length;

    ImageView currentImage;
    int imageID;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pos The current page position, not counting right pages.
     * @return A new instance of fragment LeftPage.
     */
    public static LeftPage newInstance(int pos) {
        LeftPage fragment = new LeftPage();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, pos);
        fragment.setArguments(args);
        return fragment;
    }

    public LeftPage() {
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, 1);
        setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int pos = getArguments().getInt(ARG_POSITION);
            if (pos >= 1 && pos <= picIds.length){
                imageID = picIds[pos - 1];
            } else if (pos > picIds.length){
                imageID = picIds[picIds.length - 1];
            } else {
                 imageID = picIds[0];
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left_page, container, false);
        currentImage = (ImageView) view.findViewById(R.id.image);
        currentImage.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imageID, 500, 500));
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    /**
     * From Android Developer's Website.
     * Loads Bitmap with appropriate size, using calculateInSampleSize()
     * @param res Just call getResources() on this one.
     * @param resId  The image resource, found with the R class
     * @param reqWidth The width, in pixels, that we want.
     * @param reqHeight The height, in pixels, that we want.
     * @return the Bitmap we want to load.
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
    From Android Developer's Website
    Calculates InSampleSize argument.
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }



}
