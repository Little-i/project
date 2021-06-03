package com.project.vp_fragment.forum;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.project.R;

import java.util.ArrayList;
import java.util.List;

import utils.ShowToast;

public class ForumFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ForumFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private ForumRecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance(String param1, String param2) {
        ForumFragment fragment = new ForumFragment();
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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
            Log.d(TAG, "onCreate: " + mParam1 + mParam2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_forum, container, false);
        }
        swipeRefreshLayout = rootView.findViewById(R.id.forum_swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        LinearLayout releaseTopic = rootView.findViewById(R.id.forum_releaseTopic);
        LinearLayout myTopic = rootView.findViewById(R.id.forum_myTopic);
        LinearLayout myMsg = rootView.findViewById(R.id.forum_myMsg);
        releaseTopic.setOnClickListener(this);
        myTopic.setOnClickListener(this);
        myMsg.setOnClickListener(this);
        List<Bean> beans = new ArrayList<>();
        StringBuilder content = new StringBuilder();
        for (int j = 0; j < 50; j++){
            content.append("Content");
        }
        for (int i = 0; i < 100; i++){
            Bean bean = new Bean();
            bean.setContent(content.toString());
            bean.setName("Ben");
            beans.add(bean);
        }
        RecyclerView recyclerView = rootView.findViewById(R.id.forum_rv);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        adapter = new ForumRecyclerViewAdapter(beans,getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerItemClickListener(new ForumRecyclerViewAdapter.onRecyclerItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                ShowToast.shortToast(getActivity(),"click content: " + position);
            }
        });
        return rootView;
    }

    private void refreshData(){
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
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forum_releaseTopic:
                ShowToast.shortToast(getActivity(),"发布话题");
                break;
            case R.id.forum_myTopic:
                ShowToast.shortToast(getActivity(),"我的话题");
                break;
            case R.id.forum_myMsg:
                ShowToast.shortToast(getActivity(),"我的消息");
                break;
        }
    }
}