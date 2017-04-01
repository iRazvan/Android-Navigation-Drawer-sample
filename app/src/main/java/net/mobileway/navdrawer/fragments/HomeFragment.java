package net.mobileway.navdrawer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mobileway.navdrawer.R;
import net.mobileway.navdrawer.utils.LogUtils;

/**
 * Created by Razvan on 31/03/17.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (getActivity() != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LogUtils.LOGE(TAG, "Home fragment !");
    }
}
