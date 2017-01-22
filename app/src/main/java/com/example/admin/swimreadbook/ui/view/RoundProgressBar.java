package com.example.admin.swimreadbook.ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.example.admin.swimreadbook.R;
import com.example.admin.swimreadbook.ui.activity.MainActivity;

/**
 * Created by admin on 2017/1/22.
 */
public class RoundProgressBar extends View {

    private TypedArray mTypedArray;
    /**
     * 圆环的颜色
     */
    private int Roundcolor;
    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;
    /**
     * 字体的颜色
     */
    private int textColor;
    /**
     * 进度百分笔字体的大小
     */
    private float textSize ;
    /**
     * 圆环的宽度
     */
    private float roundWidth;
    /**
     * 字体
     */
    private String mText;
    /**
     * 最大进度
     */
    private int mMax;

    /**
     * 是否显示中间的进度
     */
    private boolean mTextIsDisplayable;
    /**
     * 圆环进度的风格
     * 0 表示空心
     * 1 表示实心
     */
    private int mStyle;
    /**
     * 设置当前进度为0
     */
    private int progress = 100;

    /**
     * 画笔引用对象
     */
    private Paint mPaint;
    private int interval = 8;  //第二个圆环距离大圆外边缘的距离，根据ui自己调整
    private float lineWidth = 1;  //中间圆环的宽度

    public static final int STROKE = 0; //空心
    public static final int FILL = 1;  // 实心


    public RoundProgressBar(Context context) {
        this(context,null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        Roundcolor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.WHITE);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.WHITE);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.BLACK);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        roundWidth  = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth,8);
        mText = mTypedArray.getString(R.styleable.RoundProgressBar_text);
        mMax = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        mTextIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        mStyle = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

        mTypedArray.recycle();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = (int)(getWidth() - roundWidth) /2;

        //最大的圆
        int maxLayerX = getWidth() / 2;
//        mPaint.setColor(getResources().getColor(R.color.red));
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(5);
//        mPaint.setAntiAlias(true);
//        canvas.drawCircle(maxLayerX, maxLayerX,radius, mPaint);

//        //中间圆圈
//        mPaint.setColor(getResources().getColor(R.color.red));
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setAntiAlias(true);
//        mPaint.setStrokeWidth(1); //暂时写死在这里，根据自己项目调整该值大小，如果有需要也可写入attr中
//        canvas.drawCircle(maxLayerX, maxLayerX, maxLayerX - interval, mPaint); //画出圆环

        //中间文字
        mPaint.setColor(getResources().getColor(R.color.withe));
        mPaint.setTextSize(textSize);
        mPaint.setStrokeWidth(0);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextAlign(Paint.Align.CENTER);
        float textWidth = mPaint.measureText(mText);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        //确定文字的基准线
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(mText, maxLayerX, baseline, mPaint);

        // 设置进度是实心还是空心
        mPaint.setStrokeWidth(10); //设置圆环的宽度
        mPaint.setColor(getResources().getColor(R.color.red));  //设置进度的颜色
        mPaint.setStyle(Paint.Style.FILL);
        float displacement = interval + lineWidth * 2;

//        RectF oval = new RectF(maxLayerX - radius + displacement, maxLayerX - radius + displacement,
//                maxLayerX + radius - displacement, maxLayerX + radius - displacement);
        RectF oval = new RectF(maxLayerX - radius , maxLayerX - radius ,
                maxLayerX + radius , maxLayerX + radius ); //用于定义的圆弧的形状和大小的界限

        switch (mStyle){
            case STROKE:
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval,0,360 * progress / mMax, false, mPaint); //根据进度画圆弧
                break;
            case FILL:
                mPaint.setStyle(Paint.Style.FILL);
                if (progress != 0)
                    canvas.drawArc(oval,0,360 * progress / mMax, false, mPaint);
                break;

        }
    }

    private boolean isStop = false;
    public void start(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isStop) {
                    while (progress <= 100) {
                        progress -= 1;
                        setProgress(progress);
                        SystemClock.sleep(30);
                        if (progress == 0) {
                            isStop = true;
                            context.startActivity(new Intent(context, MainActivity.class));
                            break;
                        }
                    }
                }
            }
        }).start();
    }

    public void stop(){
        isStop = true;
        progress = 100;
    }

    public synchronized int getMax() {
        return mMax;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.mMax = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > mMax) {
            progress = mMax;
        }
        if (progress <= mMax) {
            this.progress = progress;
            postInvalidate();
        }
    }
}
