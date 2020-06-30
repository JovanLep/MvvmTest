package com.example.mvvmapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mvvmapplication.databinding.ActivityMainBinding;
import com.example.mvvmapplication.login.LoginActivity;

import com.zhen.mvvm.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setPresenter(new Presenter());
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    public class Presenter {
        public void onTextChangedTest(CharSequence charSequence, int i, int i1, int i2) {
            Log.e(TAG, "onTextChangedTest: " + charSequence.toString());
            viewModel.userName.set(charSequence.toString() + "---111");
        }

        public void onClickTestListenerBinding(MainViewModel model) {
            Log.e(TAG, "onClickTestListenerBinding: ");
            if (model == null) {
                Toast.makeText(MainActivity.this, "传入对象为空", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, model.userName.get(), Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

}