
package com.xian.xingyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xian.xingyu.R;

import java.util.List;


public class PublicAdapter extends BaseAdapter {

    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private List<Integer> mList;

    public PublicAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<Integer> getList() {
        return mList;
    }

    public void setList(List<Integer> mList) {
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

            convertView = mInflater.inflate(R.layout.item_public,
                    null, false);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.item_public_tv);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(mList.get(position) + "");

        return convertView;
    }

    private class ViewHolder
    {
        TextView text;
    }

}
