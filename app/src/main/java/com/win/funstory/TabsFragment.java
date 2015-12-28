package com.win.funstory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabsFragment extends Fragment {


    public TabsFragment() {
        // Required empty public constructor
    }

    public static TabsFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        TabsFragment fragment = new TabsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView=(TextView)view.findViewById(R.id.pager_txt);

        String s=getArguments().getString("text");
        textView.setText(s);
    }
}
