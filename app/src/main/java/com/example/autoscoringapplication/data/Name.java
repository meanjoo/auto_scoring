package com.example.autoscoringapplication.data;

import com.google.gson.annotations.SerializedName;

public class Name {
    @SerializedName("_id") private String id;
    @SerializedName("ans_name") private String ansName;

    public Name() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAnsName(String ansName) {
        this.ansName = ansName;
    }

    public String getId() {
        return id;
    }

    public String getAnsName() {
        return ansName;
    }
}
