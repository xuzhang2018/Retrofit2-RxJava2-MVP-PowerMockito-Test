package com.jenway.cuvvadogshowapplication.model.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * by Xu
 * Description: base information of the dogs
 * name is the name of the breeds or sub breeds
 * imageID is the image ID of the breeds or sub breeds
 * imageBitmap is to save the bitmap for the detail
 */

public abstract class BaseEntity implements Parcelable {

    public static final Creator<BaseEntity> CREATOR = new Creator<BaseEntity>() {
        @Override
        public BaseEntity createFromParcel(Parcel in) {
            return BaseEntity.getConcreteClass(in);
        }

        @Override
        public BaseEntity[] newArray(int size) {
            return new BaseEntity[size];
        }
    };

    private int index = -1;
    private String name;
    private String uri;
    private Bitmap imageBitmap;
    private int isSelected = 0;//0 not select//1 selected
    private int isShown = 0;

    public BaseEntity(int index, String name, int isShown) {
        this.index = index;
        this.name = name;
        this.isShown = isShown;
    }

    protected BaseEntity(Parcel in, boolean isBaseEntityParcelable) {
        if (!isBaseEntityParcelable) {
            in.readInt();
        }

        index = in.readInt();
        name = in.readString();
        uri = in.readString();
        imageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        isSelected = in.readInt();
        isShown = in.readInt();
    }

    //to get the right class for Parcelable process
    private static BaseEntity getConcreteClass(Parcel source) {
        switch (source.readInt()) {
            case BreedInfo.BREED_INDEX:
                return new BreedInfo(source, true);
            case SubBreedInfo.SUB_BREED_INDEX:
                return new SubBreedInfo(source, true);
            default:
                throw new IllegalArgumentException("Fail to create parcelable for base entity");
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public void setSelectStatus(boolean isSelected) {
        if (isSelected) {
            this.isSelected = 1;
        } else {
            this.isSelected = 0;
        }
    }

    public boolean getSelecgtStatus() {
        return isSelected > 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(index);
        parcel.writeString(name);
        parcel.writeString(uri);
        parcel.writeParcelable(imageBitmap, i);
        parcel.writeInt(isSelected);
        parcel.writeInt(isShown);
    }

    public boolean getIsShown() {
        return isShown > 0;
    }

    public void setIsShown(boolean isShown) {
        if (isShown) {
            this.isShown = 1;
        } else {
            this.isShown = 0;
        }
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
