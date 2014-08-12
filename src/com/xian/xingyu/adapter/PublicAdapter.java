
package com.xian.xingyu.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xian.xingyu.R;
import com.xian.xingyu.bean.EmotionInfo;
import com.xian.xingyu.bean.FileDataInfo;
import com.xian.xingyu.db.DBInfo.Emotion;
import com.xian.xingyu.view.ImageScrollView;

public class PublicAdapter extends BaseAdapter {

    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Activity mActivity;
    private LayoutInflater mInflater = null;
    private List<EmotionInfo> mList;

    public PublicAdapter(Activity context) {
        mActivity = context;
        mInflater = LayoutInflater.from(context);
    }

    public List<EmotionInfo> getList() {
        return mList;
    }

    public void setList(List<EmotionInfo> mList) {
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

            convertView = mInflater.inflate(R.layout.item_private, null, false);
            holder = new ViewHolder();

            holder.dateTv = (TextView) convertView.findViewById(R.id.item_private_date_tv);
            holder.timeTv = (TextView) convertView.findViewById(R.id.item_private_time_tv);
            holder.contentTv = (TextView) convertView.findViewById(R.id.item_private_content_tv);
            holder.typeTv = (TextView) convertView.findViewById(R.id.item_private_type_tv);
            holder.imageHsv =
                    (ImageScrollView) convertView.findViewById(R.id.item_private_image_hsv);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EmotionInfo info = mList.get(position);

        Date d1 = new Date(info.getStamp());
        String t1 = sFormat.format(d1);
        String[] dateStr = t1.split(" ");
        holder.dateTv.setText(dateStr[0]);
        holder.timeTv.setText(dateStr[1]);
        holder.contentTv.setText(info.getContent());

        if (info.getType() == Emotion.TYPE_PRIVATE) {
            holder.typeTv.setText("私密");
        } else {
            holder.typeTv.setText("公开");
        }
        holder.imageHsv.setVisibility(View.GONE);
        if (info.isHasPic()) {
            List<FileDataInfo> list = info.getFileDateList();
            if (list != null && list.size() > 0) {
                holder.imageHsv.setVisibility(View.VISIBLE);
                holder.imageHsv.loadData(list, mActivity);

            }
        }

        return convertView;
    }

    private class ViewHolder {
        TextView dateTv, timeTv, contentTv, typeTv;
        ImageScrollView imageHsv;

    }

}
