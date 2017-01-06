package com.tenface.StickyView.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tenface.StickyView.R;

/**
 * Created by TenFace on 2016/12/5.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Button bt_login;
    private EditText et_id;
    private EditText et_password;
    private String test = "test";
    String Token = "qybLFV5AV1sxBKepMUy6XZLVrxgWBHDyLg5c6la3wCFdvQg94sZ1PYJydZjL9WZpelUEcnMaJSzxGmSLskdEKg==";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bt_login = (Button) findViewById(R.id.bt_login);
        et_id = (EditText) findViewById(R.id.et_id);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                connect(Token);
                Log.e(TAG, "Token:::::" + Token + "::::::::" + et_id.getText() + et_password.getText());
                if (et_id.getText().equals(test) && et_password.getText().equals(test)) {
                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this,MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
//
//    /**
//     * 建立与融云服务器的连接
//     *
//     * @param token
//     */
//    private void connect(String token) {
//
//        if (getApplicationInfo().packageName.equals(getApplicationContext())) {
//
//            /**
//             * IMKit SDK调用第二步,建立与服务器的连接
//             */
//            RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//                /**
//                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
//                 */
//                @Override
//                public void onTokenIncorrect() {
//
//                    Log.d("LoginActivity", "--onTokenIncorrect");
//                }
//
//                /**
//                 * 连接融云成功
//                 * @param userid 当前 token
//                 */
//                @Override
//                public void onSuccess(String userid) {
//
//                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
//                    finish();
//                }
//
//                /**
//                 * 连接融云失败
//                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
//                 */
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//
//                    Log.d("LoginActivity", "--onError" + errorCode);
//                }
//            });
//        }
//    }
}
