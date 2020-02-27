package cn.wintec.ldj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LabelPrinting extends View {
    /**
     * view 的 宽高
     */
    private static int    viewWidth  = 0;
    private static int    viewHeight = 0;
    //1 实例化bitmap对象(坐标系图纸,背景图片)
    private static Bitmap bgBitmap;
    //2 实例化canvs对象（画架,对应这个坐标系图纸）
    private static Canvas bgCanvas;
    //    //3 实例化paint对象（画具）、
    private static Paint  bgPaint    = new Paint();
    private  Rect mRect = new Rect(); //全局这么一个方形   为了计算控件高度

    public LabelPrinting(Context context) {
        this(context, null, 0);
    }

    public LabelPrinting(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelPrinting(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置纸张大小(坐标系图纸，用来画二维码条形码等内容)
     * 初始化画布
     *
     * @param width
     * @param height
     */
    public void setPaperSize(int width, int height) {
        bgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //        作用：以bitmap对象创建一个画布，将内容都绘制在bitmap上，因此bitmap不得为null。(构造方法)。
        bgCanvas = new Canvas(bgBitmap);
        //        view的宽高弄成跟我们坐标系图纸一样规格   不太好的感觉
        viewWidth = width;
        viewHeight = height;
    }

    /**
     * @param text      打印内容(看成一个控件有宽有高包围着)
     * @param x         在坐标系图纸的x轴相对位置
     * @param y         y轴相对位置
     * @param textSize  字体大小 sp好像
     * @param direction 打印方向 0:左→右,1：下→上,2:右→左(样子是左→右图标旋转180°),3:上→下
     */
    public void printText(String text, int x, int y, int textSize, int direction) {
        System.out.println("printTextprintTextprintText=========================");
        bgPaint.setTextSize(textSize);   //文字大小 sp好像
        bgPaint.setColor(Color.RED);     //文字颜色
        bgPaint.getTextBounds(text,0,1,mRect);         //返回范围内（由调用方分配）最小的矩形
        int h= mRect.height();   //文字控件的高 （用来计算坐标画文字）
        int w = (int) bgPaint.measureText(text); //文字控件的宽
//        double textToH = 1.4;  //根据文字大小来计算控件高度的基准
//        int h = (int) (textSize * textToH);  //文字控件的高 （用来计算坐标画文字）

        if (direction == 0) {
            bgCanvas.drawText(text, x,  y + h, bgPaint);
        } else if (direction == 1) {
            //******************************把文字位图 画上去
            Path path = new Path();
            //设置路径，以圆作为我们文本显示的路线  左下角开始
            path.moveTo(x + h, y + w);
            path.lineTo(x + h, y);

            //绘制出路径原型，方便后面比较
            bgCanvas.drawPath(path, bgPaint);
            //把文字绘制在要显示的路径上，默认不偏移
            bgCanvas.drawTextOnPath(text, path, 0, 0, bgPaint);
            //把文字绘制在要显示的路径上，路径起始点偏移150，中心垂直点偏移 50
            // 在画布上绘制旋转后的位图

        } else if (direction == 2) {
            Path path = new Path();
            //设置路径，以圆作为我们文本显示的路线  左下角开始
            path.moveTo(x + w, y);
            path.lineTo(x, y);
            //绘制出路径原型，方便后面比较
            bgCanvas.drawPath(path, bgPaint);
            //把文字绘制在要显示的路径上，默认不偏移
            bgCanvas.drawTextOnPath(text, path, 0, 0, bgPaint);
            //把文字绘制在要显示的路径上，路径起始点偏移150，中心垂直点偏移 50
            // 在画布上绘制旋转后的位图
        } else if (direction == 3) {
            Path path = new Path();
            //设置路径，以圆作为我们文本显示的路线  左下角开始
            path.moveTo(x, y);
            path.lineTo(x, y + w);
            //绘制出路径原型，方便后面比较
            bgCanvas.drawPath(path, bgPaint);
            //把文字绘制在要显示的路径上，默认不偏移
            bgCanvas.drawTextOnPath(text, path, 0, 0, bgPaint);
            //把文字绘制在要显示的路径上，路径起始点偏移150，中心垂直点偏移 50
            // 在画布上绘制旋转后的位图
        }
        //        mPaint.setTextAlign();
        //        bgCanvas.drawBitmap(bgBitmap, 0, 0, bgPaint);
        //    我们在自定义View时，当需要刷新View时，如果是在UI线程中，那就直接调用invalidate方法，如果是在非UI线程中，那就通过postInvalidate方法来刷新View
//        postInvalidate();

    }
    //接口3.打印条码。参数 坐标、条码宽度，条码高度，方向、内容
    //接口4.打印二维码。参数 坐标、QR码宽度、QR高度 方向、内容

    /**
     *
     * @param anyBitmap 条码，二维码等
     * @param x         坐标x
     * @param y         坐标y
     * @param width     条形码或者二维码的宽
     * @param height    条形码或者二维码的高
     * @param direction 打印方向 0:左→右,1：下→上,2:右→左(样子是左→右图标旋转180°),3:上→下
     */
    public void printBarOrQR(Bitmap anyBitmap, int x, int y, int width, int height, int direction) {
        System.out.println("printBarOrQRprintBarOrQRprintBarOrQR=========================");
        Matrix matrix1 = new Matrix();
        if (direction == 0) {
            //left  top
            bgCanvas.drawBitmap(anyBitmap, x, y, null);
        } else if (direction == 1) {
            matrix1.postRotate(-90, 0, 0);
            //        //平移
            matrix1.postTranslate(x, width + y);
            bgCanvas.drawBitmap(anyBitmap, matrix1, null);

        } else if (direction == 2) {
            matrix1.postRotate(180, 0, 0);
            //        //平移
            matrix1.postTranslate(x + width, y + height);
            bgCanvas.drawBitmap(anyBitmap, matrix1, null);

            //              bgCanvas.drawBitmap(type, x+height, y+width, null);
        } else if (direction == 3) {
            matrix1.postRotate(90, 0, 0);
            //        //平移
            matrix1.postTranslate(x + height, y);
            bgCanvas.drawBitmap(anyBitmap, matrix1, null);
        }

        //left  top
        //        invalidate();

    }
    //接口5.打印图片 参数 坐标 图片宽度、图片高度 方向、内容。

    /**
     * 打印图片
     *
     * @param x         坐标x
     * @param y         坐标y
     * @param width     图片宽度
     * @param height    图片高度
     * @param bitmap    bitmap(坐标系图纸)中画bitmap图片
     * @param direction 打印方向
     */
    public void printPicture(int x, int y, int width, int height, Bitmap bitmap, int direction) {
        invalidate();
    }

    //接口6开始打印 --输出图片
    public boolean printLabelPicture() {
        invalidate();
        return true;
    }

    /**
     * 将内容绘制到画板上
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //        super.onDraw(canvas);
        //        初始画布跟bgBitmap结合
        System.out.println("onDrawonDrawonDrawonDrawonDraw=========================");
        if (bgBitmap != null) {
            canvas.save();
            //            bgPaint.setColor(Color.GRAY);
            //决定bgBitmap图片在整个屏幕的位置0,0
            canvas.drawBitmap(bgBitmap, 0, 0, bgPaint);
            //            canvas.drawRect(new RectF(0,0,400,400), bgPaint);
            canvas.restore();
        }
    }

    /**
     * 将view  转成 图片
     *
     * @return
     */
    public Bitmap loadBitmapFromView() {
        Bitmap bmp = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        this.draw(c);
        return bmp;
    }

}
