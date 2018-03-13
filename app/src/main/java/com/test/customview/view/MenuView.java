package com.test.customview.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by hhj on 2018/2/24.
 */

public class MenuView extends RelativeLayout {
    public final int DEFAULT_HEIGHT=dip2px(getContext(),40);
    public boolean isExpand;
    int padding=dip2px(getContext(),10);
    boolean isExpanding;
    public int getH() {
        return height;
    }

    public void setH(int h) {
        this.height = h;
      ViewGroup.LayoutParams layoutParams= getLayoutParams();
        layoutParams.height=h;
        setLayoutParams(layoutParams);
    }

    public int height=DEFAULT_HEIGHT;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width=getMeasuredWidth();

        for(int i=0;i<getChildCount();i++){
            View childView=getChildAt(i);
            int left=(width-childView.getMeasuredWidth())/2;
            int top=(DEFAULT_HEIGHT-childView.getMeasuredHeight())/2;
            Log.d("MenuView","getMeasuredWidth--"+childView.getMeasuredWidth());
            Log.d("MenuView","height--"+(height-DEFAULT_HEIGHT));
            int dy=height-DEFAULT_HEIGHT;
            switch (i){
                case 0:
                    dy=0;
                    break;
                case 1:
                    if(isExpanding){
                        dy= (int) ((dy+padding)/3.2f);
                    }else {
                        dy=dy/3;
                    }

                    break;
                case 2:
                    if(isExpanding){
                        dy= (int) ((dy+padding)/1.6f);
                        Log.d("MenuView","dy---"+dy);
                    }else {
                       dy= (int) ((dy)/1.5f);
                    }
                    break;
                case 3:
                    break;
            }
            childView.layout(left,top+dy,width-left,DEFAULT_HEIGHT-top+dy);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
        getChildAt(getChildCount()-1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }

    ObjectAnimator animator = ObjectAnimator.ofInt(MenuView.this, "h", dip2px(getContext(),40), dip2px(getContext(),130+padding*2));
    ObjectAnimator closeAnimator = ObjectAnimator.ofInt(MenuView.this, "h", dip2px(getContext(),130+padding*2), dip2px(getContext(),40));
    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        animator.setDuration(500);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isExpand=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isExpanding=false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        closeAnimator.setDuration(500);
       // closeAnimator.setInterpolator(new OvershootInterpolator());
        closeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isExpand=false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isExpanding=false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void startAnim(){
        if(isExpanding){
            return;
        }
        isExpanding=true;
        if(isExpand){
            closeAnimator.start();
        }else {
            animator.start();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        animate().setDuration(0).translationY((-height+DEFAULT_HEIGHT)/2);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        if(context==null){
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
