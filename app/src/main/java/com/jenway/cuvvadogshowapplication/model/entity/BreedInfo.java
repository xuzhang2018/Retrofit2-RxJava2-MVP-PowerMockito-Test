package com.jenway.cuvvadogshowapplication.model.entity;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * by Xu
 * Description: this class record the first level of the data of the dog information
 * subBreeds is the list of sub breeds
 */

public class BreedInfo extends BaseEntity {
    //identity of the class BreedInfo for Parcelable
    public static final int BREED_INDEX = 0;
    public static final Creator<BreedInfo> CREATOR = new Creator<BreedInfo>() {
        @Override
        public BreedInfo createFromParcel(Parcel in) {
            return new BreedInfo(in, false);
        }

        @Override
        public BreedInfo[] newArray(int size) {
            return new BreedInfo[size];
        }
    };
    private List<Integer> subBreedIndexList = new ArrayList<>();

    public BreedInfo(int index, String breedName) {
        super(index, breedName, 0);//default all breed will not show the subbreed
    }

    public BreedInfo(Parcel in, boolean isBaseEntity) {
        super(in, isBaseEntity);
        in.readList(subBreedIndexList, Integer.class.getClassLoader());
    }

    public List<Integer> getSubBreedIndexList() {
        return this.subBreedIndexList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(BREED_INDEX);
        super.writeToParcel(dest, i);
        dest.writeList(subBreedIndexList);
    }


}
