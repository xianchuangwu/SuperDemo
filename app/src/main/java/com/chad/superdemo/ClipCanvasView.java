package com.chad.superdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static android.R.attr.path;

/**
 * Created by chad
 * Time 17/8/1
 * Email: wuxianchuang@foxmail.com
 * Description: TODO
 */

public class ClipCanvasView extends View {

    private Paint paint;

    public ClipCanvasView(Context context) {
        super(context);
        init();
    }

    public ClipCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        两个clipRect和两个clipPath,分别是方形切割和path切割。如果要切割圆形，就用第二个也就是clipPath
//        clipPath(Path path)和clipPath(Path path, Region.Op op)

//        如果要切割区域，需要调用两次，第一次调用第一个方法，第二次第二个
        canvas.drawColor(Color.BLACK);
        canvas.save();
        canvas.translate(10, 10);
        canvas.clipRect(0, 0, 300, 300);
        //DIFFERENCE是第一次不同于第二次的部分显示出来A-B-------
        //REPLACE是显示第二次的B******
        //REVERSE_DIFFERENCE 是第二次不同于第一次的部分显示--------
        //INTERSECT交集显示A-(A-B)*******
        //UNION全部显示A+B******
        //XOR补集 就是全集的减去交集生育部分显示--------
        canvas.clipRect(200, 200, 400, 400, Region.Op.XOR);
        canvas.drawColor(Color.BLUE);
        canvas.restore();

//        先调用clip方法两次，确定切割区域
//        然后再调用普通的绘图方法，绘制图形
//        最后显示出来的就是clip方法确定的东西了。
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(10, 10);
        canvas.drawRect(0, 0, 300, 300, paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect(200, 200, 400, 400, paint);
        invalidate();
    }
}
