package com.xian.xingyu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
        mTitleTv.setText(mContext.getString(res));
    }


}
