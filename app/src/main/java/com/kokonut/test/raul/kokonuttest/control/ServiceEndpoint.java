package com.kokonut.test.raul.kokonuttest.control;

import com.kokonut.test.raul.kokonuttest.model.LoginRequest;
import com.kokonut.test.raul.kokonuttest.model.LoginResponse;
import com.kokonut.test.raul.kokonuttest.model.PostResponse;
import com.kokonut.test.raul.kokonuttest.model.ProfileResponse;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ServiceEndpoint {

     @Headers("Accept: application/json ")
     @POST("login")
     Observable<LoginResponse> login(
             @Header("app_version")String appVersionHeader,
             @Header("lang")String langHeader,
             @Body LoginRequest request);


     @Headers("Accept: application/json ")
     @GET("user/profile")
     Observable<ProfileResponse> getProfile(
             @Header("app_version")String appVersionHeader,
             @Header("lang")String langHeader,
             @Header("Authorization")String authHeader);

    @Headers("Accept: application/json ")
    @GET("post/all")
    Observable<PostResponse> showPosts(
            @Header("app_version")String appVersionHeader,
            @Header("lang")String langHeader);

}
