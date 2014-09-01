package com.xian.xingyu.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xian.xingyu.R;
import com.xian.xingyu.base.BaseActivity;
import com.xian.xingyu.view.CommonHeadView;

public class ImageActivity extends BaseActivity {

    private CommonHeadView mHeadView;
    private List<ImageView> mViewsList;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.image_activity);

        initTitle();


        mViewsList = new ArrayList<ImageView>();
        mViewPager = (ViewPager) findViewById(R.id.image_viewpager);


    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }

    private void initTitle() {
        mHeadView = (CommonHeadView) findViewById(R.id.head_common);
        mHeadView.setTitle("图片页面");
        mHeadView.setLeftText("返回", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });

        mHeadView.setRightText("下载", new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }


    public class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView(mViewsList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(mViewsList.get(position));
            return mViewsList.get(position);
        }



    }

}
