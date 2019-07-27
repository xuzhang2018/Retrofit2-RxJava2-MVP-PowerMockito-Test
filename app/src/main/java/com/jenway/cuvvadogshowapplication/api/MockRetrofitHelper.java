package com.jenway.cuvvadogshowapplication.api;

import android.graphics.Bitmap;
import android.webkit.MimeTypeMap;

import com.jenway.cuvvadogshowapplication.api.myGsonConverter.MyGsonConverterFactory;
import com.jenway.cuvvadogshowapplication.model.Config;
import com.jenway.cuvvadogshowapplication.network.NetUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * by Xu
 * Description: Mock the API service by using local data
 */
public class MockRetrofitHelper {

    private String dataName = "breedlist.json";

    public <T> T create(Class<T> clazz) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new MockInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.API_BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MyGsonConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    private class MockInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            ResponseBody body = null;
            if (dataName.equals("Bitmap")) {//if the request is Image, then return the n02097130_5347.jpg
                Bitmap image = NetUtil.getBitmapFromAssets("n02097130_5347.jpg");
                byte[] byteArray = NetUtil.getBitmapByte(image);
                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                body = ResponseBody
                        .create(MediaType.parse(          // Convert string to MediaType
                                mimeTypeMap.getMimeTypeFromExtension("jpg") // currently only support JPG format, but we can add more image format
                                ), byteArray // the image is saved in the value
                        );
                Response response = new Response.Builder().request(chain.request())
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .body(body)
                        .message("")
                        .build();
                return response;


            } else {
                // get local json/data
                String content = NetUtil.getJson(dataName);
                body = ResponseBody.create(MediaType.parse("application/json"), content);
                Response response = new Response.Builder().request(chain.request())
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .body(body)
                        .message("")
                        .build();
                return response;
            }


        }
    }
}