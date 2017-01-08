package com.tenface.StickyView.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.TextView;

import com.tenface.StickyView.R;

/**
 * Created by TenFace on 2017/1/7.
 */
public class MenuLiveListView extends ListView {

    private TextView titleView1;

    private TextView titleView2;

    private TextView titleView3;

    private TextView titleView4;

    private ListView listView;

    public MenuLiveListView(Context context) {
        super(context);
        initView();
    }

    public MenuLiveListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public MenuLiveListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }
    private void initView(){
        inflate(getContext(), R.layout.menu_live_listview,this);

    }
}
