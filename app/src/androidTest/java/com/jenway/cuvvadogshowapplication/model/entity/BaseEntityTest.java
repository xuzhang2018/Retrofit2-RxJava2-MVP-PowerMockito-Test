package com.jenway.cuvvadogshowapplication.model.entity;

import android.graphics.Bitmap;
import android.os.Parcel;

import com.jenway.cuvvadogshowapplication.network.NetUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BaseEntityTest {


    private BreedInfo breedInfo;
    private SubBreedInfo subBreedInfo;

    @Before
    public void setUp() throws Exception {
        subBreedInfo = new SubBreedInfo(1, "boston");
        Bitmap bm = NetUtil.getBitmapFromAssets("n02097130_5347.jpg");
        subBreedInfo.setImageBitmap(bm);
        subBreedInfo.setSelectStatus(false);

        //
        breedInfo = new BreedInfo(0, "bulldog");
        breedInfo.setImageBitmap(bm);
        breedInfo.setSelectStatus(true);

    }

    @After
    public void tearDown() throws Exception {
        breedInfo = null;
        subBreedInfo = null;
    }

    /**
     * Test parcelable.
     */
    @Test
    public void test_BreedInfo_parcelable() throws Exception {
        Parcel parcel = Parcel.obtain();
        breedInfo.writeToParcel(parcel, breedInfo.describeContents());
        parcel.setDataPosition(0);
        BaseEntity createdFromParcel = BaseEntity.CREATOR.createFromParcel(parcel);
        assertEquals(createdFromParcel.getName().equals(breedInfo.getName()), true);
        assertEquals(createdFromParcel.getImageBitmap().sameAs(breedInfo.getImageBitmap()), true);
        assertEquals(createdFromParcel.getSelecgtStatus() == breedInfo.getSelecgtStatus(), true);
        parcel.recycle();
    }


    @Test
    public void test_SubBreedInfo_parcelable() throws Exception {
        Parcel parcel = Parcel.obtain();
        subBreedInfo.writeToParcel(parcel, subBreedInfo.describeContents());
        parcel.setDataPosition(0);
        BaseEntity createdFromParcel = BaseEntity.CREATOR.createFromParcel(parcel);
        assertEquals(createdFromParcel.getName().equals(subBreedInfo.getName()), true);
        assertEquals(createdFromParcel.getImageBitmap().sameAs(subBreedInfo.getImageBitmap()), true);
        assertEquals(createdFromParcel.getSelecgtStatus() == subBreedInfo.getSelecgtStatus(), true);
        parcel.recycle();
    }

}