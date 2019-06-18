package com.example.popularmoviesstage2.data.source.remote;

import java.io.IOException;

import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/";

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new RxJavaCallAdapterFactoryObserveOnMainThread())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(getClientInterept())
                    .build();
        }
        return retrofit;
    }

    //https://stackoverflow.com/questions/34674820/unsupported-operation-android-retrofit-okhttp-adding-interceptor-in-okhttpcl
    private static OkHttpClient getClientInterept(){
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            Request original = chain.request();

                            HttpUrl url = original.url().newBuilder()
                                    .addQueryParameter("api_key",
                                            "85a08e715cb64d5e0d9f87daf23a3b60").build();

                            Request.Builder requestBuilder = original.newBuilder()
                                    .url(url)
                                    .method(original.method(), original.body());

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        })
                .build();
        return okClient;
    }

}
