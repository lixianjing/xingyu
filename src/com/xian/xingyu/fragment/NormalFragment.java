
package com.xian.xingyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.xian.xingyu.R;
import com.xian.xingyu.activity.SecondActivity;

public class NormalFragment extends Fragment implements OnClickListener {

    private static final String TAG = "NormalFragment";

    private Button btn1;

    public NormalFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // construct the RelativeLayout
        View view = inflater.inflate(R.layout.fragment_normal, null);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        this.getActivity().startActivity(new Intent(this.getActivity(), SecondActivity.class));
        this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
