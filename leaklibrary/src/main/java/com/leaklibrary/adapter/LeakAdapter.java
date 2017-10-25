package com.leaklibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leaklibrary.R;

import java.util.List;

/**
 * Created by guofeng
 * on 2017/10/23.
 */

public class LeakAdapter extends BaseAdapter {

    private List<LeakBean> data;

    private Context context;

    public LeakAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<LeakBean> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public LeakBean getItem(int i) {
        if (data != null) {
            if (i < data.size()) {
                return data.get(i);
            }
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.leak_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        LeakBean leakBean = getItem(i);
        if (leakBean != null) {
            holder.tvContent.setText(leakBean.leakReference);
            holder.tvDate.setText(leakBean.date);
        }
        return view;
    }

    private static class ViewHolder {

        TextView tvContent;
        TextView tvDate;

        private ViewHolder(View v) {
            this.tvContent = v.findViewById(R.id.tv_content);
            this.tvDate = v.findViewById(R.id.tv_date);
        }
    }


    public static class LeakBean {

        public String date;

        public String leakReference;

        public LeakBean(String leakReference, String date) {
            this.date = date;
            this.leakReference = leakReference;
        }
    }
}
