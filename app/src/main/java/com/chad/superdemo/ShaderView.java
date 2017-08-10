package com.chad.superdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chad
 * Time 17/8/7
 * Email: wuxianchuang@foxmail.com
 * Description: TODO
 */

public class ShaderView extends View {

    private Paint mPaint;
    private Paint mpaint2;

    private float maxWidth;

    private float beforeWidth;

    private float toWidth;
    private int[] colorArr;
    private ValueAnimator valueAnimator;

    public ShaderView(Context context) {
        super(context, null);
    }


    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        colorArr = new int[]{Color.parseColor("#00e1ff"), Color.parseColor("#00a5ff")};
        mPaint = new Paint();
        maxWidth = Utils.getScreenWidth(getContext());

        mpaint2 = new Paint();
        mpaint2.setColor(Color.parseColor("#50ffffff"));
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
        canvas.drawRect(0, 0, beforeWidth, getMeasuredHeight(), mPaint);
        canvas.drawRect(beforeWidth - 10, 0, beforeWidth, getMeasuredHeight(), mpaint2);
    }

    public void setProgress(int progress) {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator = null;
        }
        toWidth = maxWidth * progress / 100;
        valueAnimator = ValueAnimator.ofFloat(beforeWidth, toWidth);
        valueAnimator.setDuration(300);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                beforeWidth = (float) animation.getAnimatedValue();
                // 梯度渲染
                Shader mLinearGradient = new LinearGradient(0, 0, beforeWidth, getMeasuredHeight(), colorArr, null, Shader.TileMode.REPEAT);
                // 绘制梯度渐变
                mPaint.setShader(mLinearGradient);
                postInvalidate();
            }
        });

    }
}
