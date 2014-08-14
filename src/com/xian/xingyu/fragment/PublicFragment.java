package com.xian.xingyu.fragment;

import org.espier.messages.util.AsyncWeakHandlerTemplate;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.xian.xingyu.db.DBInfo;
import com.xian.xingyu.db.DBManager;

public class PublicFragment extends Fragment
        implements
            OnClickListener,
            SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "PublicFragment";
    private static final int MESSAGE_LIST_QUERY_TOKEN = 1001;

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
    private BackgroundQueryHandler mBackgroundQueryHandler;
    private ContentResolver mContentResolver;

    public PublicFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // construct the RelativeLayout
        Log.e(TAG, ">>>onCreateView>>>>>>>>>>>>>");


        mContentResolver = mActivity.getContentResolver();
        mBackgroundQueryHandler = new BackgroundQueryHandler(mContentResolver);
        mBackgroundQueryHandler.setContext(PublicFragment.this);

        View view = inflater.inflate(R.layout.fragment_public, null);

        mTitleTv = (TextView) view.findViewById(R.id.pub_title_tv);

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.pub_srl);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        mListView = (ListView) view.findViewById(R.id.pub_lv);

        initListView();
        initListener();

        startMsgListQuery(TYPE_LOAD_FORWARD);
        return view;
    }


    private void initListView() {
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
                startMsgListQuery(TYPE_LOAD_BACKWARD);

            }
        });
        mListView.addFooterView(mListBtmRl);


        mPublicAdapter = new PublicAdapter(getActivity(), null, mListView, true, null);
        mPublicAdapter.setOnDataSetChangedListener(mDataSetChangedListener);
        mListView.setAdapter(mPublicAdapter);


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

        startMsgListQuery(TYPE_LOAD_FORWARD);

    }

    private final PublicAdapter.OnDataSetChangedListener mDataSetChangedListener =
            new PublicAdapter.OnDataSetChangedListener() {
                @Override
                public void onDataSetChanged(PublicAdapter adapter) {
                    // mPossiblePendingNotification = true;
                }

                @Override
                public void onContentChanged(PublicAdapter adapter) {
                    startMsgListQuery(TYPE_LOAD_FORWARD);
                }
            };

    private void startMsgListQuery(int type) {

        mBackgroundQueryHandler.cancelOperation(MESSAGE_LIST_QUERY_TOKEN);
        // mListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);

        Uri uri = DBInfo.PublicShow.CONTENT_URI;

        mBackgroundQueryHandler.startQuery(MESSAGE_LIST_QUERY_TOKEN, null, uri,
                DBInfo.PublicShow.COLUMNS, null, null, DBInfo.PublicShow.STAMP);

    }


    private final static class BackgroundQueryHandler
            extends AsyncWeakHandlerTemplate<PublicFragment> {
        public BackgroundQueryHandler(ContentResolver contentResolver) {
            super(contentResolver);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            final PublicFragment o = getObject();
            if (o == null) {
                return;
            }


            switch (token) {
                case MESSAGE_LIST_QUERY_TOKEN:

                    // Once we have completed the query for the message history, if
                    // there is nothing in the cursor and we are not composing a new
                    // message, we must be editing a draft in a new conversation
                    // (unless
                    // mSentMessage is true).
                    // Show the recipients editor to give the user a chance to add
                    // more people before the conversation begins.

                    // FIXME: freshing layout changes the focused view to an
                    // unexpected
                    // one, set it back to TextEditor forcely.
                    if (cursor != null)
                    Log.e("lmf", ">>>>>>>>>>>" + cursor.getCount());
                    o.mPublicAdapter.changeCursor(cursor);


                    break;

                default:
                    break;
            }

        }

    }

}
