package com.example.mvvmapplication.login;

import android.os.Bundle;

import com.example.mvvmapplication.BR;
import com.example.mvvmapplication.R;
import com.example.mvvmapplication.databinding.ActivityLoginBinding;

import com.zhen.mvvm.base.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}