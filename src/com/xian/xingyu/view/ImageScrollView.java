
package com.xian.xingyu.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.xian.xingyu.activity.MainActivity;
import com.xian.xingyu.util.BaseUtil;

public class ImageScrollView extends HorizontalScrollView implements View.OnClickListener {

    private static final int IMAGE_SIZE = 150;

    private final Context mContext;

    private final LinearLayout mLinearLayout;
    private Handler mainHandler;
    private String[] mPics;

    public ImageScrollView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public ImageScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        mLinearLayout = new LinearLayout(context);
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addView(mLinearLayout, params);

    }

    public void loadData(String[] pics, Handler handler) {
        this.mPics = pics;
        this.mainHandler = handler;

        int listSize = pics.length;
        int childCount = mLinearLayout.getChildCount();
        Log.e("lmf", ">>>>>>>11111>>>>>>>>>>>" + pics.length + ":" + mLinearLayout.getChildCount());
        if (childCount > listSize) {
            mLinearLayout.removeViewsInLayout(listSize, childCount - listSize);
        } else if (childCount < listSize) {
            for (int i = 0; i < listSize - childCount; i++) {
                ImageView iv = new ImageView(mContext);
                iv.setScaleType(ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(IMAGE_SIZE, IMAGE_SIZE);
                iv.setOnClickListener(this);
                mLinearLayout.addView(iv, params);
            }

        }

        for (int i = 0; i < pics.length; i++) {

            ImageView iv = (ImageView) mLinearLayout.getChildAt(i);

            Bitmap bitmap = BaseUtil.getBitmapFromName(mContext, pics[i]);
            iv.setImageBitmap(bitmap);

        }

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        mainHandler.sendEmptyMessage(MainActivity.MSG_START_ACTIVITY_IMAGE);
    }

}
