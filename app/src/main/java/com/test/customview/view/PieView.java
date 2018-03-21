package com.test.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by hhj on 2018/3/21.
 */

public class PieView extends View {
    double[] percents=new double[]{0.6,0.1,0.1,0.1,0.1};
    String[]  strs=new String[]{"1","2","3","4","5"};
    int[] colors=new int[]{Color.BLUE,Color.CYAN,Color.YELLOW,Color.GREEN,Color.GRAY,Color.BLACK};
    int radius=200;
    int lastAngle;
    //选中区偏移分离长度比例。原半径1/8
    int mSelectedOffset=8;
    int padding=50;
    int linePlane=40;
    int textMargin=5;
    private float mPosX = 0.0f;
    private float mPosY = 0.0f;
    private PointF mPointF = new PointF();
    private Paint linePaint;

    public PieView(Context context) {
        super(context);
        initPaint();
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    public void initPaint(){
        linePaint=new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(2);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radius=(getWidth()<getHeight()?getWidth()-padding*2:getHeight()-padding*2)/2;
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        int[] center=new int[]{getWidth()/2,getHeight()/2};
        RectF rect=new RectF();
        rect.left=center[0]-radius;
        rect.right=rect.left+radius*2;
        rect.top=center[1]-radius;
        rect.bottom=rect.top+radius*2;


        for(int i=0;i<strs.length;i++){
            paint.setColor(colors[i]);
            float  sweepAngle=(float) (360*percents[i]);
            float  calculateAngle=  add(lastAngle , sweepAngle/2f);
            if(i==0){

                //偏移圆心点位置
                float newRadius = div(radius , mSelectedOffset,10);
                //计算百分比标签
                PointF point =calcArcEndPointXY(getWidth()/2,getHeight()/2,
                        newRadius,calculateAngle
                );
                RectF rectF= initRectF(sub(point.x , radius),sub(point.y , radius),
                        add(point.x , radius),add(point.y , radius));
                canvas.drawArc(rectF,lastAngle,sweepAngle,true,paint);
                drawLineAndText(i,calculateAngle,canvas,radius+newRadius);

            }else {
                canvas.drawArc(rect,lastAngle,sweepAngle,true,paint);
                drawLineAndText(i,calculateAngle,canvas,radius);
            }

            lastAngle+=sweepAngle;
        }
    }

    public void  drawLineAndText(int position,float calculateAngle ,Canvas canvas,float radius){
        PointF  start=calcArcEndPointXY(getWidth()/2,getHeight()/2,
                radius,
                calculateAngle);
        PointF  startP=new PointF();
        startP.x=start.x;
        startP.y=start.y;
        PointF  end=calcArcEndPointXY(getWidth()/2,getHeight()/2, radius+20,
                calculateAngle);

        PointF  enP=new PointF();

        if(calculateAngle<=90f||calculateAngle>=270f){
            enP.x=end.x+linePlane;
            enP.y=end.y;
        }else {
            enP.x=end.x-linePlane;
            enP.y=end.y;
        }
        Path path=new Path();
        path.moveTo(startP.x,startP.y);
        path.lineTo(end.x,end.y);
        path.lineTo(enP.x,enP.y);
        canvas.drawPath(path,linePaint);

        linePaint.setStrokeWidth(1f);
        if(enP.x>end.x){
            linePaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(strs[position],enP.x+textMargin,enP.y,linePaint);
        }else {
            //float w=linePaint.measureText(strs[position]);
            linePaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(strs[position],enP.x-textMargin,enP.y,linePaint);
        }


     /*   canvas.drawLine(startP.x,startP.y,end.x,end.y,linePaint);
        canvas.drawLine(end.x,end.y,enP.x,enP.y,linePaint);*/

    }


    protected RectF initRectF(float left,float top,float right,float bottom)
    {
        return new RectF(left ,top,right,bottom);
    }
    /**
     * 除法运算,当除不尽时，精确到小数点后scale位
     * @param v1
     * @param v2
     * @param scale
     * @return 运算结果
     */
    public float div(float v1, float v2, int scale)
    {
        if (scale < 0)
            throw new IllegalArgumentException("The scale must be a positive integer or zero");

        if( Float.compare(v2, 0.0f) == 0)return 0.0f;

        BigDecimal bgNum1 = new BigDecimal(Float.toString(v1));
        BigDecimal bgNum2 = new BigDecimal(Float.toString(v2));
        return bgNum1.divide(bgNum2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    private void resetEndPointXY()
    {
        mPosX = mPosY = 0.0f;
        mPointF.x = mPosX;
        mPointF.y = mPosY;
    }
    //依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
    public PointF calcArcEndPointXY(float cirX,
                                    float cirY,
                                    float radius,
                                    float cirAngle)
    {
        resetEndPointXY();
        if( Float.compare(cirAngle, 0.0f) == 0 ||
                Float.compare(radius, 0.0f) == 0   )
        {
            return mPointF;
        }
        //Math.cos，sin参数为弧度
        float arcAngle = (float) (Math.PI *  div(cirAngle , 180.0f,10));
        mPosX = add(cirX , (float)Math.cos(arcAngle) * radius);
        mPosY = add(cirY , (float)Math.sin(arcAngle) * radius) ;
        mPointF.x = mPosX;
        mPointF.y = mPosY;
        return mPointF;


    }
    public float add(float v1, float v2)
    {
        BigDecimal bgNum1 = new BigDecimal(Float.toString(v1));
        BigDecimal bgNum2 = new BigDecimal(Float.toString(v2));
        return bgNum1.add(bgNum2).floatValue();
    }
    public float sub(float v1, float v2)
    {
        BigDecimal bgNum1 = new BigDecimal(Float.toString(v1));
        BigDecimal bgNum2 = new BigDecimal(Float.toString(v2));
        return bgNum1.subtract(bgNum2).floatValue();
    }
}
