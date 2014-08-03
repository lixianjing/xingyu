
package com.xian.xingyu.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.xian.xingyu.R;
import com.xian.xingyu.activity.PersonInfoActivity;
import com.xian.xingyu.activity.TestActivity;
import com.xian.xingyu.login.QQAccountManager;

/**
 * 自定义SlidingMenu 测拉菜单类
 */
public class DrawerView implements OnClickListener {

    private final Activity activity;
    private SlidingMenu localSlidingMenu;
    private Button leftBtn1, leftBtn2, leftBtn3, rightBtn1, rightBtn2;

    private FrameLayout leftLoginFl;
    private TextView leftLoginTv, leftLoginInfoTitleTv, leftLoginInfoContentTv;
    private LinearLayout leftLoginInfoLl;
    private ImageView leftLoginInfoIconIv;

    public DrawerView(Activity activity) {
        this.activity = activity;
    }

    public SlidingMenu initSlidingMenu() {
        localSlidingMenu = new SlidingMenu(activity);
        localSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// 设置左右滑菜单
        localSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);// 设置要使菜单滑动，触碰屏幕的范围
        localSlidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_MARGIN);// 设置了这个会获取不到菜单里面的焦点，所以先注释掉
        localSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);// 设置阴影图片的宽度
        localSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置阴影图片
        localSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// SlidingMenu划出时主页面显示的剩余宽度
        localSlidingMenu.setFadeDegree(0.35F);// SlidingMenu滑动时的渐变程度
        localSlidingMenu.attachToActivity(activity, SlidingMenu.RIGHT);// 使SlidingMenu附加在Activity右边
        // localSlidingMenu.setBehindWidthRes(R.dimen.left_drawer_avatar_size);//设置SlidingMenu菜单的宽度
        localSlidingMenu.setMenu(R.layout.slidingmenu_left);// 设置menu的布局文件
        // localSlidingMenu.toggle();//动态判断自动关闭或开启SlidingMenu
        localSlidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
        localSlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
        localSlidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {

            }
        });
        localSlidingMenu.setOnClosedListener(new OnClosedListener() {

            @Override
            public void onClosed() {
                // TODO Auto-generated method stub

            }
        });
        initView();
        return localSlidingMenu;
    }

    private void initView() {

        leftBtn1 = (Button) localSlidingMenu.findViewById(R.id.left_btn1);
        leftBtn2 = (Button) localSlidingMenu.findViewById(R.id.left_btn2);
        leftBtn3 = (Button) localSlidingMenu.findViewById(R.id.left_btn3);
        rightBtn1 = (Button) localSlidingMenu.findViewById(R.id.right_btn1);
        rightBtn2 = (Button) localSlidingMenu.findViewById(R.id.right_btn2);

        leftLoginFl = (FrameLayout) localSlidingMenu.findViewById(R.id.left_login_fl);
        leftLoginTv = (TextView) localSlidingMenu.findViewById(R.id.left_login_tv);
        leftLoginInfoLl = (LinearLayout) localSlidingMenu.findViewById(R.id.left_login_info_ll);

        leftLoginInfoTitleTv = (TextView) localSlidingMenu
                .findViewById(R.id.left_login_info_title_tv);
        leftLoginInfoContentTv = (TextView) localSlidingMenu
                .findViewById(R.id.left_login_info_content_tv);
        leftLoginInfoIconIv = (ImageView) localSlidingMenu
                .findViewById(R.id.left_login_info_icon_iv);

        leftBtn1.setOnClickListener(this);
        leftBtn2.setOnClickListener(this);
        leftBtn3.setOnClickListener(this);
        rightBtn1.setOnClickListener(this);
        rightBtn2.setOnClickListener(this);
        leftLoginFl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_login_fl:

                if (QQAccountManager.getInstance(activity.getApplicationContext()).isLogin()) {

                    Log.e("lmf", ">>>>>>>>>>login>>>>>>>>>>>");
                    activity.startActivity(new Intent(activity, PersonInfoActivity.class));
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Log.e("lmf", ">>>>>>>>>>not login>>>>>>>>>>>");
                    LoginDialog dialog = new LoginDialog(activity);

                    dialog.show();
                }

                break;
            case R.id.left_btn1:
                Log.e("lmf", ">>>>>>>>>>left_btn1>>>>>>>>>>>");
                localSlidingMenu.showContent(true);
                break;
            case R.id.left_btn2:
                Log.e("lmf", ">>>>>>>>>>left_btn2>>>>>>>>>>>");
                activity.startActivity(new Intent(activity, TestActivity.class));
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.left_btn3:
                Log.e("lmf", ">>>>>>>>>>left_btn3>>>>>>>>>>>");
                activity.startActivity(new Intent(activity, TestActivity.class));
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.right_btn1:
                Log.e("lmf", ">>>>>>>>>>right_btn1>>>>>>>>>>>");
                activity.startActivity(new Intent(activity, TestActivity.class));
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.right_btn2:
                Log.e("lmf", ">>>>>>>>>>right_btn1>>>>>>>>>>>");
                // activity.startActivity(new Intent(activity,
                // TestActivity.class));
                // activity.overridePendingTransition(R.anim.slide_in_right,
                // R.anim.slide_out_left);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("http://www.baidu.com"));
                activity.startActivity(intent);

                break;

            default:
                break;
        }
    }

    public void updateLoginStatus(boolean bool) {
        if (bool) {
            leftLoginInfoLl.setVisibility(View.VISIBLE);
            leftLoginTv.setVisibility(View.GONE);
        } else {
            leftLoginInfoLl.setVisibility(View.GONE);
            leftLoginTv.setVisibility(View.VISIBLE);
        }
    }

}
