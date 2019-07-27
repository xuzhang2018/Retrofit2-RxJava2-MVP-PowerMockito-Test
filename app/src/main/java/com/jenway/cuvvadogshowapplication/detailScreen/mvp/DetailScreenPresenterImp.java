package com.jenway.cuvvadogshowapplication.detailScreen.mvp;

import android.view.LayoutInflater;

import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;

import java.util.ArrayList;

/**
 * by Xu
 * Description: handle the page view
 */
public class DetailScreenPresenterImp implements DetailScreenContract.Presenter {

    private DetailScreenContract.View mView;

    @Override
    public void loadBreedListData(LayoutInflater inflater, ArrayList<BaseEntity> data) {
        mView.showLoadingDialog("Loading the Images");
        try {
            mView.setPageView(data);
            mView.onLoadInfoSuccess();
        } catch (Exception e) {
            mView.onLoadInfoFail(e.getMessage());
        }

    }

    @Override
    public DetailScreenContract.Presenter attachView(DetailScreenContract.View view) {
        mView = view;
        return this;
    }

    @Override
    public void unSubscribe() {
        //to clear the RxJava
    }
}
