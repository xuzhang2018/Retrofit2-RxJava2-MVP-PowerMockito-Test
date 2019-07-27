package com.jenway.cuvvadogshowapplication.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jenway.cuvvadogshowapplication.MyApplication;
import com.jenway.cuvvadogshowapplication.dagger.AppComponent;

import javax.inject.Inject;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    @Inject
    protected P mPresenter;

    protected BaseActivity mActivity;

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    protected abstract void initInjector();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //in case, the mobile kill the activity
        this.mActivity = (BaseActivity) activity;
    }

    protected void addFragment(BaseFragment fragment) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment);
        }
    }

    protected void removeFragment() {
        getHoldingActivity().removeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initInjector();
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }

    protected AppComponent getAppComponent() {
        return MyApplication.getAppComponent();
    }
}