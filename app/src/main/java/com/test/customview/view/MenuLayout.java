package com.test.customview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.test.customview.R;

/**
 * Created by hhj on 2018/2/24.
 */

public class MenuLayout extends RelativeLayout {
    MenuView menuView;
    ObjectAnimator animator;
    public MenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        menuView=new MenuView(context,attrs);
        menuView.setBackgroundResource(R.drawable.shape_bg);
        animator = ObjectAnimator.ofInt(menuView, "h", dip2px(getContext(),30), dip2px(getContext(),60));
        animator.setDuration(1000);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(menuView);
    }
    public void startAnim(){
        animator.start();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
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
