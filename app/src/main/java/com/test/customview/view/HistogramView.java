package com.test.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hhj on 2018/3/21.
 */

public class HistogramView extends View {
    int padding=50;
    String[] strs=new String[]{"Froyo","GB","ICS","JB","KitKat","L","M"};
    double[] percents=new double[]{0.01,0.05,0.1,0.3,0.7,0.9,0.5};
    int width=60;
    int maxHeight ;
    int  space=15;
    public HistogramView(Context context) {
        super(context);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        maxHeight=getHeight()-padding*2;
        float[] points=new float[]{padding,padding,padding,padding+maxHeight,padding,padding+maxHeight,getWidth()-padding,padding+maxHeight};
        canvas.drawLines(points,paint);
        paint.setTextSize(15);
        for(int i=0;i<strs.length;i++){
            paint.setColor(Color.GREEN);
            Rect rect=new Rect();
            rect.left=padding+space*(i+1)+width*i;
            rect.right=rect.left+width;
            rect.bottom=padding+maxHeight-1;
            rect.top= (int) (padding+maxHeight*(1-percents[i]));
            canvas.drawRect(rect,paint);
            paint.setColor(Color.WHITE);
          /*  Rect tRect=new Rect();
           paint.getTextBounds(strs[i],0,strs[i].length(),tRect);*/
            float h= (float) Math.ceil(paint.getFontMetrics().descent - paint.getFontMetrics().ascent);
            // float w=paint.measureText(strs[i]);
            // Log.e("Practice10HistogramView","tRect--left-"+w);
            Log.e("Practice10HistogramView","tRect--top-"+h);
            // float dx=width-w;
            // float tx=dx>0?rect.left+dx/2:rect.left;
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(strs[i],rect.left+(rect.right-rect.left)/2,padding+maxHeight+h,paint);

        }





//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
    }
}
