package com.project;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.project.vp_fragment.HomeFragment;
import com.project.vp_fragment.InfoFragment;
import com.project.vp_fragment.MyFragmentAdapter;
import com.project.vp_fragment.forum.ForumFragment;
import com.project.vp_fragment.recovery.RecoveryFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import utils.ShowToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int START_X = 0;
    private int START_Y = 0;
    private boolean isExit = true;
    private ViewPager2 mVp2;
    private ImageView mIvHome, mIvRecovery, mIvForum, mIvMine, mIvCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVp2 = findViewById(R.id.vp);
        mIvHome = findViewById(R.id.iv_home);
        mIvRecovery = findViewById(R.id.iv_recovery);
        mIvForum = findViewById(R.id.iv_forum);
        mIvMine = findViewById(R.id.iv_mine);
        LinearLayout homeLayout = findViewById(R.id.ll_home);
        LinearLayout recoveryLayout = findViewById(R.id.ll_recovery);
        LinearLayout forumLayout = findViewById(R.id.ll_forum);
        LinearLayout mineLayout = findViewById(R.id.ll_mine);
        homeLayout.setOnClickListener(this);
        recoveryLayout.setOnClickListener(this);
        forumLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance("1", "2"));
        fragmentList.add(RecoveryFragment.newInstance());
        fragmentList.add(ForumFragment.newInstance("1", "2"));
        fragmentList.add(InfoFragment.newInstance("1", "2"));
        mVp2.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, getLifecycle()));
        mVp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pageChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        mIvHome.setSelected(true);
        mIvCurrent = mIvHome;
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        try {
            final Field recyclerViewField = ViewPager2.class.getDeclaredField("mRecyclerView");
            recyclerViewField.setAccessible(true);

            final RecyclerView recyclerView = (RecyclerView) recyclerViewField.get(mVp2);

            final Field touchSlopField = RecyclerView.class.getDeclaredField("mTouchSlop");
            touchSlopField.setAccessible(true);

            final int touchSlop = (int) touchSlopField.get(recyclerView);
            touchSlopField.set(recyclerView, touchSlop * 4);//6 is empirical value
        } catch (Exception ignore) {
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void pageChange(int position) {
        mIvCurrent.setSelected(false);
        switch (position) {
            case R.id.ll_home:
                mVp2.setCurrentItem(0);
            case 0:
                mIvHome.setSelected(true);
                mIvCurrent = mIvHome;
                break;
            case R.id.ll_recovery:
                mVp2.setCurrentItem(1);
            case 1:
                mIvRecovery.setSelected(true);
                mIvCurrent = mIvRecovery;
                break;
            case R.id.ll_forum:
                mVp2.setCurrentItem(2);
            case 2:
                mIvForum.setSelected(true);
                mIvCurrent = mIvForum;
                break;
            case R.id.ll_mine:
                mVp2.setCurrentItem(3);
            case 3:
                mIvMine.setSelected(true);
                mIvCurrent = mIvMine;
                break;

        }
    }

    @Override
    public void onClick(View v) {
        pageChange(v.getId());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                ShowToast.shortToast(this, "再按一次退出应用");
                isExit = false;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = true;
                    }
                }, 2000);
            } else {
                finish();
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean canSlide = mVp2.onInterceptTouchEvent(ev);
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