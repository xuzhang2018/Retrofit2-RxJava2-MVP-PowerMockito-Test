package com.jenway.cuvvadogshowapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;
import com.jenway.cuvvadogshowapplication.model.entity.BreedInfo;
import com.jenway.cuvvadogshowapplication.model.entity.SubBreedInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.ResponseBody;


/**
 * by Xu
 * Description: handle the receive data
 */
public class NetBean {
    public static ArrayList<BaseEntity> CovertTheNetDataToBreedList(NetData data) {
        ArrayList<BaseEntity> myData = new ArrayList<>();
        int index = 0;

        Set<Map.Entry<String, List<String>>> entrySet = data.getMessage().entrySet();
        for (Map.Entry<String, List<String>> entry : entrySet) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            //
            BreedInfo breedInfo = new BreedInfo(index, key);
            breedInfo.setUri(breedInfo.getName());
            myData.add(breedInfo);
            index++;
            if (null != value) {
                for (int i = 0; i < value.size(); i++) {
                    SubBreedInfo subBreedInfo = new SubBreedInfo(index, value.get(i));
                    subBreedInfo.setUri(breedInfo.getName());
                    myData.add(subBreedInfo);
                    breedInfo.getSubBreedIndexList().add(subBreedInfo.getIndex());
                    index++;
                }
            }
        }

        return myData;
    }

    //change ResponseBody to byte array, be careful, this need to be process in new thread
    public static Bitmap getBitmapFromResponseBody(ResponseBody value) {
        byte[] bys = new byte[0];
        try {
            bys = value.bytes();
            return BitmapFactory.decodeByteArray(bys, 0, bys.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
