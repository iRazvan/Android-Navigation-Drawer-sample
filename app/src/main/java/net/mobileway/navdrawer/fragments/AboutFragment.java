package net.mobileway.navdrawer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mobileway.navdrawer.R;


public class AboutFragment extends Fragment {

    private static final String TAG = AboutFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        if (getActivity() != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Show a webview maybe, or show a textview with some info about the app.
    }
}
