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


/**
 * by Xu
 * test the retrofit service
 * Only load the local data when the debug mod is on
 */

public class APIRequestTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadBreedList() throws Exception {
        RetrofitService.mockDataName = "breedlist.json";
        TestObserver<NetData> testObserver = new TestObserver<>();
        RetrofitService.getAll().subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        NetData data = testObserver.values().get(0);
        List<BaseEntity> breeds = NetBean.CovertTheNetDataToBreedList(data);
        assertEquals(breeds.size(), 41);
        assertEquals(((BreedInfo) breeds.get(3)).getSubBreedIndexList().size(), 3);
        assertEquals(((BreedInfo) breeds.get(9)).getSubBreedIndexList().size(), 0);
    }


    @Test
    public void testLoadBreedImageAddress() throws Exception {
        RetrofitService.mockDataName = "breedImage.json";
        TestObserver<NetUriData> testObserver = new TestObserver<>();
        RetrofitService.baseApi.getBreedRandomImageAddress("").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        NetUriData data = testObserver.values().get(0);
        assertEquals(data.getMessage(), "https://images.dog.ceo/breeds/schnauzer-giant/n02097130_5347.jpg");
    }

    @Test
    public void testLoadBreedImage() throws Exception {
        RetrofitService.mockDataName = "Bitmap";
        TestObserver<ResponseBody> testObserver = new TestObserver<>();
        RetrofitService.baseApi.getBreedImage("https://images.dog.ceo//breeds//schnauzer-giant//n02097130_5347.jpg").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        ResponseBody data = testObserver.values().get(0);
        assertEquals(data.contentType().toString(), "image/jpeg");
        Bitmap image = NetUtil.getBitmapFromAssets("n02097130_5347.jpg");
        assertEquals(data.contentLength(), NetUtil.getBitmapByte(image).length);
    }
}