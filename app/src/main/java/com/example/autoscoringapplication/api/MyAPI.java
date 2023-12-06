package com.example.autoscoringapplication.api;

import com.example.autoscoringapplication.data.Answer;
import com.example.autoscoringapplication.data.Name;
import com.example.autoscoringapplication.data.Result;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MyAPI {
    @POST("/answers")
    Call<Answer> postAnswer(@Body Answer data);

    @Multipart
    @POST("/image")
    Call<String> uploadImage(@Part MultipartBody.Part image);

    @GET("/name")
    Call<List<Name>> getName();

    @FormUrlEncoded
    @POST("/result")
    Call<Result> getResult(
            @Field("_id") String id
    );
}
