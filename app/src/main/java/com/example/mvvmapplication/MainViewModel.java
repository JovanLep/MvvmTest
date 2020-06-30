package com.example.mvvmapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhen.mvvm.base.BaseViewModel;

public class MainViewModel extends BaseViewModel<MainModel> {

    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        userName.set("6161561561");
    }
}
