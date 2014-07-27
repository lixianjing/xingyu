
package com.xian.xingyu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.xian.xingyu.R;

public class PrivateFragment extends Fragment implements OnClickListener {

    private static final String TAG = "PrivateFragment";


    public PrivateFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // construct the RelativeLayout
        Log.e(TAG, ">>>onCreateView>>>>>>>>>>>>>");
        View view = inflater.inflate(R.layout.fragment_private, null);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.e(TAG, ">>>onCreate>>>>>>>>>>>>>");
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e(TAG, ">>>onDestroy>>>>>>>>>>>>>");
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        Log.e(TAG, ">>>onDestroyView>>>>>>>>>>>>>");
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.e(TAG, ">>>onStart>>>>>>>>>>>>>");
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.e(TAG, ">>>onStop>>>>>>>>>>>>>");
    }



}
