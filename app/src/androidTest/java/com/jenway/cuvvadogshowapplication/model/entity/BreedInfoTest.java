package com.jenway.cuvvadogshowapplication.model.entity;

import android.graphics.Bitmap;
import android.os.Parcel;

import com.jenway.cuvvadogshowapplication.network.NetUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BreedInfoTest {
    BreedInfo breedInfo;

    @Before
    public void setUp() throws Exception {
        breedInfo = new BreedInfo(0, "bulldog");
        Bitmap bm = NetUtil.getBitmapFromAssets("n02097130_5347.jpg");
        breedInfo.setImageBitmap(bm);
        breedInfo.setSelectStatus(false);
        breedInfo.getSubBreedIndexList().add(3);
        breedInfo.getSubBreedIndexList().add(4);
        breedInfo.getSubBreedIndexList().add(5);
    }

    @After
    public void tearDown() throws Exception {
        breedInfo = null;
    }


    /**
     * Test parcelable.
     */
    @Test
    public void test_parcelable() throws Exception {
        Parcel parcel = Parcel.obtain();
        breedInfo.writeToParcel(parcel, breedInfo.describeContents());
        parcel.setDataPosition(0);
        BreedInfo createdFromParcel = BreedInfo.CREATOR.createFromParcel(parcel);
        assertEquals(createdFromParcel.getName().equals(breedInfo.getName()), true);
        assertEquals(createdFromParcel.getImageBitmap().sameAs(breedInfo.getImageBitmap()), true);
        assertEquals(createdFromParcel.getSubBreedIndexList().get(0) == breedInfo.getSubBreedIndexList().get(0), true);
        assertEquals(createdFromParcel.getSubBreedIndexList().get(1) == breedInfo.getSubBreedIndexList().get(1), true);
        assertEquals(createdFromParcel.getSubBreedIndexList().get(2) == breedInfo.getSubBreedIndexList().get(2), true);
        assertEquals(createdFromParcel.getSelecgtStatus() == breedInfo.getSelecgtStatus(), true);
        parcel.recycle();
    }
}