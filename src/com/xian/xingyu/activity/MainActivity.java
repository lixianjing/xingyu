
package com.xian.xingyu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.xian.xingyu.R;
import com.xian.xingyu.fragment.PrivateFragment;
import com.xian.xingyu.fragment.PublicFragment;
import com.xian.xingyu.view.DrawerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener {

    private FragmentManager mFragmentManager;
    private Context mContext;

    private Fragment mPublicFragment, mPrivateFragment;
    private List<Fragment> mFragmentList;

    private TextView mTitleLeftTv, mTitleRightTv;
    private RelativeLayout mTabRightRl, mTabLeftRl, mTabMiddleRl;
    private ViewPager mViewPager;
    private ImageView mPointIv;

    private SlidingMenu mSlidingMenu;

    int moveX; // 导航下面横线偏移宽度
    int width; // 导航下面比较粗的线的宽度
    int index; // 当前第一个view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mFragmentManager = getSupportFragmentManager();
        setContentView(R.layout.main);
        initView();
        initSlidingMenu();
        initListener();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.e("lmf", ">>>>>>>xxxxxxxxxx>>>>>>>>>>>>>>>");
        mSlidingMenu.showContent(false);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mTitleLeftTv = (TextView) findViewById(R.id.head_viewpage_left_tv);
        mTitleRightTv = (TextView) findViewById(R.id.head_viewpage_right_tv);
        mPointIv = (ImageView) findViewById(R.id.head_viewpage_iamge);

        mTabRightRl = (RelativeLayout) findViewById(R.id.tab_right_rl);
        mTabLeftRl = (RelativeLayout) findViewById(R.id.tab_left_rl);
        mTabMiddleRl = (RelativeLayout) findViewById(R.id.tab_middle_rl);

        mPublicFragment = new PublicFragment();
        mPrivateFragment = new PrivateFragment();

        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(mPublicFragment);
        mFragmentList.add(mPrivateFragment);

        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(new MyPageListener());

        DisplayMetrics dm = new DisplayMetrics(); // 获取手机分辨率
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        width = BitmapFactory.decodeResource(getResources(),
                R.drawable.viewpager_point).getWidth();// 获取图片宽度
        // 计算偏移量，也就是那条粗的下划线距离屏幕坐标的距离，如果有三个view则screeW/3，以此类推
        moveX = (screenW / 2 - width) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(moveX, 0);
        mPointIv.setImageMatrix(matrix); // 设置动画初始位置
    }

    private void initListener() {

        mTitleLeftTv.setOnClickListener(this);
        mTitleRightTv.setOnClickListener(this);

        mTabRightRl.setOnClickListener(this);
        mTabLeftRl.setOnClickListener(this);
        mTabMiddleRl.setOnClickListener(this);
    }

    private void initSlidingMenu() {
        mSlidingMenu = new DrawerView(this).initSlidingMenu();
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mSlidingMenu.isMenuShowing() || mSlidingMenu.isSecondaryMenuShowing()) {
                mSlidingMenu.showContent();
            } else {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, "在按一次退出", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        // 拦截MENU按钮点击事件，让他无任何操作
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.head_viewpage_left_tv:
                if (mSlidingMenu.isMenuShowing()) {
                    mSlidingMenu.showContent();
                } else {
                    mViewPager.setCurrentItem(0);
                }
                break;
            case R.id.head_viewpage_right_tv:
                if (mSlidingMenu.isMenuShowing()) {
                    mSlidingMenu.showContent();
                } else {
                    mViewPager.setCurrentItem(1);
                }
                break;

            case R.id.tab_left_rl:
                if (mSlidingMenu.isMenuShowing()) {
                    mSlidingMenu.showContent();
                } else {
                    mSlidingMenu.showMenu(true);
                }

                break;
            case R.id.tab_middle_rl:
                if (mSlidingMenu.isMenuShowing()) {
                    mSlidingMenu.showContent();
                } else {

                    Intent intent = new Intent(this, AddActivity.class);
                    mContext.startActivity(intent);
                    this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    Log.e("lmf", ">>>>>>>>>>>>>>edit>>>>>>>>>");
                }
                break;
            case R.id.tab_right_rl:
                if (mSlidingMenu.isMenuShowing()) {
                    mSlidingMenu.showContent();
                } else {
                    mSlidingMenu.showSecondaryMenu(true);
                }
                break;

            default:
                break;
        }
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            return mFragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

    }

    public class MyPageListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            int x = moveX * 2 + width; // 从第一个到第二个view，粗的下划线的偏移量
            /**
             * TranslateAnimation(float fromXDelta, float toXDelta, float
             * fromYDelta, float toYDelta) 　 float
             * fromXDelta:这个参数表示动画开始的点离当前View X坐标上的差值； float toXDelta,
             * 这个参数表示动画结束的点离当前View X坐标上的差值； float fromYDelta,
             * 这个参数表示动画开始的点离当前View Y坐标上的差值； float toYDelta)这个参数表示动画开始的点离当前View
             * Y坐标上的差值；
             */
            Log.v("index的值为:", index + "");
            Log.v("arg0的值为:", arg0 + "");
            Animation animation =
                    new TranslateAnimation(x * index, x * arg0, 0, 0);
            index = arg0;

            animation.setFillAfter(true); // 设置动画停止在结束位置
            animation.setDuration(300); // 设置动画时间
            mPointIv.startAnimation(animation); // 启动动画
        }
    }

}
