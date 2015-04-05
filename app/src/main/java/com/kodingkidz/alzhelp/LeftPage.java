package com.kodingkidz.alzhelp;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;


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
    int imageID, position;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pos The current page position, counting right pages.
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
            imageID = picIds[(pos - 1) / 2];
            position = pos;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left_page, container, false);
        currentImage = (ImageView) view.findViewById(R.id.image);
        new BitmapWorkerTask(currentImage).execute(); //Loads Bitmap off UI thread.
        TextView pageNum = (TextView) view.findViewById(R.id.port_left_page_num);
        pageNum.setText(position + "");
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
     *
     * @param res       Just call getResources() on this one.
     * @param resId     The image resource, found with the R class
     * @param reqWidth  The width, in pixels, that we want.
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
     * From Android Developer's Website
     * Calculates InSampleSize argument.
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

    /*
        From Android Developer's Website:
        Loads Bitmap off the UI thread.
         */
    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = picIds[(position - 1) / 2];
            return decodeSampledBitmapFromResource(getResources(), data, 500, 500);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
