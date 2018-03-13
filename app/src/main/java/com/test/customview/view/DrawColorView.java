package com.test.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.test.customview.R;

/**
 * Created by hhj on 2018/1/4.
 */

public class DrawColorView extends View {
    private int imgWidth=400;
    public DrawColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.login_sp);
        Rect src=new Rect(0, 0, imgWidth,imgWidth);
        int left=getWidth()/2-imgWidth/2;
        int top=getHeight()/2-imgWidth/2;
        Rect dst=new Rect(left, top, left+imgWidth,top+imgWidth);
        canvas.drawBitmap(bitmap,src,dst,new Paint());

    }
}
