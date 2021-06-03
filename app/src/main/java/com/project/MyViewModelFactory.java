package com.project;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private final String name;
    private final String password;

    public MyViewModelFactory(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new MyViewModel(name,password);
    }
}
