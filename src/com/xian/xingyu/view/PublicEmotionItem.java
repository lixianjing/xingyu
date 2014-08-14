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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xian.xingyu.R;
import com.xian.xingyu.bean.PublicItem;

/**
 * This class provides view of a message in the messages list.
 */
@SuppressLint({"NewApi", "ResourceAsColor"})
public class PublicEmotionItem extends LinearLayout implements View.OnClickListener {

    private Context mContext;

    private PublicItem mMessageItem;

    private ImageView iconIv;
    private TextView dateTv, nameTv, contentTv, favCountTv, messageCountTv;
    private ImageScrollView imageHsv;

    public PublicEmotionItem(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public PublicEmotionItem(Context context, AttributeSet attrs) {
        super(context, attrs);

    }



    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        iconIv = (ImageView) findViewById(R.id.item_public_emotion_icon_iv);
        imageHsv = (ImageScrollView) findViewById(R.id.item_public_emotion_image_hsv);

        dateTv = (TextView) findViewById(R.id.item_public_emotion_date_tv);
        nameTv = (TextView) findViewById(R.id.item_public_emotion_name_tv);
        contentTv = (TextView) findViewById(R.id.item_public_emotion_content_tv);
        favCountTv = (TextView) findViewById(R.id.item_public_emotion_message_tv);
        messageCountTv = (TextView) findViewById(R.id.item_public_emotion_fav_tv);

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



        favCountTv.setText(msgItem.favCount + "");
        messageCountTv.setText(msgItem.commentCount + "");
        requestLayout();
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }



}
