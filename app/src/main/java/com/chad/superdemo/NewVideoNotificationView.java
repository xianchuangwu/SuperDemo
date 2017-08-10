package com.chad.superdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

/**
 * Created by chad
 * Time 17/6/30
 * Email: wuxianchuang@foxmail.com
 * Description: TODO
 */

public class NewVideoNotificationView extends View {

    private Paint mPaint;

    private String mText;

    private int mTextAlpta;

    private boolean mShowTextContainer = false;

    private boolean mShowText = false;

    private static final int mTextSize = 40;

    private static final float mMaxRadius = 50;

    private float mCircleRadius;

    private float mCircleY;

    private float mRectWidth;
    private RectF rectF;
    private Paint textPaint;

    public NewVideoNotificationView(Context context) {
        super(context, null);
        init(context);
    }

    public NewVideoNotificationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public NewVideoNotificationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.circle));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        rectF = new RectF();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int measuredHeight, measuredWidth;
        int SIZE = (int) (mMaxRadius * 8);//控件默认大小

        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
        } else {
            measuredWidth = SIZE;
        }
//        if (heightMode == MeasureSpec.EXACTLY) {
//            measuredHeight = heightSize;
//        } else {
//            measuredHeight = SIZE;
//        }
        measuredHeight = SIZE;
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float screenWidthHalf = getMeasuredWidth() / 2;

        if (mShowTextContainer) {
            float left = screenWidthHalf - mCircleRadius - mRectWidth / 2;
            float top = mCircleY - mCircleRadius;
            float right = screenWidthHalf + mCircleRadius + mRectWidth / 2;
            float bottom = mCircleY + mCircleRadius;
            rectF.set(left, top, right, bottom);
            canvas.drawRoundRect(rectF, mCircleRadius, mCircleRadius, mPaint);

            if (mShowText) {
                textPaint.setAlpha(mTextAlpta);
                textPaint.setTextSize(mTextSize);
                textPaint.measureText(mText);
                canvas.drawText(mText, screenWidthHalf, mCircleY + mTextSize / 2, textPaint);
            }
        } else {
            Log.d("onDraw", "mCircleY :" + mCircleY + "\nmCircleRadius :" + mCircleRadius);
            canvas.drawCircle(screenWidthHalf, mCircleY, mCircleRadius, mPaint);
        }

    }

    public void playAnimation(final String text) {

        mShowTextContainer = false;
        mShowText = false;
        mText = text;

        //圆半径动画
        ValueAnimator radius = ValueAnimator.ofFloat(0, mMaxRadius);
        radius.setInterpolator(new AccelerateDecelerateInterpolator());
        radius.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mCircleRadius = value;
                mCircleY = value;
                postInvalidate();
            }
        });

        //圆形坐标动画
        final ValueAnimator coordinate = ValueAnimator.ofFloat(0, mMaxRadius * 6);
        coordinate.setInterpolator(new BounceInterpolator());
        coordinate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleY = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        final ValueAnimator rect = ValueAnimator.ofFloat(0, mMaxRadius * 4);
        rect.setInterpolator(new BounceInterpolator());
        rect.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mShowTextContainer = true;

                mRectWidth = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator alpha = ValueAnimator.ofInt(0, 255);
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mShowText = true;

                mTextAlpta = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        final AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.play(rect).with(alpha);
        animatorSet1.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(radius).with(coordinate);
        animatorSet.setDuration(1500);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet1.start();

            }
        });
    }
}
