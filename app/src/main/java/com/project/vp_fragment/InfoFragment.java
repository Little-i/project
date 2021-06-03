package com.project.vp_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.R;

import utils.ShowToast;

public class InfoFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public InfoFragment() {
        // Required empty public constructor
    }

    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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
        inflater.inflate(R.menu.menu_info,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_info, container, false);
        }
        CardView personalCv = rootView.findViewById(R.id.cardView_personal);
        CardView exerciseCv = rootView.findViewById(R.id.cardView_exercise);
        CardView settingsCv = rootView.findViewById(R.id.cardView_settings);
        CardView aboutCv = rootView.findViewById(R.id.cardView_about);
        personalCv.setOnClickListener(this);
        exerciseCv.setOnClickListener(this);
        settingsCv.setOnClickListener(this);
        aboutCv.setOnClickListener(this);
        return rootView;

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardView_personal:
                ShowToast.shortToast(getActivity(),"个人信息");
                break;
            case R.id.cardView_exercise:
                ShowToast.shortToast(getActivity(),"我的训练");
                break;
            case R.id.cardView_settings:
                ShowToast.shortToast(getActivity(),"设置");
                break;
            case R.id.cardView_about:
                ShowToast.shortToast(getActivity(),"关于");
                break;
        }
    }
}