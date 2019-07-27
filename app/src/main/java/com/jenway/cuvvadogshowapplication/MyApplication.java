package com.jenway.cuvvadogshowapplication;

import android.app.Application;

import com.jenway.cuvvadogshowapplication.api.RetrofitService;
import com.jenway.cuvvadogshowapplication.dagger.AppComponent;
import com.jenway.cuvvadogshowapplication.dagger.AppModule;
import com.jenway.cuvvadogshowapplication.dagger.DaggerAppComponent;


public class MyApplication extends Application {

    /**
     * The constant DEBUG.
     */
    public static boolean DEBUG = false;
    private static MyApplication singleton;
    private static AppComponent appComponent;

    public static MyApplication getInstance() {
        return singleton;
    }
    //private RefWatcher mRefWatcher;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        InitInjector();
        InitConfig();
        if (BuildConfig.DEBUG) {
            DEBUG = true;
        }
    }

    /**
     * init the injection
     */
    private void InitInjector() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void InitConfig() {
        //init the LeakCanary
        //mRefWatcher = LeakCanary.install(this);
        //init the retrofit
        RetrofitService.init();
    }
    /**
     * monitor the memory
     */
//    public void mustDie(Object object) {
//        if (mRefWatcher != null) {
//            mRefWatcher.watch(object);
//        }
//    }


//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//    }
}
