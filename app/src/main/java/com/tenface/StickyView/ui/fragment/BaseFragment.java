package com.tenface.StickyView.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.tenface.StickyView.R;
import com.tenface.StickyView.ui.activity.MenuActivity;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
	protected MenuActivity mMenuActivity;
	private View mViewRoot;
	private RelativeLayout relativeLayout;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mMenuActivity = (MenuActivity)activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//判断为空，为空就去加载布局，onCreateView在界面切换的时候会被多次调用,防止界面跳转回来的时候显示空白
		if (mViewRoot==null) {
			mViewRoot = createView(inflater, container, savedInstanceState);
			ButterKnife.bind(this, mViewRoot);
		}
		relativeLayout=(RelativeLayout) mMenuActivity.findViewById(R.id.layoutFooter);
		relativeLayout.setVisibility(View.VISIBLE);
		return mViewRoot;
	}
	
	
	//提供方法Fragment切换方法
	public void swichToChildFragment(Fragment fragment, boolean addToBackStack) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (addToBackStack) {
			//添加回退栈
			transaction.addToBackStack(fragment.getTag());
		}
		transaction.replace(R.id.fl_container, fragment);
		transaction.commit();
	}
	
	//切换到底部导航的Fragment
	public void switchNavigationFragment(int checkedId) {
		mMenuActivity.switchNavigationFragment(checkedId);
	}
	
	abstract protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);
	
	
	//当前的界面被切换出去的时候被调用,解决ViewGroup只有一个子View的bug
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mViewRoot!=null) {
			ViewParent parent = mViewRoot.getParent();
			if (parent instanceof ViewGroup) {
				ViewGroup viewGroup = (ViewGroup) parent;
				viewGroup.removeView(mViewRoot);
			}
		}
	}
	
	

}
