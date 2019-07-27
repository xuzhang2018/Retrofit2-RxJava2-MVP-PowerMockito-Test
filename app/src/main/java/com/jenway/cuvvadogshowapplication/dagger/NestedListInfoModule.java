package com.jenway.cuvvadogshowapplication.dagger;

import com.jenway.cuvvadogshowapplication.nestedList.mvp.NestedListInfoContract;
import com.jenway.cuvvadogshowapplication.nestedList.mvp.NestedListInfoPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class NestedListInfoModule {
    private NestedListInfoContract.View mView;

    public NestedListInfoModule(NestedListInfoContract.View mView) {
        this.mView = mView;
    }

    @MainScope
    @Provides
    public NestedListInfoPresenterImp provideNestedListInfoPresenterImp() {
        NestedListInfoPresenterImp imp = new NestedListInfoPresenterImp();
        imp.attachView(mView);
        return imp;
    }
}
