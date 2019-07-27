package com.jenway.cuvvadogshowapplication.model.entity;

import android.graphics.Bitmap;
import android.os.Parcel;

import com.jenway.cuvvadogshowapplication.network.NetUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SubBreedInfoTest {
    SubBreedInfo subBreedInfo;

    @Before
    public void setUp() throws Exception {
        subBreedInfo = new SubBreedInfo(0, "boston");
        Bitmap bm = NetUtil.getBitmapFromAssets("n02097130_5347.jpg");
        subBreedInfo.setImageBitmap(bm);
        subBreedInfo.setSelectStatus(false);
    }

    @After
    public void tearDown() throws Exception {
        subBreedInfo = null;
    }

    /**
     * Test parcelable.
     */
    @Test
    public void test_parcelable() throws Exception {
        Parcel parcel = Parcel.obtain();
        subBreedInfo.writeToParcel(parcel, subBreedInfo.describeContents());
        parcel.setDataPosition(0);
        SubBreedInfo createdFromParcel = SubBreedInfo.CREATOR.createFromParcel(parcel);
        assertEquals(createdFromParcel.getName().equals(subBreedInfo.getName()), true);
        assertEquals(createdFromParcel.getImageBitmap().sameAs(subBreedInfo.getImageBitmap()), true);
        assertEquals(createdFromParcel.getSelecgtStatus() == subBreedInfo.getSelecgtStatus(), true);
        parcel.recycle();
    }
}