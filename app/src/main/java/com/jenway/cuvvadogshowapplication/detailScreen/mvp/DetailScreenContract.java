package com.jenway.cuvvadogshowapplication.detailScreen.mvp;

import android.view.LayoutInflater;

import com.jenway.cuvvadogshowapplication.base.BasePresenter;
import com.jenway.cuvvadogshowapplication.base.BaseView;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;

import java.util.ArrayList;

public interface DetailScreenContract {
    interface View extends BaseView {
        void setPageView(ArrayList<BaseEntity> data);

        void onLoadInfoSuccess();

        void onLoadInfoFail(String errorMsg);
    }

    interface Presenter extends BasePresenter<DetailScreenContract.Presenter, DetailScreenContract.View> {
        void loadBreedListData(LayoutInflater inflater, ArrayList<BaseEntity> data);
    }
}
