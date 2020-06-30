package com.zhen.mvvm.base;

import androidx.annotation.NonNull;

/**
 * ItemViewModel
 *
 * @author lep
 */
public class ItemViewModel<VM extends BaseViewModel> {
    protected VM viewModel;

    public ItemViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
