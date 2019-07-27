package com.jenway.cuvvadogshowapplication.api.myGsonConverter;

import android.webkit.MimeTypeMap;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * by Xu
 * Description: custom the GSON converter
 * Change the responseBodyConverter method to handle the image download
 * the rest of the data will be used by GSON to decode.
 */
public class MyGsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    private MyGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static MyGsonConverterFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static MyGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new MyGsonConverterFactory(gson);
    }

    /**
     * add the custom converter to handle the image download
     * the rest of the data will be used by GSON to decode.
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (((Class) type).getSimpleName().equals("Bitmap")) {//handle the JPG image
            return new Converter<okhttp3.ResponseBody, ResponseBody>() {
                @Override
                public ResponseBody convert(okhttp3.ResponseBody value) throws IOException {
                    MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                    ResponseBody responseBody = ResponseBody
                            .create(MediaType.parse(          // Convert string to MediaType
                                    mimeTypeMap.getMimeTypeFromExtension("jpg") // currently only support JPG format, but we can add more image format
                                    ), value.bytes() // the image is saved in the value
                            );
                    return responseBody;
                }
            };
        } else {
            //the rest of the data will be used by GSON to decode.
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new MyGsonResponseBodyConverter<>(gson, adapter);
        }
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new MyGsonRequestBodyConverter<>(gson, adapter);
    }
}
