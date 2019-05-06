package com.testt.productlist.service;

import com.testt.productlist.Conf;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * {@link ApiClient} is designed to initialize
 * base retrofit url and manage retrofit
 * initialization see {@link #init()}.
 * <p>
 * {@link ApiInterface} is also accessed
 * through {@link ApiClient} instance
 * and reached by {@link #getApiInterface()}
 * <p>
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.service
 */
public class ApiClient {

    /**
     * {@link ApiClient} instance
     */
    private static volatile ApiClient instance;

    /**
     * {@link ApiInterface} instance
     */
    private ApiInterface apiInterface;

    /**
     * {@link ApiClient} construction
     * also initialize and prepare retrofit
     */
    private ApiClient() {
        init();
    }

    /**
     * static {@link ApiClient} instance
     * is reached by this method
     *
     * @return {@link ApiClient}
     */
    public static ApiClient getInstance() {
        ApiClient localInstance = instance;
        if (localInstance == null) {
            synchronized (ApiClient.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ApiClient();
                }
            }
        }
        return localInstance;
    }

    /**
     * retrofit initialization
     */
    public void init() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (Conf.RETROFIT_LOG) {
            builder = builder.addInterceptor(interceptor);
        }

        builder = builder.addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Content-Type", "application/json; charset=utf-8")
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
        builder = builder.readTimeout(60, TimeUnit.SECONDS);
        builder = builder.connectTimeout(60, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.build();


        Gson gson = new GsonBuilder()
                .create();

        GsonConverterFactory factory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Conf.API_ROOT)
                .addConverterFactory(factory)
                .client(okHttpClient)
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    /**
     * reach {@link ApiInterface} instance
     * for the application scope
     *
     * @return {@link ApiInterface}
     */
    public ApiInterface getApiInterface() {
        return apiInterface;
    }

}
