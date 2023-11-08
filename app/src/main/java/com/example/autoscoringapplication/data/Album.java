package com.example.autoscoringapplication.data;

import com.google.gson.annotations.SerializedName;

public class Album {
    @SerializedName("userId") private int userId;
    @SerializedName("id") private int id;
    @SerializedName("title") private String title;

    public Album() {
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
