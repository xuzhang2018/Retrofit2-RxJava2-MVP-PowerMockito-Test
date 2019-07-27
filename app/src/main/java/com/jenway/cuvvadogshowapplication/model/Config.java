package com.jenway.cuvvadogshowapplication.model;

public class Config {

    ///images.dog.ceo/breeds/affenpinscher/n02110627_13662.jpg
    public static final int maxloadingItemCount = 15;//load 15 breeds every time
    public static final String API_BASE_URL = "https://dog.ceo/api/";
    public static final String listAllEndPoint = "breeds/list/all";
    public static final String breedRandomImageAddress = "breed/{breedname}/images/random";
    public static final String subBreesRandomImageEndPoint = "breed/{breedname}/{subbreedname}/images/random";
}
