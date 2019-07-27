package com.jenway.cuvvadogshowapplication.base;

public interface BaseView {
    void showLoadingDialog(String msg);

    void dismissLoadingDialog();

    void showToast(String message);
}
