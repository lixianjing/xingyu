package com.xian.xingyu.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.xian.xingyu.R;
import com.xian.xingyu.activity.ImageActivity;
import com.xian.xingyu.bean.FileDataInfo;
import com.xian.xingyu.util.BaseUtil;


public class ImageScrollView extends HorizontalScrollView implements View.OnClickListener {


    private static final int IMAGE_SIZE = 150;

    private Context mContext;

    private LinearLayout mLinearLayout;
    private List<FileDataInfo> mList;
    private Activity mActivity;



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


    public void loadData(List<FileDataInfo> list, Activity activity) {
        this.mList = list;
        this.mActivity = activity;

        int listSize = list.size();
        int childCount = mLinearLayout.getChildCount();
        Log.e("lmf", ">>>>>>>11111>>>>>>>>>>>" + list.size() + ":" + mLinearLayout.getChildCount());
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

        Log.e("lmf", ">>>>>>>22222>>>>>>>>>>>" + list.size() + ":" + mLinearLayout.getChildCount());

        for (int i = 0; i < list.size(); i++) {
            FileDataInfo dataInfo = list.get(i);

            ImageView iv = (ImageView) mLinearLayout.getChildAt(i);

            Bitmap bitmap = BaseUtil.getBitmapFromPath(dataInfo.getThumbUri());
            iv.setImageBitmap(bitmap);


        }

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        mActivity.startActivity(new Intent(mActivity, ImageActivity.class));
        mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }



    // public void addView() {
    // for (int i = 0; i < list.size(); i++) {
    // FileDataInfo dataInfo = list.get(i);
    //
    // ImageView iv = new ImageView(mContext);
    // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
    //
    // Bitmap bitmap = BaseUtil.getBitmapFromPath(dataInfo.getThumbUri());
    // iv.setImageBitmap(bitmap);
    //
    // holder.imageLl.addView(iv, i, params);
    //
    // }
    // }

}
