package utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.youth.banner.Banner;

/**
 * 必须重写含有(Context context,AttributeSet attrs)的构造器！
 * 不然会报错
 * Caused by: java.lang.NoSuchMethodException: <init> [class android.content.Context, interface android.util.AttributeSet]
 */
public class MyBanner  extends Banner {
    public MyBanner(Context context) {
        super(context);
    }

    public MyBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
