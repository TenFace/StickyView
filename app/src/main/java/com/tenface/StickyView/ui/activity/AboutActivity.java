package com.tenface.StickyView.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tenface.StickyView.R;
import com.tenface.StickyView.util.KickBackAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tenface on 16/11/30.
 */
public class AboutActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webView)
    WebView webView;


    private Context context = AboutActivity.this;
    private Handler mHandler = new Handler();
    private List<ImageView> list = new ArrayList<>();
    private Dialog dialog;

    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        initToolBar(toolbar, true, "关于 (V" + getVersionName(this) + ")");

        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); //如果访问的页面中有Javascript，则WebView必须设置支持Javascript
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮

        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }

        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); //自适应屏幕
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); //优先使用缓存

        webView.setHorizontalScrollbarOverlay(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        webView.requestFocus();

        webView.loadUrl("file:///android_asset/about.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    // 获取当前应用的版本号
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            String version = packInfo.versionName;
            if (!TextUtils.isEmpty(version)) {
                return version;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();//返回上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialog() {
        dialog = new Dialog(context, R.style.my_dialog_style);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
        final ImageView img01 = (ImageView) view.findViewById(R.id.img_01);
        final ImageView img02 = (ImageView) view.findViewById(R.id.img_02);
        final ImageView img03 = (ImageView) view.findViewById(R.id.img_03);
        // 这几个show和close的操作千万不要用集合循环去操作，否则在显示dialog时会出现dialog闪一下就消失的情况
        showAnim(img01, 150);
        showAnim(img02, 200);
        showAnim(img03, 250);
        Button button = (Button) view.findViewById(R.id.btn_dialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim(img03, 100, 380);
                closeAnim(img02, 150, 430);
                closeAnim(img01, 200, 480);
            }
        });

    }
    private void showAnim(final ImageView i, int d) {
        i.setVisibility(View.INVISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                i.setVisibility(View.VISIBLE);
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(i, "translationY", 1000, 0);
                fadeAnim.setDuration(600);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(600);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();
                fadeAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        i.clearAnimation();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {}
                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
            }
        },  d);
    }

    private void closeAnim(final ImageView img, int i, int j) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(img, "translationY", 0, 1000);
                fadeAnim.setDuration(600);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(600);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();
                fadeAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        img.setVisibility(View.INVISIBLE);
                        img.clearAnimation();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                });
            }
        }, i);
        if(img.getId() == R.id.img_01){
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, j);
        }
    }



}
