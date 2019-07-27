package com.jenway.cuvvadogshowapplication.api;

import com.jenway.cuvvadogshowapplication.model.Config;
import com.jenway.cuvvadogshowapplication.network.NetData;
import com.jenway.cuvvadogshowapplication.network.NetUriData;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface IBaseApi {


    /**
     * Get the Breed and sub Breed's name list
     * eg:https://dog.ceo/api/breeds/list/all";
     *
     * @return NetData
     */
    @GET(Config.listAllEndPoint)
    Observable<NetData> getAll();

    /**
     * get the random image information
     * eg:https://dog.ceo/api/breed/affenpinscher/images/random
     *
     * @param name breed name or breed/subbreed name
     * @return NetUriData
     */
    @GET(Config.breedRandomImageAddress)
    Observable<NetUriData> getBreedRandomImageAddress(@Path("breedname") String name);


    /**
     * get the random image information
     * eg:https://dog.ceo/api/breed/schnauzer/miniature/images/random
     *
     * @param name breed name
     * @param name subbreed name
     * @return NetUriData
     */
    @GET(Config.subBreesRandomImageEndPoint)
    Observable<NetUriData> getSubBreedRandomImageAddress(@Path("breedname") String name, @Path("subbreedname") String subname);


    /**
     * get the ResponseBody(Image/JPG)
     * eg:https://images.dog.ceo/breeds/schnauzer-miniature/n02097047_2417.jpg
     *
     * @param uri Image Uri
     * @return Bitmap
     */
    @GET
    Observable<ResponseBody> getBreedImage(@Url String uri);


}

