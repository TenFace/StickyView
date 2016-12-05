package com.tenface.StickyView.view.GameView;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tenface.StickyView.R;

/**
 * Created by TenFace on 2016/12/5.
 */

public class Card extends FrameLayout {


    public static int width;

    private TextView label;

    public Card(Context context) {
        super(context);

        LayoutParams lp = null;

        View background = new View(getContext());
        lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        background.setBackgroundColor(getResources().getColor(R.color._e84b4b));
        addView(background, lp);

        label = new TextView(getContext());
        label.setTextSize(28);
        label.setHintTextColor(getResources().getColor(R.color.home_text_1n_color));
        label.setTextColor(Color.WHITE);
        label.setGravity(Gravity.CENTER);

        lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);

        setNum(0);
    }


    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if (num<=0) {
            label.setText("");
        }else{
            label.setText(String.valueOf(num));

        }

        switch (num) {
            case 0:
                label.setBackgroundColor(getResources().getColor(R.color._e84b4b));
                break;
            case 2:
                label.setBackgroundColor(0xffeee4da);
                break;
            case 4:
                label.setBackgroundColor(0xffede0c8);
                break;
            case 8:
                label.setBackgroundColor(0xfff2b179);
                break;
            case 16:
                label.setBackgroundColor(0xfff59563);
                break;
            case 32:
                label.setBackgroundColor(0xfff67c5f);
                break;
            case 64:
                label.setBackgroundColor(0xfff65e3b);
                break;
            case 128:
                label.setBackgroundColor(0xffedcf72);
                break;
            case 256:
                label.setBackgroundColor(0xffedcc61);
                break;
            case 512:
                label.setBackgroundColor(0xffedc850);
                break;
            case 1024:
                label.setBackgroundColor(0xffedc53f);
                break;
            case 2048:
                label.setBackgroundColor(0xffedc22e);
                break;
            default:
                label.setBackgroundColor(getResources().getColor(R.color._e84b4b));
                break;
        }
    }

    public boolean equals(Card another) {
        return getNum()==another.getNum();
    }

    public TextView getLabel() {
        return label;
    }

    public void addScaleAnimation(){
        ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(500);
        setAnimation(null);
        getLabel().startAnimation(sa);
    }

}

