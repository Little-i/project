package com.project;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    public String name;
    public String password;

    public MyViewModel(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
