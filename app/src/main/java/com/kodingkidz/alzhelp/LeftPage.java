package com.kodingkidz.alzhelp;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    public static Bitmap[] pics;
    public static int NUM_PAGES;
    int screenWidth, screenLength;
    ImageView currentImage;
    int position;
    Bitmap image;
    public static void setPics (Bitmap[] pictures) {
        pics = pictures;
        NUM_PAGES = pics.length;
    }
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
            image = pics[(pos - 1) / 2];
            position = pos;
        }
        WindowManager wm = getActivity().getWindowManager();
        Rect rect = new Rect();
        wm.getDefaultDisplay().getRectSize(rect);
        screenWidth = rect.width();
        screenLength = rect.height();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left_page, container, false);
        currentImage = (ImageView) view.findViewById(R.id.image);
        currentImage.setImageBitmap(image);
        //new BitmapWorkerTask(currentImage).execute(); //Loads Bitmap off UI thread.
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



}
