package com.example.mvvmapplication;


import com.zhen.mvvm.base.BaseModel;

public class MainRepository extends BaseModel {
    private volatile static MainRepository INSTANCE = null;
    private MainRepository() {
    }

    public static MainRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (MainRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MainRepository();
                }
            }
        }
        return INSTANCE;
    }
}
