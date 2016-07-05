package com.example.msi.myapp.Utils;

import com.example.msi.myapp.Interface.API.AndroidApi;
import com.example.msi.myapp.Interface.API.MeiziApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 文 件 名: NetworkUtil
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 17:17
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class NetworkUtil {
    private static MeiziApi meiziApi;
    private static final String GAMKIO = "http://gank.io/api/";
    private static OkHttpClient.Builder builder;
    private static OkHttpClient client;
    private static Converter.Factory gsonConverterFactory ;
    private static CallAdapter.Factory rxJavaCallAdapterFactory ;
    private static NetworkUtil INSTANCE = new NetworkUtil();
    private static AndroidApi androidApi;

    private NetworkUtil(){
        builder =new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        client = builder.build();
        gsonConverterFactory = GsonConverterFactory.create();
        rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    }

    public static NetworkUtil getINSTANCE(){
        return INSTANCE;
    }
    public static MeiziApi getMeiziApi() {
        if (null == meiziApi) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(GAMKIO)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            meiziApi = retrofit.create(MeiziApi.class);
        }
        return meiziApi;
    }
    public static AndroidApi getAndroidApi(){
        if (null == androidApi){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(GAMKIO)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            androidApi = retrofit.create(AndroidApi.class);
        }
        return androidApi;
    }

}
