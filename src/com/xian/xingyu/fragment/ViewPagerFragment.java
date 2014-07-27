
package com.xian.xingyu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xian.xingyu.R;


public class ViewPagerFragment extends Fragment {

    private static final String TAG = "ViewPagerFragment";

    public ViewPagerFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // construct the RelativeLayout
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
