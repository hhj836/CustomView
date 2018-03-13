package com.test.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.test.customview.R;

/**
 * Created by hhj on 2018/2/28.
 */

public class VolumeView extends View {
    private int mCount=13;
    private int mSplitSize=15;
    private  int mCurrentCount=3;
    private int mCircleWidth=20;
    /**
    * 第一圈的颜色
    */
    private int mFirstColor;

    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Bitmap mImage;
    private Rect innerRect=new Rect();
    public VolumeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public VolumeView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        Log.d("VolumeView","初始化-00000");
        mImage= BitmapFactory.decodeResource(getResources(),R.drawable.main_my_party);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,R.styleable.VolumeView,defStyle,0);
        int n = a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.VolumeView_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.VolumeView_firstColor:
                    mFirstColor=a.getColor(attr,Color.BLACK);
                    break;
                case R.styleable.VolumeView_secondColor:
                    mSecondColor=a.getColor(attr,Color.RED);
                    break;
            }


        }

        paint.setAntiAlias(true);
        paint.setStrokeWidth(mCircleWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centre = getHeight()>getWidth()?getWidth() / 2:getHeight()/2; // 获取圆心的x坐标
        int radius = centre - mCircleWidth / 2;// 半径

        drawOval(canvas, centre, radius);
        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - mCircleWidth / 2;// 获得内圆的半径
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        innerRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        /**
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
         */
        innerRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        innerRect.bottom = (int) (innerRect.left + Math.sqrt(2) * relRadius)/2;
        innerRect.right = (int) (innerRect.left + Math.sqrt(2) * relRadius);

/**
 * 如果图片比较小，那么根据图片的尺寸放置到正中心
 */
        if (mImage!=null&&mImage.getWidth() < Math.sqrt(2) * relRadius)
        {
            innerRect.left = (int) (innerRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);
            innerRect.top = (int) (innerRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);
            innerRect.right = (int) (innerRect.left + mImage.getWidth());
            innerRect.bottom = (int) (innerRect.top + mImage.getHeight());

        }
        if(mImage!=null){
            // 绘图
            canvas.drawBitmap(mImage, null, innerRect, paint);
        }


    }
    private void drawOval(Canvas canvas, int centre, int radius)
    {
        /**
         * 根据需要画的个数以及间隙计算每个块块所占的比例*360
         */
        float itemSize = (270 * 1.0f - mCount * mSplitSize) / mCount;

        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
        Log.d("Practice01DrawTextView","left-"+oval.left);
        Log.d("Practice01DrawTextView","right-"+oval.right);
        Log.d("Practice01DrawTextView","top-"+oval.top);

        Log.d("Practice01DrawTextView","bottom-"+oval.bottom);


        paint.setColor(mFirstColor); // 设置圆环的颜色
        for (int i = 0; i < mCount; i++)
        {
            canvas.drawArc(oval, i * (itemSize + mSplitSize)-220, itemSize, false, paint); // 根据进度画圆弧
        }

        paint.setColor(mSecondColor); // 设置圆环的颜色
        for (int i = 0; i < mCurrentCount; i++)
        {
            canvas.drawArc(oval, i * (itemSize + mSplitSize)-220, itemSize, false, paint); // 根据进度画圆弧
        }
    }
    /**
     * 当前数量+1
     */
    public void up()
    {
        if(mCurrentCount>=mCount){
            return;
        }
        mCurrentCount++;
        postInvalidate();
    }

    /**
     * 当前数量-1
     */
    public void down()
    {
        mCurrentCount--;
        postInvalidate();
    }

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;

            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown)// 下滑
                {
                    down();
                } else
                {
                    up();
                }
                break;
        }

        return true;
    }
}
