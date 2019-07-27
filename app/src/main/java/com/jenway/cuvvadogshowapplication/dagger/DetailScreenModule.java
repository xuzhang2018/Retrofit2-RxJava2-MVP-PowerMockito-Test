package com.jenway.cuvvadogshowapplication.dagger;

import com.jenway.cuvvadogshowapplication.detailScreen.mvp.DetailScreenContract;
import com.jenway.cuvvadogshowapplication.detailScreen.mvp.DetailScreenPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailScreenModule {
    private DetailScreenContract.View mView;

    public DetailScreenModule(DetailScreenContract.View mView) {
        this.mView = mView;
    }

    @MainScope
    @Provides
    public DetailScreenPresenterImp provideDetailScreenPresenterImp() {
        DetailScreenPresenterImp imp = new DetailScreenPresenterImp();
        imp.attachView(mView);
        return imp;
    }
}
