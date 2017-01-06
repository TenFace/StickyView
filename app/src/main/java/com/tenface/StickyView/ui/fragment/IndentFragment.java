package com.tenface.StickyView.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenface.StickyView.R;

/**
 * Created by TenFace on 2017/1/4.
 */

public class IndentFragment extends BaseFragment {
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indent,null);
        return view;
    }
}
