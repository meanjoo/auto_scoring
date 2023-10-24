package com.example.autoscoringapplication.api;

import com.example.autoscoringapplication.data.Answer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyAPI {
    @POST("answers")
    Call<Answer> postAnswer(@Body Answer data);
}
