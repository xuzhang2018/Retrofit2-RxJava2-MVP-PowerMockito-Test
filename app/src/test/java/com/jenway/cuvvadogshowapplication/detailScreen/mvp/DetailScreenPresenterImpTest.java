package com.jenway.cuvvadogshowapplication.detailScreen.mvp;

import android.view.LayoutInflater;

import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@PrepareForTest({DetailScreenPresenterImp.class})
@RunWith(PowerMockRunner.class)
public class DetailScreenPresenterImpTest {

    private DetailScreenPresenterImp presenterImp;
    private DetailScreenPresenterImp presenter;
    private DetailScreenContract.View view;

    @Before
    public void setUp() throws Exception {
        view = mock(DetailScreenContract.View.class);
        presenterImp = new DetailScreenPresenterImp();
        presenterImp.attachView(view);
        presenter = PowerMockito.spy(presenterImp);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_loadBreedListData_onSuccess() throws Exception {

        LayoutInflater inflater = PowerMockito.mock(LayoutInflater.class);
        ArrayList<BaseEntity> data = PowerMockito.mock(ArrayList.class);
        PowerMockito.doCallRealMethod().when(presenter).loadBreedListData(inflater, data);
        //
        presenter.loadBreedListData(inflater, data);
        verify(view).showLoadingDialog(anyString());
        verify(view).setPageView(any());
        verify(view).onLoadInfoSuccess();


    }
}