package com.xian.xingyu.fragment;

import java.util.List;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xian.xingyu.MainApp;
import com.xian.xingyu.R;
import com.xian.xingyu.activity.AddActivity;
import com.xian.xingyu.activity.EmotionActivity;
import com.xian.xingyu.activity.MainActivity;
import com.xian.xingyu.adapter.PublicAdapter;
import com.xian.xingyu.bean.EmotionInfo;
import com.xian.xingyu.db.DBManager;

public class PublicFragment extends Fragment
        implements
            OnClickListener,
            SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "PrivateFragment";

    public static final int TYPE_LOAD_FORWARD = 0;
    public static final int TYPE_LOAD_BACKWARD = 1;

    // 分页 显示
    public static final int LOAD_ITEM_COUNT = 20;
    private int loadCount;

    public static final int MSG_LOAD_FORWARD = 1001;

    private MainActivity mActivity;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private TextView mTitleTv;
    private TextView mListBtmTv;
    private RelativeLayout mListBtmRl;
    private LinearLayout mListBtmProgressLayout;

    private PublicAdapter mPublicAdapter;
    private DBManager mDBManager;

    public PublicFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // construct the RelativeLayout
        Log.e(TAG, ">>>onCreateView>>>>>>>>>>>>>");

        View view = inflater.inflate(R.layout.fragment_public, null);

        mTitleTv = (TextView) view.findViewById(R.id.pub_title_tv);

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.pub_srl);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mListView = (ListView) view.findViewById(R.id.pub_lv);

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
                if (mListBtmProgressLayout.getVisibility() == View.VISIBLE) return;
                mListBtmTv.setVisibility(View.GONE);
                mListBtmProgressLayout.setVisibility(View.VISIBLE);
                startLoadData(TYPE_LOAD_BACKWARD);

            }
        });
        mListView.addFooterView(mListBtmRl);

        initListener();
        startLoadData(TYPE_LOAD_FORWARD);
        return view;
    }

    private void initListener() {
        mTitleTv.setOnClickListener(this);

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Log.e("lmf", ">>>>>>>>>>onItemClick>>>>>>>>>");
                mActivity.startActivity(new Intent(mActivity, EmotionActivity.class));
                mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.pub_title_tv:
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
        mDBManager = DBManager.getInstance(activity);
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

        startLoadData(TYPE_LOAD_FORWARD);

    }


    public void startLoadData(int type) {
        new LoadDataAsyncTask(type).execute();
    }

    private class LoadDataAsyncTask extends AsyncTask<Void, Void, List<EmotionInfo>> {

        private final int type;

        public LoadDataAsyncTask(int type) {
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<EmotionInfo> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result == null || result.size() == 0) {
                loadCount = 0;
                return;
            }

            if (mPublicAdapter == null) {
                mPublicAdapter = new PublicAdapter(mActivity);
                mPublicAdapter.setList(result);
                mListView.setAdapter(mPublicAdapter);
            } else {
                mPublicAdapter.setList(result);
            }
            if (type == TYPE_LOAD_FORWARD) {
                mSwipeLayout.setRefreshing(false);
            } else {
                mListBtmTv.setVisibility(View.VISIBLE);
                mListBtmProgressLayout.setVisibility(View.GONE);
            }

            mPublicAdapter.notifyDataSetChanged();

        }

        @Override
        protected List<EmotionInfo> doInBackground(Void... params) {
            // TODO Auto-generated method stub

            loadCount = loadCount + LOAD_ITEM_COUNT;

            List<EmotionInfo> list = mDBManager.queryEmotion(loadCount);

            return list;
        }

    }

}
