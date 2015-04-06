package com.kodingkidz.alzhelp;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * Activities that contain this fragment must implement the
 * {@link com.kodingkidz.alzhelp.LandscapePagesFragment.OnLandscapeFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LandscapePagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LandscapePagesFragment extends android.support.v4.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String POSITION = "position";
    private int position;
    //TODO Replace these arrays with a dynamic list.
    static int[] picIds = {R.drawable.demo_pic_one, R.drawable.demo_pic_two, R.drawable.demo_pic_three};
    static String[] descs = {"A picture of my grandparents.", "A picture of the Gaffneys, the Mylanders, and others.", "The whole third generation! Andrew, Mac, Paige, Elias, Drew, John, Sara, and Adam."};

    private OnLandscapeFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pos position (page # of right / 2 - 1)
     * @return A new instance of fragment LandscapePagesFragment.
     */
    public static LandscapePagesFragment newInstance(int pos) {
        LandscapePagesFragment fragment = new LandscapePagesFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, pos);
        fragment.setArguments(args);
        return fragment;
    }

    public LandscapePagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_landscape_pages, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView text = (TextView) view.findViewById(R.id.text);
        new BitmapWorkerTask(image).execute();  //Uses ASyncTask to load Bitmap off UI Thread.
        text.setText(descs[position]);
        TextView pLeftNum = (TextView) view.findViewById(R.id.land_left_page_num);
        TextView pRightNum = (TextView) view.findViewById(R.id.land_right_page_num);
        pLeftNum.setText((position + 1) * 2 - 1 + "");
        pRightNum.setText((position + 1) * 2 + "");
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLandscapeFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLandscapeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLandscapeFragmentInteractionListener {
        //TODO Add something in here if I need Activity interaction.
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
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = picIds[position];
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
