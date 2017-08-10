package com.chad.superdemo;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by chad
 * Time 17/7/5
 * Email: wuxianchuang@foxmail.com
 * Description: TODO
 */

public class TestViewDragHelperLayout extends LinearLayout {

    private ViewDragHelper mDragger;

    public TestViewDragHelperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                return super.clampViewPositionHorizontal(child, left, dx);
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                return super.clampViewPositionVertical(child, top, dy);
                return top;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        mDragger.processTouchEvent(event);
        return true;
    }
}
