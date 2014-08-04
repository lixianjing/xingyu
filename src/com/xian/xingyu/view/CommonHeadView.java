
package com.xian.xingyu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xian.xingyu.R;

public class CommonHeadView extends RelativeLayout {

    private final Context mContext;
    private final TextView mTitleTv, mRightTv, mLeftTv;

    public CommonHeadView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public CommonHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public CommonHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mContext = context;

        LayoutInflater.from(context).inflate(R.layout.head, this);
        mTitleTv = (TextView) findViewById(R.id.head_title);
        mRightTv = (TextView) findViewById(R.id.head_right_tv);
        mLeftTv = (TextView) findViewById(R.id.head_left_tv);

    }

    public void setTitle(String text) {
        mTitleTv.setText(text);
    }

    public void setTitle(int res) {
        setTitle(mContext.getString(res));
    }

    public void setLeftText(String text, OnClickListener l) {
        mLeftTv.setText(text);
        if (l != null)
            mLeftTv.setOnClickListener(l);
        mLeftTv.setVisibility(View.VISIBLE);
    }

    public void setLeftText(int res, OnClickListener l) {
        setLeftText(mContext.getString(res), l);
    }

    public void setRightText(String text, OnClickListener l) {
        mRightTv.setText(text);
        if (l != null)
            mRightTv.setOnClickListener(l);
        mRightTv.setVisibility(View.VISIBLE);
    }

    public void setRightText(int res, OnClickListener l) {
        setRightText(mContext.getString(res), l);
    }

}
