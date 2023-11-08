package com.example.autoscoringapplication.api;

import com.example.autoscoringapplication.data.Album;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface TestAPI {
    @GET("/albums")
    Call<List<Album>> getAlbum();
}
