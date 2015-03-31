package com.kodingkidz.alzhelp;

import android.app.Activity;
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
public class LeftPage extends Fragment {

    private static final String ARG_POSITION = "Right Page Position";



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
            imageID = picIds[pos - 1];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left_page, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource(imageID);
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



    int[] picIds = {R.drawable.demo_pic_one, R.drawable.demo_pic_two, R.drawable.demo_pic_three};
}
