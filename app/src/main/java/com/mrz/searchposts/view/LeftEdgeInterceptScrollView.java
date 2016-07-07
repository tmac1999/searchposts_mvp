package com.mrz.searchposts.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by zhengpeng on 2016/6/30.
 */
public class LeftEdgeInterceptScrollView extends HorizontalScrollView {
    public LeftEdgeInterceptScrollView(Context context) {
        super(context);
    }

    public LeftEdgeInterceptScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LeftEdgeInterceptScrollView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    float downX = 0;


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        System.out.println("onTouchEvent=");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int scrollX = getScrollX();
                float moveX = ev.getX();
                int width = getWidth();
                int scrollViewMeasuredHeight = getChildAt(0).getMeasuredWidth();
                Log.d("EVENT", "downX" + downX + "moveX" + moveX + "downX - moveX" + (downX - moveX));
                if (scrollX == 0) {//在在最左端

                    if (downX - moveX < 0) {//右滑
                        System.out.println("在在最左端 右滑scrollX()=" + scrollX);
                        //requestDisallowInterceptTouchEvent(false) 需要拦截我的子控件  （父控件调用）
                        //getParent().requestDisallowInterceptTouchEvent(false) 我的父控件 请拦截我。
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
//                    System.out.println("滑动到了顶端 view.getScrollY()=" + scrollX);
                }
                if ((scrollX + width) == scrollViewMeasuredHeight) {
                    System.out.println("滑动到了底部 scrollY=" + scrollX);
                    System.out.println("滑动到了底部 height=" + width);
                    System.out.println("滑动到了底部 scrollViewMeasuredHeight=" + scrollViewMeasuredHeight);
                }
                break;
        }


        return super.onTouchEvent(ev);
    }
}
