package com.tenface.StickyView.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tenface.StickyView.R;

/**
 * Created by TenFace on 2016/12/1.
 */

public class TestActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = (TextView) findViewById(R.id.tv_test);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("img");
        textView.setText(str);
    }
}
