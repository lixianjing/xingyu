
package com.xian.xingyu.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xian.xingyu.MainApp;
import com.xian.xingyu.R;
import com.xian.xingyu.activity.AddActivity;
import com.xian.xingyu.activity.MainActivity;
import com.xian.xingyu.adapter.PublicAdapter;

import java.util.ArrayList;
import java.util.List;

public class PrivateFragment extends Fragment
        implements
        OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "PublicFragment";
    public static final int TYPE_LOAD_FORWARD = 0;
    public static final int TYPE_LOAD_BACKWARD = 1;

    private MainActivity mActivity;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private TextView mTextView;
    private TextView mListBtmTv;
    private RelativeLayout mListBtmRl;
    private LinearLayout mListBtmProgressLayout;

    private List<Integer> mDataList;

    private PublicAdapter mPublicAdapter;

    public PrivateFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // construct the RelativeLayout
        Log.e(TAG, ">>>onCreateView>>>>>>>>>>>>>");
        View view = inflater.inflate(R.layout.fragment_private, null);

        mTextView = (TextView) view.findViewById(R.id.pri_tv);

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.pri_srl);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        initTestData();

        mListView = (ListView) view.findViewById(R.id.pri_lv);

        mListBtmRl =
                (RelativeLayout) LayoutInflater.from(getActivity()).inflate(
                        R.layout.item_public_bottom, null);
        mListBtmTv = (TextView) mListBtmRl.findViewById(R.id.item_public_btm_tv);
        mListBtmProgressLayout = (LinearLayout) mListBtmRl.findViewById(R.id.item_public_btm_ll);
        ListView.LayoutParams params =
                new ListView.LayoutParams(LayoutParams.MATCH_PARENT, getActivity().getResources()
                        .getDimensionPixelOffset(R.dimen.listview_footer_height));
        mListBtmRl.setLayoutParams(params);
        mListBtmRl.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (mListBtmProgressLayout.getVisibility() == View.VISIBLE)
                    return;
                mListBtmTv.setVisibility(View.GONE);
                mListBtmProgressLayout.setVisibility(View.VISIBLE);
                new LoadDataAsyncTask(TYPE_LOAD_BACKWARD).execute(mDataList.get(mDataList.size() - 1));

            }
        });
        mListView.addFooterView(mListBtmRl);

        mPublicAdapter = new PublicAdapter(getActivity());
        mPublicAdapter.setList(mDataList);
        mListView.setAdapter(mPublicAdapter);

        mTextView.setOnClickListener(this);
        
        if (MainApp.isLogin()) {
            showData();
        } else {
            notLogin();
        }

        return view;
    }

    private void initTestData() {
        mDataList = new ArrayList<Integer>();
        for (int i = 100; i < 110; i++) {
            mDataList.add(i);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.pri_tv:
                if (MainApp.isLogin()) {
                    Intent intent = new Intent(mActivity, AddActivity.class);
                    mActivity.startActivity(intent);
                } else {

                    mActivity.showDialogLogin(true);
                }
                break;

            default:
                break;
        }
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

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        Log.e(TAG, ">>>onAttach>>>>>>>>>>>>>");
        mActivity = (MainActivity) activity;
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        Log.e(TAG, ">>>onDetach>>>>>>>>>>>>>");
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub

        new LoadDataAsyncTask(TYPE_LOAD_FORWARD).execute(mDataList.get(0));

    }

    public void notLogin() {
        mTextView.setText("你还没有登陆哦");
        mTextView.setVisibility(View.VISIBLE);
        mSwipeLayout.setVisibility(View.GONE);
    }

    public void showData() {
        mTextView.setText("你还没有秘密哦，赶紧加入吧");

        mTextView.setVisibility(View.VISIBLE);
        mSwipeLayout.setVisibility(View.GONE);

        // mSwipeLayout.setVisibility(View.VISIBLE);
        // mTextView.setVisibility(View.GONE);

    }

    private class LoadDataAsyncTask extends AsyncTask<Integer, Void, List<Integer>> {

        private final int type;

        public LoadDataAsyncTask(int type) {
            this.type = type;
        }

        @Override
        protected List<Integer> doInBackground(Integer... arg0) {
            // TODO Auto-generated method stub

            List<Integer> list = new ArrayList<Integer>();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (type == TYPE_LOAD_FORWARD) {
                for (int i = 11; i > 0; i--) {
                    list.add(arg0[0] - i);
                }
            } else {
                for (int i = 1; i < 11; i++) {
                    list.add(arg0[0] + i);
                }
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Integer> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (type == TYPE_LOAD_FORWARD) {
                mDataList.addAll(0, result);
                mSwipeLayout.setRefreshing(false);
            } else {
                mDataList.addAll(mDataList.size(), result);
                mListBtmTv.setVisibility(View.VISIBLE);
                mListBtmProgressLayout.setVisibility(View.GONE);
            }

            mPublicAdapter.notifyDataSetChanged();

        }

    }

}
