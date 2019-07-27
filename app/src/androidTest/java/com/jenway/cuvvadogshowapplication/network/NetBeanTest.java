package com.jenway.cuvvadogshowapplication.network;

import android.graphics.Bitmap;
import android.webkit.MimeTypeMap;

import com.google.gson.Gson;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;
import com.jenway.cuvvadogshowapplication.model.entity.BreedInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

import static org.junit.Assert.assertEquals;

public class NetBeanTest {

    private NetData data;
    private Gson gson;

    @Before
    public void setUp() throws Exception {

        gson = new Gson();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_CovertTheNetDataToBreedList() {
        data = gson.fromJson(NetUtil.getJson("breedlist.json"), NetData.class);
        List<BaseEntity> breeds = NetBean.CovertTheNetDataToBreedList(data);
        assertEquals(breeds.size(), 41);
        assertEquals(((BreedInfo) breeds.get(3)).getSubBreedIndexList().size(), 3);
        assertEquals(((BreedInfo) breeds.get(9)).getSubBreedIndexList().size(), 0);
    }


    @Test
    public void test_getBitmapFromResponseBody() {

        Bitmap image = NetUtil.getBitmapFromAssets("n02097130_5347.jpg");
//then create a copy of bitmap bmp1 into bmp2
        Bitmap image2 = image.copy(image.getConfig(), true);
        byte[] byteArray = NetUtil.getBitmapByte(image);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        ResponseBody body = ResponseBody
                .create(MediaType.parse(          // Convert string to MediaType
                        mimeTypeMap.getMimeTypeFromExtension("jpg") // currently only support JPG format, but we can add more image format
                        ), byteArray // the image is saved in the value
                );

        Bitmap data = NetBean.getBitmapFromResponseBody(body);
        assertEquals(image2.getByteCount(), data.getByteCount());
    }
}