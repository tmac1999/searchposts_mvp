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
    public boolean onInterceptTouchEvent(MotionEvent ev) {

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
                //if (scrollX == 0) {//在最左端
                    if (downX - moveX < 0) {//右滑
                        return false;
                    }
                    System.out.println("滑动到了顶端 view.getScrollY()=" + scrollX);
               // }
                if ((scrollX + width) == scrollViewMeasuredHeight) {
                    System.out.println("滑动到了底部 scrollY=" + scrollX);
                    System.out.println("滑动到了底部 height=" + width);
                    System.out.println("滑动到了底部 scrollViewMeasuredHeight=" + scrollViewMeasuredHeight);
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

//        int left = getLeft();
//        int childleft = getChildAt(0).getLeft();
//        float x = getX();
//        float x1 = getChildAt(0).getX();
//        Log.d("SVVVVVVVVVVVVV", "left" + left + "childleft" + childleft + "x" + x + "childx" + x1);
        return super.onTouchEvent(ev);
    }
}
