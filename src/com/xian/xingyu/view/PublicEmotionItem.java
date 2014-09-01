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

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xian.xingyu.R;
import com.xian.xingyu.bean.PublicItem;
import com.xian.xingyu.util.BaseUtil;

/**
 * This class provides view of a message in the messages list.
 */
@SuppressLint({
        "NewApi", "ResourceAsColor"
})
public class PublicEmotionItem extends LinearLayout implements View.OnClickListener {

    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Context mContext;
    private Handler mainHandler;

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
        // Clear all references to the message item, which can contain
        // attachments and other
        // memory-intensive objects
    }

    public PublicItem getMessageItem() {
        return mMessageItem;
    }

    private void bindCommonMessage(final PublicItem msgItem) {
        Log.e("lmf", ">>>>bindCommonMessage>>>>>11111>");
        long begin = System.currentTimeMillis();

        Bitmap bitmap = BaseUtil.getBitmapFromName(mContext, msgItem.userIcon);
        Log.e("lmf", ">>>>xxxxxx>>>>>>" + bitmap + ":" + msgItem.userIcon + ":"
                + (msgItem.hasPic && !TextUtils.isEmpty(msgItem.picUri)));
        if (bitmap != null) {
            iconIv.setImageBitmap(bitmap);
        }

        nameTv.setText(msgItem.userName);

        Date d1 = new Date(msgItem.stamp);
        String t1 = sFormat.format(d1);
        dateTv.setText(t1);

        imageHsv.setVisibility(View.GONE);
        if (msgItem.hasPic && !TextUtils.isEmpty(msgItem.picUri)) {

            String[] pic = msgItem.picUri.split(",");
            if (pic.length > 0) {
                imageHsv.setVisibility(View.VISIBLE);
                imageHsv.loadData(pic);

            }

        }

        contentTv.setText(msgItem.content);

        favCountTv.setText(msgItem.favCount + "");
        messageCountTv.setText(msgItem.commentCount + "");
        requestLayout();

        Log.e("lmf", ">>>>bindCommonMessage>>>>>222>" + (System.currentTimeMillis() - begin));
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

}
