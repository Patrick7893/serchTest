package com.example.stasenkopavel.searchtest;
import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by stasenkopavel on 4/29/16.
 */
public class ApiFactory {

    private static ApiFactory instance;

    private ApiInterface apiInterface;

    public ApiFactory() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.SERVICE_ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiFactory getInstance() {
        if (instance == null)
            instance = new ApiFactory();
        return instance;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

}
