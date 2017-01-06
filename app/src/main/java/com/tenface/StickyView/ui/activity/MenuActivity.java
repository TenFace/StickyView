package com.tenface.StickyView.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tenface.StickyView.R;
import com.tenface.StickyView.ui.fragment.FragmentInstanceManager;
import com.tenface.StickyView.ui.fragment.HomeFragment;
import com.tenface.StickyView.ui.fragment.IndentFragment;
import com.tenface.StickyView.ui.fragment.MessageFragment;
import com.tenface.StickyView.ui.fragment.TakeOutFragment;
import com.tenface.StickyView.ui.fragment.UserFragment;
import com.tenface.StickyView.view.X_SystemBarUI;

/**
 * Created by TenFace on 16/11/30.
 */
public class MenuActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mRadioGroup;
    private FragmentManager mFragmentManager;

    /**
     * 按下两次退出
     */
    private long exitTime = 0;

    @Override
    protected void onResume() {
        mRadioGroup = (RadioGroup) findViewById(R.id.group);
        mFragmentManager = getSupportFragmentManager();
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.check(R.id.main_footbar_home);
        X_SystemBarUI.initSystemBar(this, R.color.color0);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //切换到底部导航的Fragment
    public void switchNavigationFragment(int checkedId) {
        mRadioGroup.check(checkedId);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //首页
            case R.id.main_footbar_home:
                switchFragment(FragmentInstanceManager.getInstance().getFragment(HomeFragment.class));
                break;
            //收藏
            case R.id.main_footbar_takeout:
                switchFragment(FragmentInstanceManager.getInstance().getFragment(TakeOutFragment.class));
                break;
            //消息
            case R.id.main_footbar_indent:
                switchFragment(FragmentInstanceManager.getInstance().getFragment(IndentFragment.class));
                break;
            //信息
            case R.id.main_footbar_user:
                switchFragment(FragmentInstanceManager.getInstance().getFragment(MessageFragment.class));
                break;
            case R.id.main_user:
                switchFragment(FragmentInstanceManager.getInstance().getFragment(UserFragment.class));
                break;
            default:
                break;
        }
    }


    //提供方法切换Fragment，点击RadioButton的时候需要清空回退栈
    public void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //循环的的pop回退栈
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        while (backStackEntryCount > 0) {
            mFragmentManager.popBackStack();
            backStackEntryCount--;
        }

        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 0)//这里是取出我们返回栈存在Fragment的个数
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
        else
            getSupportFragmentManager().popBackStack();
        //取出我们返回栈保存的Fragment,这里会从栈顶开始弹栈
    }
}
