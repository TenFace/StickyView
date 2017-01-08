package com.tenface.StickyView.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.tenface.StickyView.R;
import com.tenface.StickyView.ui.fragment.LivePlayFragment;
import com.tenface.StickyView.ui.fragment.LivePushFragment;
import com.tenface.StickyView.view.SegmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TenFace on 2017/1/7.
 */
public class LiveActivity extends FragmentActivity implements SegmentView.OnSegmentChangeListener {


    private List<Fragment> fragments;

    private String infoId; //获取信息id
    private String infoTitle;    //获取信息标题
    private String infoDetails;    //获取信息详情
    private String infoUrl;
    private Intent intent;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getLiveIntent();
        initFragments();
        SegmentView sv = (SegmentView) findViewById(R.id.sv);
        sv.setOnSegmentChangeListener(this);
        sv.setSegmentSelected(0);

    }

    private void initFragments() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new LivePushFragment());
        LivePlayFragment livePlayFragment = new LivePlayFragment();
        livePlayFragment.setLive(false);
        fragments.add(livePlayFragment);
        livePlayFragment = new LivePlayFragment();
        livePlayFragment.setLive(true);
        fragments.add(livePlayFragment);

        Bundle bundle1 = new Bundle();
        if (infoUrl != null) {
            bundle1.putString("infoUrl", infoUrl);
            livePlayFragment.setArguments(bundle1);
        }
    }

    @Override
    public void onSegmentChange(int selectedIndex) {
        changeFragment(selectedIndex);
    }

    private void changeFragment(int selectedIndex) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, fragments.get(selectedIndex)).commit();
    }

    public void getLiveIntent() {
        intent = getIntent();
        infoId = intent.getStringExtra("infoId");
        infoTitle = intent.getStringExtra("infoTitle");
        infoDetails = intent.getStringExtra("infoDetails");
        infoUrl = intent.getStringExtra("infoUrl");
    }

}
