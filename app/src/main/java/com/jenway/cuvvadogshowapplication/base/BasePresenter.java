package com.jenway.cuvvadogshowapplication.base;

public interface BasePresenter<K extends BasePresenter, T extends BaseView> {
    K attachView(T view);

    void unSubscribe();
}