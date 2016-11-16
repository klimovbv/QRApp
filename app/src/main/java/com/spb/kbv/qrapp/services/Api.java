package com.spb.kbv.qrapp.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    @GET
    Call<ResponseBody> getStatus(@Url String url);
}
