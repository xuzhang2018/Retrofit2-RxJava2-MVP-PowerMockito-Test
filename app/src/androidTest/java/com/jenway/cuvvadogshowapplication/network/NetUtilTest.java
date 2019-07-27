package com.jenway.cuvvadogshowapplication.network;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NetUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_getJson() {
        String strJson = NetUtil.getJson("breedlist.json");
        Gson gson = new Gson();
        JsonElement jelem = gson.fromJson(strJson, JsonElement.class);
        JsonObject jobj = jelem.getAsJsonObject();
        JsonObject oj = jobj.getAsJsonObject("message");
        assertEquals(oj.size(), 29);
    }

    @Test
    public void test_getBitmapFromAssets() {
        Bitmap image = NetUtil.getBitmapFromAssets("n02097130_5347.jpg");
        assertEquals(image.getByteCount(), 948000);
    }

    @Test
    public void test_getBitmapByte() {
        Bitmap image = NetUtil.getBitmapFromAssets("n02097130_5347.jpg");
        byte[] data = NetUtil.getBitmapByte(image);
        assertEquals(data.length, 446401);
    }

}