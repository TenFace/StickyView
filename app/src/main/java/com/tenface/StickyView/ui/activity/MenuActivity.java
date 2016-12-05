package com.tenface.StickyView.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.tenface.StickyView.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by TenFace on 16/11/30.
 */
public class MenuActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        String Token = "qybLFV5AV1sxBKepMUy6XZLVrxgWBHDyLg5c6la3wCFdvQg94sZ1PYJydZjL9WZpelUEcnMaJSzxGmSLskdEKg==";
/**
 23         * IMKit SDK调用第二步
 24         *
 25         * 建立与服务器的连接
 26         *
 27         */

        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
            }

            @Override
            public void onSuccess(String userId) {
                Log.e("MenuActivity", "——onSuccess—-" + userId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("MenuActivity", "——onError—-" + errorCode);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /**
         51         * 启动单聊
         52         * context - 应用上下文。
         53         * targetUserId - 要与之聊天的用户 Id。
         54         * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
         55         */
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startPrivateChat(MenuActivity.this, "2462", "hello");
        }


        return super.onOptionsItemSelected(item);
    }


    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token){

    }

}
