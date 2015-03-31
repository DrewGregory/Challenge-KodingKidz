package com.kodingkidz.alzhelp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * <p/>
 * <p/>
 * <p/>
 * Use the {@link RightPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightPage extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PAGE = "The Page";

    private String currentDesc;
    private TextView descText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pos The current page position, not counting left pages.
     * @return A new instance of fragment RightPage.
     */
    // TODO: Rename and change types and number of parameters
    public static RightPage newInstance(int pos) {
        RightPage fragment = new RightPage();
        Bundle args = new Bundle();
        args.putInt(PAGE, pos);
        fragment.setArguments(args);
        return fragment;
    }

    public RightPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int pos = getArguments().getInt(PAGE, 1) + 1;
            currentDesc = descs[pos];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right_page, container, false);
        descText = (TextView) view.findViewById(R.id.text);
        descText.setText(currentDesc);
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


    //TODO: Replace this test code with the actual, customizable descriptions to come.
    String[] descs = {"A picture of my grandparents.", "A picture of the Gaffneys, the Mylanders, and others.", "The whole third generation! Andrew, Mac, Paige, Elias, Drew, John, Sara, and Adam."};
}
