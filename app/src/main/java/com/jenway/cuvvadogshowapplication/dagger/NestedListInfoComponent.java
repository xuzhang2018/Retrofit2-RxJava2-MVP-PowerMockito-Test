package com.jenway.cuvvadogshowapplication.dagger;

import com.jenway.cuvvadogshowapplication.nestedList.NestedListFragment;

import dagger.Component;

@MainScope
@Component(dependencies = AppComponent.class, modules = NestedListInfoModule.class)
public interface NestedListInfoComponent {
    void inject(NestedListFragment fragment);
}
