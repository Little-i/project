package com.project.vp_fragment.recovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.project.R;

import java.util.ArrayList;
import java.util.List;

public class RecoveryFragment extends Fragment {
    private View rootView;
    private RecoveryRecyclerViewAdapter adapter;
    private List<Bean> beans;
    private SwipeRefreshLayout swipeRefreshLayout;

    public RecoveryFragment(){

    }

    public static RecoveryFragment newInstance(){
        return new RecoveryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_recovery,container,false);
        }
        swipeRefreshLayout = rootView.findViewById(R.id.recovery_swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        beans = new ArrayList<>();
        for (int i = 0; i <10; i++){
            Bean bean = new Bean();
            bean.setContent("广告" + i);
            beans.add(bean);
        }
        RecyclerView recyclerView = rootView.findViewById(R.id.recovery_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecoveryRecyclerViewAdapter(getActivity(),beans);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
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
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
