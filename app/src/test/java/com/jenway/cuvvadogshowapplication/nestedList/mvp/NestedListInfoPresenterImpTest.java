package com.jenway.cuvvadogshowapplication.nestedList.mvp;

import android.graphics.Bitmap;

import com.jenway.cuvvadogshowapplication.api.RetrofitService;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;
import com.jenway.cuvvadogshowapplication.model.entity.BreedInfo;
import com.jenway.cuvvadogshowapplication.model.entity.SubBreedInfo;
import com.jenway.cuvvadogshowapplication.network.NetData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * by Xu
 * Description: only test the MVP functions, the other function test is in Android Test folder
 */

@PrepareForTest({NestedListInfoPresenterImp.class, RetrofitService.class})
@RunWith(PowerMockRunner.class)
public class NestedListInfoPresenterImpTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();


    private NestedListInfoContract.View view;
    private NestedListInfoPresenterImp presenterImp;
    private NestedListInfoPresenterImp presenter;


    private ArrayList<BaseEntity> fulllist;
    private ArrayList<BaseEntity> breedList;
    private ArrayList<BaseEntity> SubBreedList;

    private Method[] methods;

    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        fulllist = new ArrayList<>();
        fulllist.add(new BreedInfo(0, "test"));
        fulllist.add(new SubBreedInfo(1, "test"));
        //
        breedList = new ArrayList<>();
        breedList.add(new BreedInfo(0, "test"));
        //
        SubBreedList = new ArrayList<>();
        SubBreedList.add(new SubBreedInfo(1, "test"));
        //
        view = mock(NestedListInfoContract.View.class);
        presenterImp = new NestedListInfoPresenterImp();
        presenterImp.attachView(view);
        presenterImp.setBreedInfoFullList(fulllist);
        presenter = PowerMockito.spy(presenterImp);


    }

    @After
    public void tearDown() throws Exception {

        RxAndroidPlugins.reset();

    }

    @Test
    public void test_loadBreedListData_onSuccess() throws Exception {
        PowerMockito.mockStatic(RetrofitService.class);
        NetData result = new NetData();
        PowerMockito.when(RetrofitService.getAll()).thenReturn(Observable.just(result));
        PowerMockito.doCallRealMethod().when(presenter).loadBreedListData();
        //
        presenter.loadBreedListData();
        verify(view).setRecyclerView(any());
    }


    @Test
    public void test_loadBreedListData_onError() throws Exception {
        PowerMockito.mockStatic(RetrofitService.class);
        Exception e = new Exception();
        PowerMockito.when(RetrofitService.getAll()).thenReturn(Observable.error(e));
        presenter.loadBreedListData();
        verify(view).showLoadingDialog(any());
        verify(view).onLoadInfoFail(e.getMessage());

    }

    @Test
    public void test_loadMoreBreeds_onSuccess() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(0);
        //mock private method "updateShowList"
        PowerMockito.when(presenter, "updateShowList").thenReturn(integers);

        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getBreedRequireImageList", integers).thenReturn(breedList);

        //mock static RetrofitService class
        Bitmap bitmap = PowerMockito.mock(Bitmap.class);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
        PowerMockito.mockStatic(RetrofitService.class);
        PowerMockito.when(RetrofitService.getTheBreedImages(breedList)).thenReturn(Single.just(bitmaps));

        //mork the target method
        PowerMockito.doCallRealMethod().when(presenter).loadMoreBreeds();

        presenter.loadMoreBreeds();
        verify(view).showLoadingDialog(any());
        verify(view).updateRecyclerViewItem(anyInt(), any());
        verify(view).onLoadInfoSuccess();
    }


    @Test
    public void test_loadMoreBreeds_onError() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(0);
        //mock private method "updateShowList"
        PowerMockito.when(presenter, "updateShowList").thenReturn(integers);

        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getBreedRequireImageList", integers).thenReturn(fulllist);

        //mock static RetrofitService class
        Bitmap bitmap = PowerMockito.mock(Bitmap.class);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
        PowerMockito.mockStatic(RetrofitService.class);
        Exception e = new Exception();
        PowerMockito.when(RetrofitService.getTheBreedImages(fulllist)).thenReturn(Single.error(e));

        PowerMockito.doCallRealMethod().when(presenter).loadMoreBreeds();
        presenter.loadMoreBreeds();
        verify(view).showLoadingDialog(any());
        verify(view).onLoadInfoFail(e.getMessage());
    }

    @Test
    public void test_loadMoreBreeds_NoLoadImage() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(0);
        //mock private method "updateShowList"
        PowerMockito.when(presenter, "updateShowList").thenReturn(integers);

        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getBreedRequireImageList", integers).thenReturn(new ArrayList<BaseEntity>());

        //mork the target method
        PowerMockito.doCallRealMethod().when(presenter).loadMoreBreeds();

        presenter.loadMoreBreeds();
        verify(view).showLoadingDialog(any());
        verify(view).onLoadInfoSuccess();
    }

    @Test
    public void test_updateSubBreedstatus_onSuccess() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);

        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getSubBreedIndexList", 0).thenReturn(integers);
        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getSubBreedRequireImageList", integers).thenReturn(SubBreedList);

        //mock static RetrofitService class
        Bitmap bitmap = PowerMockito.mock(Bitmap.class);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
        PowerMockito.mockStatic(RetrofitService.class);
        PowerMockito.when(RetrofitService.getTheSubBreedImages(SubBreedList)).thenReturn(Single.just(bitmaps));

        //mork the target method
        PowerMockito.doCallRealMethod().when(presenter).updateSubBreedstatus(0);

        presenter.updateSubBreedstatus(0);
        verify(view).showLoadingDialog(any());
        verify(view).updateRecyclerViewItem(anyInt(), any());
        verify(view).onLoadInfoSuccess();
    }

    @Test
    public void test_updateSubBreedstatus_onError() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);

        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getSubBreedIndexList", 0).thenReturn(integers);
        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getSubBreedRequireImageList", integers).thenReturn(SubBreedList);

        //mock static RetrofitService class
        Bitmap bitmap = PowerMockito.mock(Bitmap.class);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
        PowerMockito.mockStatic(RetrofitService.class);
        Exception e = new Exception();
        PowerMockito.when(RetrofitService.getTheSubBreedImages(SubBreedList)).thenReturn(Single.error(e));

        //mork the target method
        PowerMockito.doCallRealMethod().when(presenter).updateSubBreedstatus(0);

        presenter.updateSubBreedstatus(0);
        verify(view).showLoadingDialog(any());
        verify(view).onLoadInfoFail(e.getMessage());
    }


    @Test
    public void test_updateSubBreedstatus_onLoadData() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();


        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getSubBreedIndexList", 0).thenReturn(integers);
        //mork the target method
        PowerMockito.doCallRealMethod().when(presenter).updateSubBreedstatus(0);

        presenter.updateSubBreedstatus(0);
        verify(view).showLoadingDialog(any());
        verify(view).showToast(anyString());
        verify(view).onLoadInfoSuccess();
    }

    @Test
    public void test_updateSubBreedstatus_onLoadedImageData() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);

        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getSubBreedIndexList", 0).thenReturn(integers);
        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getSubBreedRequireImageList", integers).thenReturn(new ArrayList<SubBreedInfo>());

        PowerMockito.doCallRealMethod().when(presenter).updateSubBreedstatus(0);

        presenter.updateSubBreedstatus(0);
        verify(view).showLoadingDialog(any());
        verify(view).updateRecyclerViewItem(anyInt(), any());
        verify(view).onLoadInfoSuccess();
    }


    @Test
    public void test_hideTheSubBreed_noImage() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getSubBreedIndexList", 0).thenReturn(integers);
        PowerMockito.doCallRealMethod().when(presenter).hideTheSubBreed(0);

        presenter.hideTheSubBreed(0);
        verify(view).showLoadingDialog(any());
        verify(view).showToast(anyString());
        verify(view).onLoadInfoSuccess();
    }

    @Test
    public void test_hideTheSubBreed_withImage() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        //mock private method "getRequireImageList"
        PowerMockito.when(presenter, "getSubBreedIndexList", 0).thenReturn(integers);
        PowerMockito.doCallRealMethod().when(presenter).hideTheSubBreed(0);

        presenter.hideTheSubBreed(0);
        verify(view).showLoadingDialog(any());
        verify(view).updateRecyclerViewItem(anyInt(), any());
        verify(view).onLoadInfoSuccess();
    }
    
}