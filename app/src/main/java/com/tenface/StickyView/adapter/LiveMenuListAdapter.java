package com.tenface.StickyView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tenface.StickyView.R;
import com.tenface.StickyView.model.City;

import java.util.List;

/**
 * Created by TenFace on 2017/1/7.
 */
public class LiveMenuListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private View[] liveList;

    public LiveMenuListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return liveList.length;
    }

    @Override
    public Object getItem(int position) {
        return liveList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null)
            return liveList[position];
        return convertView;
    }

}
