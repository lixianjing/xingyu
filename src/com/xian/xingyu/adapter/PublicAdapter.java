/*
 * Copyright (C) 2010~2014 limingfeng
 *
 * This file is a part of limingfeng apps.
 *
 * All rights reserved.
 */

package com.xian.xingyu.adapter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.xian.xingyu.R;
import com.xian.xingyu.bean.PublicItem;
import com.xian.xingyu.db.DBInfo;
import com.xian.xingyu.view.PublicEmotionItem;

/**
 * The back-end data adapter of a message list.
 */
public class PublicAdapter extends CursorAdapter {
    private static final String TAG = "PublicAdapter";


    private static final int CACHE_SIZE = 20;

    protected LayoutInflater mInflater;
    private final LinkedHashMap<Long, PublicItem> mMessageItemCache;
    private OnDataSetChangedListener mOnDataSetChangedListener;

    private Context mContext;



    public PublicAdapter(Context context, Cursor c, ListView listView,
            boolean useDefaultColumnsMap, Pattern highlight) {
        super(context, c, false /* auto-requery */);
        mContext = context;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMessageItemCache = new LinkedHashMap<Long, PublicItem>(10, 1.0f, true) {
            private static final long serialVersionUID = 0;

            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, PublicItem> eldest) {
                return size() > CACHE_SIZE;
            }
        };


        listView.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                if (view instanceof PublicEmotionItem) {
                    PublicEmotionItem mli = (PublicEmotionItem) view;
                    // Clear references to resources
                    mli.unbind();
                }
            }
        });
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (view instanceof PublicEmotionItem) {
            long recordId = cursor.getLong(DBInfo.PublicShow.INDEX_ID);

            PublicItem msgItem = getCachedMessageItem(recordId, cursor);
            if (msgItem != null) {
                PublicEmotionItem mli = (PublicEmotionItem) view;
                mli.setAdapter(PublicAdapter.this);
                mli.bind(mContext, msgItem);
            }
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2; // Incoming and outgoing messages
    }

    @Override
    public int getItemViewType(int position) {
        Cursor cursor = (Cursor) getItem(position);
        return getItemViewType(cursor);
    }

    private int getItemViewType(Cursor cursor) {
        return cursor.getInt(DBInfo.PublicShow.INDEX_TYPE);
    }

    public interface OnDataSetChangedListener {
        void onDataSetChanged(PublicAdapter adapter);

        void onContentChanged(PublicAdapter adapter);
    }

    public void setOnDataSetChangedListener(OnDataSetChangedListener l) {
        mOnDataSetChangedListener = l;
    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        mMessageItemCache.clear();

        if (mOnDataSetChangedListener != null) {
            mOnDataSetChangedListener.onDataSetChanged(this);
        }


    }

    @Override
    public void changeCursor(Cursor cursor) {
        // mHasLastEms = false;
        super.changeCursor(cursor);
    }

    @Override
    protected void onContentChanged() {
        if (getCursor() != null && !getCursor().isClosed()) {
            if (mOnDataSetChangedListener != null) {
                mOnDataSetChangedListener.onContentChanged(this);
            }
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view =
                mInflater.inflate(getItemViewType(cursor) == DBInfo.PublicShow.TYPE_EMOTION
                        ? R.layout.item_public_emotion
                        : R.layout.item_public_story, parent, false);

        view.setWillNotCacheDrawing(true);
        view.setDrawingCacheEnabled(false);

        return view;

    }

    public PublicItem getCachedMessageItem(long recordId, Cursor c) {
        PublicItem item = mMessageItemCache.get(recordId);
        if (item == null && c != null && isCursorValid(c)) {
            item = new PublicItem(mContext, c);
            mMessageItemCache.put(recordId, item);
        }

        return item;
    }

    private boolean isCursorValid(Cursor cursor) {
        // Check whether the cursor is valid or not.
        if (cursor.isClosed() || cursor.isBeforeFirst() || cursor.isAfterLast()) {
            return false;
        }
        return true;
    }



}
