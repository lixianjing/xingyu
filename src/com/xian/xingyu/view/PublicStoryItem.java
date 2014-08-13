/*
 * Copyright (C) 2008 Esmertec AG. Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.xian.xingyu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xian.xingyu.bean.PublicItem;

/**
 * This class provides view of a message in the messages list.
 */
@SuppressLint({"NewApi", "ResourceAsColor"})
public class PublicStoryItem extends LinearLayout implements View.OnClickListener {


    private Context mContext;

    private PublicItem mMessageItem;



    private TextView mTimeTv, mMessageTv;
    private FrameLayout mBtmFl;
    private LinearLayout mBtmBtnsLl;
    private RelativeLayout mContentLl1, mContentLl2;
    private Button mAllowBtn, mRejectBtn, mBlockBtn, mBtmOneBtn, mBtmTv, mTitleTv;


    private TextView mRoomName, mRoomLanguage, mRoomSubject;

    private TextView mPersonalName, mPersonalAge, mPersonalMood, mPersonalLine0, mPersonalLine1,
            tv_line_content1, tv_line_content2;
    private ImageView mIvSex, mIvRoom, mIvPersonal;




    public PublicStoryItem(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public PublicStoryItem(Context context, AttributeSet attrs) {
        super(context, attrs);

    }



    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        mTimeTv = (TextView) findViewById(R.id.msg_notice_time_tv);
        mMessageTv = (TextView) findViewById(R.id.msg_notice_message_tv);
        mTitleTv = (Button) findViewById(R.id.msg_notice_title_tv);

        mBtmFl = (FrameLayout) findViewById(R.id.msg_notice_btm_fl);
        mContentLl1 = (RelativeLayout) findViewById(R.id.msg_notice_content1);
        mContentLl2 = (RelativeLayout) findViewById(R.id.msg_notice_content2);

        mBtmBtnsLl = (LinearLayout) findViewById(R.id.msg_notice_btm_btns_ll);
        mAllowBtn = (Button) findViewById(R.id.msg_notice_btm_allow_btn);
        mRejectBtn = (Button) findViewById(R.id.msg_notice_btm_reject_btn);
        mBlockBtn = (Button) findViewById(R.id.msg_notice_btm_block_btn);
        mBtmOneBtn = (Button) findViewById(R.id.msg_notice_btm_btn);
        mBtmTv = (Button) findViewById(R.id.msg_notice_btm_tv);

        mRoomName = (TextView) findViewById(R.id.message_room_name);
        mRoomLanguage = (TextView) findViewById(R.id.message_language);
        mRoomSubject = (TextView) findViewById(R.id.message_subject);
        mIvRoom = (ImageView) findViewById(R.id.iv_room);


        mPersonalName = (TextView) findViewById(R.id.message_personal_name);
        mPersonalAge = (TextView) findViewById(R.id.message_personal_age);
        mPersonalMood = (TextView) findViewById(R.id.message_personal_mood);
        mIvSex = (ImageView) findViewById(R.id.iv_sex);
        mIvPersonal = (ImageView) findViewById(R.id.iv_personal);

        mPersonalLine0 = (TextView) findViewById(R.id.tv_line_personal0);
        mPersonalLine1 = (TextView) findViewById(R.id.tv_line_personal1);

        tv_line_content1 = (TextView) findViewById(R.id.tv_line_content1);
        tv_line_content2 = (TextView) findViewById(R.id.tv_line_content2);


        mAllowBtn.setOnClickListener(this);
        mRejectBtn.setOnClickListener(this);
        mBlockBtn.setOnClickListener(this);
        mBtmOneBtn.setOnClickListener(this);

        mContentLl1.setOnClickListener(this);
        mContentLl2.setOnClickListener(this);

    }

    public void bind(Context context, PublicItem msgItem) {
        mContext = context;
        mMessageItem = msgItem;
        setLongClickable(false);
        bindCommonMessage(msgItem);
    }

    public void unbind() {
        // Clear all references to the message item, which can contain attachments and other
        // memory-intensive objects
    }

    public PublicItem getMessageItem() {
        return mMessageItem;
    }


    private void bindCommonMessage(final PublicItem msgItem) {

        requestLayout();
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }



}
