package cn.wintec.ldj;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.wintec.ldj.LabelPrinting;
import java.io.File;

/**
 * 此activity只是为了获取系统权限才能进行读取文件
 */
public class Main2Activity extends AppCompatActivity {

    private static Paint mPaint = new Paint();
    private        Rect  mRect   = new Rect(); //全局这么一个方形   为了计算控件高度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LabelPrinting  ceshi =  new LabelPrinting(this);
        ceshi.setPaperSize(777,777); //1 设置画画背景图

        int textSize=44; //2 文字大小
        mPaint.setTextSize(textSize);// 有了这个我们这些文字才有对应矩形的高度
        String text0="我00000000000000000000000000000000000000";  //3文字内容
        String text1="111111111111111111111111111111111111111";
        String text2="2222222222222222222222222222222222222222";
        String text3="3333333333333333333333333333333333333333333";
        mPaint.getTextBounds(text0,0,1,mRect);         //返回范围内文字（由调用方分配）最小的矩形 用来计算文字控件高度
        int h= mRect.height();    //4文字控件的高  20是修正用的
        System.out.println("hhhhhhhhhhhhh========="+mRect.height());

        ceshi.printText(text0,0,0,textSize,0);   //5画文字
        ceshi.printText(text1,0,0,textSize,1);
        ceshi.printText(text2,0,777-h,textSize,2);
        ceshi.printText(text3,777-h,0,textSize,3);


        int width=200,height=100;//1图片宽高            x y 是图片左上角在坐标系图纸中的位置。。
        Bitmap bitmapBarOrQR = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);  //指定好宽高的bitmap
        bitmapBarOrQR.eraseColor(Color.parseColor("#FF0000"));//3填充颜色
        Canvas canvas = new Canvas(bitmapBarOrQR);
        Paint paint = new Paint();
        paint .setTextSize(44);
        paint .setColor(Color.GREEN);
        paint .setFlags(1);
        paint .setStyle(Paint.Style.FILL);
        canvas.drawText("陆德健", 0, 44, paint ); //为了看清位置 在图片上画笔记了颜色    //以上都是初始化图片


        ceshi.printBarOrQR(bitmapBarOrQR,200,100,width,height,0);
        ceshi.printBarOrQR(bitmapBarOrQR,200,250,width,height,1);
        ceshi.printBarOrQR(bitmapBarOrQR,0,400,width,height,2);
        ceshi.printBarOrQR(bitmapBarOrQR,400,250,width,height,3);

        setContentView(ceshi);
        ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android
                .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1:
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    //创建文件夹
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File file = new File(Environment.getExternalStorageDirectory() + "/aa/bb/");
                        if (!file.exists()) {
                            Log.d("jim", "path1 create:" + file.mkdirs());
                        }
                    }
                    break;
                }
        }}
    }
