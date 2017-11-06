package com.example.xjl.message.CustomerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

/**
 * Created by xjl on 2017/10/27.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {
    private int ScreenHeight;
    private int ScreenWidht;
    private ViewGroup linearLayout;//直接子控件
    private ViewGroup mMenu;//Menu布局控件
    private ViewGroup mContent;//content布局控件
    private boolean b=true;
    public MyHorizontalScrollView(Context context) {
        this(context,null);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        ScreenHeight=outMetrics.heightPixels;
        ScreenWidht =outMetrics.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(b){
            linearLayout=(ViewGroup)this.getChildAt(0);
            mMenu=(ViewGroup)linearLayout.getChildAt(0);
            mContent=(ViewGroup)linearLayout.getChildAt(1);
            mMenu.getLayoutParams().width = ScreenWidht/8*5;
            mContent.getLayoutParams().width =ScreenWidht;
            b=false;
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        MyHorizontalScrollView.this.post(new Runnable() {
            @Override
            public void run() {
                MyHorizontalScrollView.this.scrollTo(mMenu.getLayoutParams().width,0);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_UP){
            int scrollX=getScrollX();
            if(scrollX>mMenu.getLayoutParams().width/2){
                this.smoothScrollTo(mMenu.getLayoutParams().width,0);
            }else {
                this.smoothScrollTo(0,0);
            }
            return true;
        }else {
            return super.onTouchEvent(ev);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mMenu.setTranslationX(l);
        mContent.setAlpha(1.0f-(0.2f-1.0f*l/(float)mMenu.getLayoutParams().width*1.0f+0.2f));
    }
}
