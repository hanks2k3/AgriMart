package com.example.agrimart.data.API;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiGHN {
    private static Retrofit retrofit=null;

    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl("https://dev-online-gateway.ghn.vn/shiip/public-api/")
                    .client(new OkHttpClient())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}
