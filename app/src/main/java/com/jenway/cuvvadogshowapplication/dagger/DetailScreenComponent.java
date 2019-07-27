package com.jenway.cuvvadogshowapplication.dagger;

import com.jenway.cuvvadogshowapplication.detailScreen.DetailScreenFragment;

import dagger.Component;


@MainScope
@Component(dependencies = AppComponent.class, modules = DetailScreenModule.class)
public interface DetailScreenComponent {
    void inject(DetailScreenFragment fragment);
}


