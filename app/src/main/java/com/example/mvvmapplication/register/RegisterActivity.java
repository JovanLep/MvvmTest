package com.example.mvvmapplication.register;

import android.os.Bundle;

import com.example.mvvmapplication.BR;
import com.example.mvvmapplication.R;
import com.example.mvvmapplication.databinding.ActivityRegisterBinding;
import com.zhen.mvvm.base.BaseActivity;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding,RegisterViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}