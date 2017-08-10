package com.chad.superdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chad
 * Time 17/6/22
 * Email: wuxianchuang@foxmail.com
 * Description: TODO
 */

public class VolumnBar extends View {

    private Paint mPaint;

    private int mCurrentVolumn = 10;

    @IntDef({0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VolumnType {
    }

    public VolumnBar(Context context) {
        super(context, null);
        init();
    }

    public VolumnBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public VolumnBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);

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
        canvas.drawColor(Color.TRANSPARENT);
        int interval = 10;
        int barWidth = (getMeasuredWidth() - interval * 9) / 10;
        int startX;
        int stopX;
        double alphas = 25.5;
        for (int i = 0; i < mCurrentVolumn; i++) {
            int alpha = (int) (alphas * (i + 1));
            mPaint.setAlpha(alpha);
            startX = (barWidth + interval) * i;
            stopX = startX + barWidth;
            canvas.drawRect(startX, 0, stopX, getMeasuredHeight(), mPaint);
        }


    }

    public void setVolumn(@VolumnType int volumn) {
        mCurrentVolumn = volumn;
        postInvalidate();
    }
}
