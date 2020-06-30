package com.example.mvvmapplication.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhen.mvvm.base.BaseViewModel;

public class LoginModel extends BaseViewModel {

    public ObservableField<String> msg=new ObservableField<>();

    public LoginModel(@NonNull Application application) {
        super(application);
        msg.set("311");
    }
}
