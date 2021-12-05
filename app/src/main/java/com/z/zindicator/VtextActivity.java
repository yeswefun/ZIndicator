package com.z.zindicator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;

/*
    文字指示器的演示
 */
public class VtextActivity extends AppCompatActivity {

    private Vtext mVtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vtext);
        mVtext = findViewById(R.id.vtext);
    }

    public void leftToRight(View view) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curPos = (float) animation.getAnimatedValue();
                mVtext.setDirection(true);
                mVtext.setCurPos(curPos);
            }
        });
        valueAnimator.start();
    }

    public void rightToLeft(View view) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curPos = (float) animation.getAnimatedValue();
                mVtext.setDirection(false);
                mVtext.setCurPos(curPos);
            }
        });
        valueAnimator.start();
    }
}