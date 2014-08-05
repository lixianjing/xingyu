package com.xian.xingyu.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.xian.xingyu.R;

public class LoadingDialog extends Dialog {

    private final Context mContext;
    private TextView mLoadingTv;

    public LoadingDialog(Context context) {
        super(context, R.style.MyDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_loading);
        this.setCancelable(false);
        mLoadingTv = (TextView) findViewById(R.id.dialog_loading_tv);


    }

    public void setText(String text) {
        mLoadingTv.setText(text);
    }

    public void setText(int res) {
        mLoadingTv.setText(mContext.getText(res));
    }

}
