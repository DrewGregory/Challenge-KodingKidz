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
public class RightPage extends android.support.v4.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PAGE = "The Page";
    //TODO: Replace this test code with the actual, customizable descriptions to come.
    static String[] descs;
    public static int NUM_PAGES;
    private String currentDesc;
    private TextView descText;
    int position;



    public static void setDescs (String[] descriptions) {
        descs = descriptions;
        NUM_PAGES = descs.length;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pos The current page number, counting left pages.
     * @return A new instance of fragment RightPage.
     */
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
            position = getArguments().getInt(PAGE, 1);
            currentDesc = descs[(position - 2) / 2];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right_page, container, false);
        descText = (TextView) view.findViewById(R.id.text);
        descText.setText(currentDesc);
        TextView pageNum = (TextView) view.findViewById(R.id.port_right_page_num);
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
