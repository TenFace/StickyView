package com.tenface.StickyView.view.HeaderView;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.tenface.StickyView.R;
import com.tenface.StickyView.adapter.HeaderChannelAdapter;
import com.tenface.StickyView.model.ChannelEntity;
import com.tenface.StickyView.view.FixedGridView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tenface on 16/11/30.
 */
public class HeaderChannelViewView extends HeaderViewInterface<List<ChannelEntity>> {

    @Bind(R.id.gv_channel)
    FixedGridView gvChannel;

    public HeaderChannelViewView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(List<ChannelEntity> list, ListView listView) {
        View view = mInflate.inflate(R.layout.header_channel_layout, listView, false);
        ButterKnife.bind(this, view);

        dealWithTheView(list);
        listView.addHeaderView(view);
    }

    private void dealWithTheView(List<ChannelEntity> list) {
        int size = list.size();

        if (size <= 4) {
            gvChannel.setNumColumns(size);
        } else if (size == 6) {
            gvChannel.setNumColumns(3);
        } else if (size == 8) {
            gvChannel.setNumColumns(4);
        } else {
            gvChannel.setNumColumns(4);
        }

        HeaderChannelAdapter adapter = new HeaderChannelAdapter(mContext, list);
        gvChannel.setAdapter(adapter);
    }

}