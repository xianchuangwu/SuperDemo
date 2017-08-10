package com.chad.superdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView clipImageView;
    private ImageView blurImageView;
    private ImageView blurImageView1;
    private ShaderView shaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clipImageView = (ImageView) findViewById(R.id.clip);
        blurImageView = (ImageView) findViewById(R.id.blur);
        blurImageView1 = (ImageView) findViewById(R.id.blur_bottom);
        clipImage();

        shaderView = (ShaderView) findViewById(R.id.shader);
    }

    public void viewDrag(View view) {
        Intent intent = new Intent(this, ViewDragActivity.class);
        startActivity(intent);
    }

    public void start(View view) {
        ((VideoRecoderButton) findViewById(R.id.recoder)).start();
    }

    public void notification(View view) {
        ((NewVideoNotificationView) view).playAnimation("1条新视频");
    }

    private void clipImage() {
        int screenWidth = Utils.getScreenWidth(this);
        int screenHeight = Utils.getScreenHeight(this);
        Bitmap bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.translate(screenWidth / 2, 0);

        int outerRight = Utils.dp2px(this, 100);
        int outerLeft = -outerRight;
        int outerTop = Utils.dp2px(this, 90);

        int margin = Utils.dp2px(this, 20);
        int insideLeft = outerLeft + margin;
        int insideTop = outerTop + margin;
        int insideRight = outerRight - margin;
        int insideBottom = insideRight * 2 + outerTop + margin;
        Path path1 = new Path();
        RectF rect1 = new RectF(insideLeft, insideTop, insideRight, insideBottom);
        path1.addRoundRect(rect1, 20, 20, Path.Direction.CW);
        canvas.clipPath(path1, Region.Op.XOR);

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.bitmap).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap scaleImage = Bitmap.createScaledBitmap(image, screenWidth, screenHeight, true);
        canvas.drawBitmap(scaleImage, -screenWidth / 2, 0, new Paint());

        clipImageView.setImageBitmap(bitmap);

    }

    public void blur(View view) {
        int width = blurImageView.getWidth();
        int height = blurImageView.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(width / 2, 0);

        float outerRight = width / 2;
        float outerLeft = -outerRight;
        float outerTop = 0;
        float outerBottom = height;
        Path path = new Path();
        RectF rect = new RectF(outerLeft, outerTop, outerRight, outerBottom);
        path.addRoundRect(rect, 40, 40, Path.Direction.CW);
        canvas.clipPath(path);

        float radiu = Utils.dp2px(this, 7f);
        float circleX = width / 2;
        float circleY = height * 4 / 5 + 4;
        Path path2 = new Path();
        path2.addCircle(-circleX, circleY, radiu, Path.Direction.CW);
        path2.addCircle(circleX, circleY, radiu, Path.Direction.CW);
        canvas.clipPath(path2, Region.Op.DIFFERENCE);

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.bitmap).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap scaleImage = Bitmap.createScaledBitmap(image, width, height * 2, true);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(scaleImage, -width / 2, 0, paint);

        blurImageView1.setImageBitmap(bitmap);

    }

    private int progress = 0;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            progress++;
            shaderView.setProgress(progress);
            if (progress != 100)
                handler.sendEmptyMessageDelayed(0, 200);
            return false;
        }
    });

    public void shader(View view) {
        progress = 0;
        handler.sendEmptyMessage(0);
    }


}
