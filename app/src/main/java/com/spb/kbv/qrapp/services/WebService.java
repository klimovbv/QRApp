package com.spb.kbv.qrapp.services;

import retrofit2.Retrofit;

public class WebService {
    public static Api createWebService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://my.api.url/")
                .build();

        return retrofit.create(Api.class);
    }
}
