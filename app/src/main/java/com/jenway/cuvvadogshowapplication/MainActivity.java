package com.jenway.cuvvadogshowapplication;

import android.os.Bundle;

import com.jenway.cuvvadogshowapplication.base.BaseActivity;
import com.jenway.cuvvadogshowapplication.base.BaseFragment;
import com.jenway.cuvvadogshowapplication.nestedList.NestedListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return NestedListFragment.newInstance("", "");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
