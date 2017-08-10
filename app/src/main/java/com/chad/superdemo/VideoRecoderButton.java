package com.chad.superdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chad
 * Time 17/7/28
 * Email: wuxianchuang@foxmail.com
 * Description: TODO
 */

public class VideoRecoderButton extends View {

    private Paint mPaint;

    private float mRadius;
    private float mRadius1;
    private ValueAnimator valueAnimator;

    public VideoRecoderButton(Context context) {
        super(context, null);
        init();
    }

    public VideoRecoderButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public VideoRecoderButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    private boolean isRepeat;

    public void start() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator = null;
            mRadius = 0f;
            mRadius1 = 0f;
            invalidate();
        }
        else {
            valueAnimator = ValueAnimator.ofFloat(0f, 100f);
            valueAnimator.setDuration(1000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator.start();
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    if (isRepeat)
                        mRadius1 = Math.abs(value - 100f);
                    else
                        mRadius = value;
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    if (isRepeat) isRepeat = false;
                    else isRepeat = true;
                }
            });
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int measuredHeight, measuredWidth;
        int SIZE = 10;//控件默认大小

        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
        } else {
            measuredWidth = SIZE;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else {
            measuredHeight = SIZE;
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        int x = getMeasuredWidth() / 2;
        int y = getMeasuredHeight() / 2;

        if (isRepeat) {
            mPaint.setColor(getResources().getColor(R.color.red));
            canvas.drawCircle(x, y, mRadius, mPaint);
            mPaint.setColor(getResources().getColor(R.color.red_alpha));
            canvas.drawCircle(x, y, mRadius1, mPaint);
        } else {
            mPaint.setColor(getResources().getColor(R.color.red_alpha));
            canvas.drawCircle(x, y, mRadius1, mPaint);
            mPaint.setColor(getResources().getColor(R.color.red));
            canvas.drawCircle(x, y, mRadius, mPaint);
        }
    }
}
