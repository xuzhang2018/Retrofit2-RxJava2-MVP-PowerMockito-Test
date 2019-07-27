package com.jenway.cuvvadogshowapplication.model.entity;

import android.os.Parcel;

/**
 * by Xu
 * Description: Description: this class record the first level of the data of the dog information and this class can be easily extended in the future development
 */
public class SubBreedInfo extends BaseEntity {
    //identity of the class SubBreedInfo for Parcelable
    public static final int SUB_BREED_INDEX = 1;

    public static final Creator<SubBreedInfo> CREATOR = new Creator<SubBreedInfo>() {
        @Override
        public SubBreedInfo createFromParcel(Parcel in) {
            return new SubBreedInfo(in, false);
        }

        @Override
        public SubBreedInfo[] newArray(int size) {
            return new SubBreedInfo[size];
        }
    };

    public SubBreedInfo(int index, String SubBreedName) {
        super(index, SubBreedName, 0);//default all breed will not show
    }

    public SubBreedInfo(Parcel in, boolean isBaseEntity) {
        super(in, isBaseEntity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(SUB_BREED_INDEX);
        super.writeToParcel(dest, i);
    }
}
