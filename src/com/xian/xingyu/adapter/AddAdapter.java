
package com.xian.xingyu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xian.xingyu.R;

import java.util.List;

public class AddAdapter extends BaseAdapter {

    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private List<Bitmap> mList;

    public AddAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<Bitmap> getList() {
        return mList;
    }

    public void setList(List<Bitmap> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item_add_gv,
                    null, false);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.item_add_iv);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iv.setImageBitmap(mList.get(position));

        return convertView;
    }

    private class ViewHolder
    {
        ImageView iv;
    }

}
