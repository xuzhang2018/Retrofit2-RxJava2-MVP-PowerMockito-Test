package com.jenway.cuvvadogshowapplication.api;

import android.graphics.Bitmap;

import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;
import com.jenway.cuvvadogshowapplication.model.entity.BreedInfo;
import com.jenway.cuvvadogshowapplication.network.NetBean;
import com.jenway.cuvvadogshowapplication.network.NetData;
import com.jenway.cuvvadogshowapplication.network.NetUriData;
import com.jenway.cuvvadogshowapplication.network.NetUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.observers.TestObserver;
import okhttp3.ResponseBody;

import static org.junit.Assert.assertEquals;

public class APIRequestTest {

    IBaseApi baseApi;
    MockRetrofitHelper retrofitHelper;

    @Before
    public void setUp() throws Exception {
        retrofitHelper = new MockRetrofitHelper();
        baseApi = retrofitHelper.create(IBaseApi.class);
    }

    @After
    public void tearDown() throws Exception {
        retrofitHelper = null;
    }

    @Test
    public void testLoadBreedList() throws Exception {
        retrofitHelper.setDataName("breedlist.json");
        TestObserver<NetData> testObserver = new TestObserver<>();
        baseApi.getAll().subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        NetData data = testObserver.values().get(0);
        List<BaseEntity> breeds = NetBean.CovertTheNetDataToBreedList(data);
        assertEquals(breeds.size(), 41);
        assertEquals(((BreedInfo) breeds.get(3)).getSubBreedIndexList().size(), 3);
        assertEquals(((BreedInfo) breeds.get(9)).getSubBreedIndexList().size(), 0);
    }


    @Test
    public void testLoadBreedImageAddress() throws Exception {
        retrofitHelper.setDataName("breedImage.json");
        TestObserver<NetUriData> testObserver = new TestObserver<>();
        baseApi.getBreedRandomImageAddress("").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        NetUriData data = testObserver.values().get(0);
        assertEquals(data.getMessage(), "https://images.dog.ceo/breeds/schnauzer-giant/n02097130_5347.jpg");
    }

    @Test
    public void testLoadBreedImage() throws Exception {
        retrofitHelper.setDataName("Bitmap");
        TestObserver<ResponseBody> testObserver = new TestObserver<>();
        baseApi.getBreedImage("https://images.dog.ceo//breeds//schnauzer-giant//n02097130_5347.jpg").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        ResponseBody data = testObserver.values().get(0);
        assertEquals(data.contentType().toString(), "image/jpeg");
        Bitmap image = NetUtil.getBitmapFromAssets("n02097130_5347.jpg");
        assertEquals(data.contentLength(), NetUtil.getBitmapByte(image).length);
    }
}