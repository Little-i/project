package com.project.vp_fragment;

import android.annotation.SuppressLint;
import android.app.StatusBarManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.project.R;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import utils.MyBanner;
import utils.ShowToast;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private MyBanner banner;
    private SwipeRefreshLayout refreshLayout;
    private MyRecyclerViewAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_info, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        initView();
        List<Bean> beanList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Bean bean = new Bean();
            bean.setName("Content" + i);
            beanList.add(bean);
        }
        RecyclerView recyclerView = rootView.findViewById(R.id.home_rv);
        adapter = new MyRecyclerViewAdapter(beanList, getContext());
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.setRecyclerItemClickListener(new MyRecyclerViewAdapter.onRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {
                ShowToast.shortToast(getContext(), "click: " + position);
            }
        });
        LinearLayout taskItem = rootView.findViewById(R.id.ll_task);
        LinearLayout knowledgeItem = rootView.findViewById(R.id.ll_knowledge);
        LinearLayout answerItem = rootView.findViewById(R.id.ll_answer);
        LinearLayout doctorItem = rootView.findViewById(R.id.ll_doctor);
        taskItem.setOnClickListener(this);
        knowledgeItem.setOnClickListener(this);
        answerItem.setOnClickListener(this);
        doctorItem.setOnClickListener(this);
        refreshLayout = rootView.findViewById(R.id.swipeRefresh);
        refreshLayout.setColorSchemeResources(R.color.design_default_color_primary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        return rootView;
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    public void initView() {
        banner = rootView.findViewById(R.id.home_banner);
        List<Integer> listImages = new ArrayList<>();
        listImages.add(R.drawable.frame1);
        listImages.add(R.drawable.frame2);
        listImages.add(R.drawable.frame3);
        listImages.add(R.drawable.frame4);
        List<String> listTitles = new ArrayList<>();
        listTitles.add("item1");
        listTitles.add("item2");
        listTitles.add("item3");
        listTitles.add("item4");
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        }).setBannerAnimation(Transformer.Default).setImages(listImages)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setBannerTitles(listTitles).setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        ShowToast.shortToast(getContext(), "click: " + position);
                    }
                }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.stopAutoPlay();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_task:
            case R.id.ll_knowledge:
            case R.id.ll_answer:
            case R.id.ll_doctor:
                ShowToast.shortToast(getContext(), "敬请期待");
                break;
        }
    }
}