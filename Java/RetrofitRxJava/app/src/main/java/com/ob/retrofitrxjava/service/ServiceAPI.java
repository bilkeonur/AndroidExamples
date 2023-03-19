package com.ob.retrofitrxjava.service;

import com.ob.retrofitrxjava.model.CityModel;
import com.ob.retrofitrxjava.model.LoginRequestModel;
import com.ob.retrofitrxjava.model.LoginResponseModel;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ServiceAPI {
    @Headers({
        "Content-Type: application/json",
        "Accept: application/json",
    })
    @GET("api/cities")
    Observable<CityModel> getCitiesObservable();

    @Headers({
        "Content-Type: application/json",
        "Accept: application/json",
    })
    @GET("api/cities")
    Single<CityModel> getCitiesSingle();

    @Headers({
        "Content-Type: application/json",
        "Accept: application/json",
    })
    @POST("api/login")
    Observable<LoginResponseModel> login(@Body LoginRequestModel loginRequestModel);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json",
    })
    @POST
    Observable<LoginResponseModel> loginWithURL(@Url String url, @Body LoginRequestModel loginRequestModel);
}
