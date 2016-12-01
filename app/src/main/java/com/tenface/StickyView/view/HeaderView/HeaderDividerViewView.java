package com.tenface.StickyView.view.HeaderView;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.tenface.StickyView.R;

/**
 * Created by tenface on 16/11/30.
 */
public class HeaderDividerViewView extends HeaderViewInterface<String> {

    public HeaderDividerViewView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(String s, ListView listView) {
        View view = mInflate.inflate(R.layout.header_divider_layout, listView, false);
        listView.addHeaderView(view);
    }

}
