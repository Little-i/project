package utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    private int START_X = 0;
    private int START_Y = 0;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean canSlide = this.onInterceptTouchEvent(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                START_X = (int) ev.getX();
                START_Y = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int disX = endX - START_X;
                int disY = endY - START_Y;
                if (disX < disY){
                    canSlide = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                START_X = 0;
                START_Y = 0;
                canSlide = true;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
