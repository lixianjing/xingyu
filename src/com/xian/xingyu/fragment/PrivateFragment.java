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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xian.xingyu.MainApp;
import com.xian.xingyu.R;
import com.xian.xingyu.activity.AddActivity;
import com.xian.xingyu.activity.MainActivity;
import com.xian.xingyu.adapter.PrivateAdapter;
import com.xian.xingyu.bean.EmotionInfo;
import com.xian.xingyu.db.DBManager;

public class PrivateFragment extends Fragment
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
    private TextView mTextView;
    private TextView mListBtmTv;
    private RelativeLayout mListBtmRl;
    private LinearLayout mListBtmProgressLayout;

    private PrivateAdapter mPrivateAdapter;
    private DBManager mDBManager;


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
                if (mListBtmProgressLayout.getVisibility() == View.VISIBLE) return;
                mListBtmTv.setVisibility(View.GONE);
                mListBtmProgressLayout.setVisibility(View.VISIBLE);
                startLoadData(TYPE_LOAD_BACKWARD);

            }
        });
        mListView.addFooterView(mListBtmRl);

        mTextView.setOnClickListener(this);

        if (MainApp.isLogin()) {
            startLoadData(TYPE_LOAD_FORWARD);
        } else {
            showText("你还没有登录哦");
        }

        return view;
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

    public void showText(String text) {
        mTextView.setText(text);
        mTextView.setVisibility(View.VISIBLE);
        mSwipeLayout.setVisibility(View.GONE);
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
            if (loadCount == 0) {
                showText("正在加载数据");
            }
        }


        @Override
        protected void onPostExecute(List<EmotionInfo> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.e("lmf", ">>>>>>>>>onPostExecute>>>>>>>>>>>>>" + result.size());
            if (result.size() == 0) {
                loadCount = 0;
                showText("你還沒有數據，抓緊添加吧");
                return;
            } else {
                mTextView.setVisibility(View.GONE);
                mSwipeLayout.setVisibility(View.VISIBLE);
            }

            if (mPrivateAdapter == null) {
                mPrivateAdapter = new PrivateAdapter(mActivity);
                mPrivateAdapter.setList(result);
                mListView.setAdapter(mPrivateAdapter);
            } else {
                mPrivateAdapter.setList(result);
            }
            if (type == TYPE_LOAD_FORWARD) {
                mSwipeLayout.setRefreshing(false);
            } else {
                mListBtmTv.setVisibility(View.VISIBLE);
                mListBtmProgressLayout.setVisibility(View.GONE);
            }

            mPrivateAdapter.notifyDataSetChanged();

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
